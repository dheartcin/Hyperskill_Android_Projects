package org.hyperskill.calculator.tip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import android.widget.SeekBar
import android.text.TextWatcher
import android.text.Editable
import java.math.RoundingMode
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.edit_text)
        val bill_value_tv = findViewById<TextView>(R.id.bill_value_tv)
        val seek_bar = findViewById<SeekBar>(R.id.seek_bar)
        val tip_percent_tv = findViewById<TextView>(R.id.tip_percent_tv)
        val tip_amount_tv = findViewById<TextView>(R.id.tip_amount_tv)

        var textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                val bill = s.toString()
                if(bill.isNotEmpty() && bill.toDouble() > 0) {
                    val hundred = BigDecimal(100)
                    val billValue = bill.toBigDecimal().setScale(2, RoundingMode.HALF_UP)
                    val tipPercent = seek_bar.progress
                    bill_value_tv.text = "Bill Value: $${billValue}"
                    tip_percent_tv.text = "Tip: ${tipPercent}%"
                    tip_amount_tv.text = "Tip Amount: $${(billValue * tipPercent.toBigDecimal()/hundred).setScale(2, RoundingMode.HALF_UP)}"
                } else {
                    bill_value_tv.text=""
                    tip_percent_tv.text=""
                    tip_amount_tv.text=""
                }
            }
        }
        editText.addTextChangedListener(textWatcher)

        seek_bar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(editText.text.isNotEmpty() && (editText.text.toString().toDouble()>0)) {
                    val hundred = BigDecimal(100)
                    val billValue = editText.text.toString().toBigDecimal().setScale(2, RoundingMode.HALF_UP)
                    val tipPercent = seek_bar.progress

                    tip_percent_tv.text = "Tip: ${tipPercent}%"
                    tip_amount_tv.text = "Tip Amount: $${(billValue * tipPercent.toBigDecimal()/hundred).setScale(2, RoundingMode.HALF_UP)}"
                } else {
                    tip_amount_tv.text=""
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }
            override fun onStopTrackingTouch(seekBar: SeekBar?){
            }
        })



    }


}