package org.hyperskill.secretdiary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.content.Intent
import android.app.AlertDialog

const val PWD = 1234

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etPin = findViewById<EditText>(R.id.etPin)

        btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            if(etPin.getText().toString().toInt() == PWD) {
                finish()
                startActivity(intent)
            }
            else etPin.setError(this.getResources().getString(R.string.error_wrongPin))
        }
    }

}