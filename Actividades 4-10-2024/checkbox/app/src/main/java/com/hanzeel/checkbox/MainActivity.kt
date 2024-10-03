package com.hanzeel.checkbox

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val et1 = findViewById<EditText>(R.id.et1)
        val et2 = findViewById<EditText>(R.id.et2)
        val check1 = findViewById<CheckBox>(R.id.check1)
        val check2 = findViewById<CheckBox>(R.id.check2)
        val button = findViewById<Button>(R.id.button)
        val tv1 = findViewById<TextView>(R.id.tv1)

        button.setOnClickListener {
            var res = ""
            val num1 = et1.text.toString().toIntOrNull() ?: 0
            val num2 = et2.text.toString().toIntOrNull() ?: 0

            if (check1.isChecked)
                res += "Suma = ${num1 + num2} \n"

            if (check2.isChecked)
                res += "Resta = ${num1 - num2}"

            tv1.text = res
        }
    }
}