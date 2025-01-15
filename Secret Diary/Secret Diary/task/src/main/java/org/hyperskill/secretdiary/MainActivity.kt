package org.hyperskill.secretdiary
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
import android.content.SharedPreferences
import android.content.Context
import android.content.Intent
import kotlinx.datetime.*
import kotlin.text.padStart


const val PREF_DIARY = "PREF_DIARY"
lateinit var sharedPreferences: SharedPreferences

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences(PREF_DIARY, Context.MODE_PRIVATE)
        val btnSave = findViewById<Button>(R.id.btnSave)
        val btnUndo = findViewById<Button>(R.id.btnUndo)
        val etNewWriting = findViewById<EditText>(R.id.etNewWriting)
        val tvDiary = findViewById<TextView>(R.id.tvDiary)
        var newNote: String = ""
        var savedNote: String = ""
        var currentDateTime: LocalDateTime
        val editor = sharedPreferences.edit()

        sharedPreferences.getString("KEY_DIARY_TEXT", "").let{
            tvDiary.setText(it)
        }

        btnSave.setOnClickListener{
            currentDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            newNote = etNewWriting.getText().toString()
            savedNote = tvDiary.getText().toString()

            if (newNote.isEmpty() || newNote.isBlank()) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.emptyStringError),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                if (savedNote.isEmpty()) {
                    tvDiary.setText(dateTimeToString(currentDateTime) + "\n" + newNote)
                } else {
                    tvDiary.setText(
                        dateTimeToString(currentDateTime) + "\n" + newNote + "\n\n" + tvDiary.getText()
                            .toString()
                    )
                }
                editor.putString("KEY_DIARY_TEXT", tvDiary.getText().toString()).apply()
                etNewWriting.getText().clear()
            }
        }
        btnUndo.setOnClickListener {
                savedNote = tvDiary.getText().toString()
                AlertDialog.Builder(this)
                    .setTitle(R.string.removeStringDialogTitle)
                    .setMessage(R.string.removeStringDialogMessage)
                    .setNegativeButton(R.string.no, null)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        if (savedNote.isNotEmpty()) {
                            savedNote.substringAfter("\n\n").let {
                                if (it.isNotEmpty()) tvDiary.setText(it)
                                else tvDiary.setText("")
                            }
                        }
                    }
                    .show()
                editor.putString("KEY_DIARY_TEXT", tvDiary.getText().toString()).apply()
        }


            /*
            Tests for android can not guarantee the correctness of solutions that make use of
            mutation on "static" variables to keep state. You should avoid using those.
            Consider "static" as being anything on kotlin that is transpiled to java
            into a static variable. That includes global variables and variables inside
            singletons declared with keyword object, including companion object.
            This limitation is related to the use of JUnit on tests. JUnit re-instantiate all
            instance variable for each test method, but it does not re-instantiate static variables.
            The use of static variable to hold state can lead to state from one test to spill over
           adb shell pm list packages to another test and cause unexpected results.
            Using mutation on static variables to keep state
            is considered a bad practice anyway and no measure
            attempting to give support to that pattern will be made.
         */
    }
        /*
    override fun onSaveInstanceState(outState: Bundle){
        super.onSaveInstanceState(outState)
        //send user pwd
    }*/
        override fun onRestart() {
            //create and send intent to start login activity if
            //pwd exists in savedInstance
            super.onRestart()
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }
    }

    fun dateTimeToString(date: LocalDateTime): String {
        return "${date.date} ${date.hour.toString().padStart(2, '0')}:${
            date.minute.toString().padStart(2, '0')
        }:${date.second.toString().padStart(2, '0')}"
    }
