package com.example.segundoactivitypswd

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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

        val etClave = findViewById<EditText>(R.id.etClave)
        val btnValidar = findViewById<Button>(R.id.btnValidar)

        btnValidar.setOnClickListener {
            val claveIngresada = etClave.text.toString()
            if (claveIngresada == "abc123") {
                // Clave correcta, iniciar nueva Activity
                Toast.makeText(this, "Clave correcta", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
            } else {
                // Clave incorrecta, mostrar Toast
                Toast.makeText(this, "Ingresa la clave abc123", Toast.LENGTH_SHORT).show()
            }
        }
    }
}