package com.hanzeel.spinner

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
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
        val spinner = findViewById<Spinner>(R.id.spinner)
        val button = findViewById<Button>(R.id.button)
        val tv1 = findViewById<TextView>(R.id.tv1)

        val opcionesSpinner = arrayOf("Sumar", "Restar", "Multiplicar", "Dividir")

        spinner.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            opcionesSpinner
        )

        button.setOnClickListener {
            val num1 = et1.text.toString().toIntOrNull() ?: 0
            val num2 = et2.text.toString().toIntOrNull() ?: 0

            when (spinner.selectedItem.toString()) {
                "Sumar" -> tv1.text = "Suma = ${num1 + num2}"
                "Restar" -> tv1.text = "Resta = ${num1 - num2}"
                "Multiplicar" -> tv1.text = "Multiplicacion = ${num1 * num2}"
                "Dividir" -> tv1.text = "División = ${num1 / num2}"
            }
        }
    }
}