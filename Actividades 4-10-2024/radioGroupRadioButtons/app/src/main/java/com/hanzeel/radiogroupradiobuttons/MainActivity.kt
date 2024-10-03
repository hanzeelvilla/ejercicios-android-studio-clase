package com.hanzeel.radiogroupradiobuttons

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
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
        val r1 = findViewById<RadioButton>(R.id.r1)
        val r2 = findViewById<RadioButton>(R.id.r2)
        val button = findViewById<Button>(R.id.button)
        val tv1 = findViewById<TextView>(R.id.tv1)

        button.setOnClickListener {
            val num1 = et1.text.toString().toIntOrNull() ?: 0
            val num2 = et2.text.toString().toIntOrNull() ?: 0

            if(r1.isChecked)
                tv1.text = "Resultado = ${num1 + num2}"

            if(r2.isChecked)
                tv1.text = "Resultado = ${num1 - num2}"
        }
    }
}