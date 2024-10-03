package com.hanzeel.juegoadivinarnumero

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var numeroSecreto: Int = 0
    private var puntaje: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val puntajeTextView = findViewById<TextView>(R.id.puntaje)
        val pistaTextView = findViewById<TextView>(R.id.pista)
        val numUsuarioEditText = findViewById<EditText>(R.id.numUsuario)
        val btnAdivinar = findViewById<Button>(R.id.btnAdivinar)

        // Recuperar el puntaje de las preferencias al iniciar la actividad
        puntaje = obtenerPuntajeDePreferencias()
        puntajeTextView.text = puntaje.toString()

        // Generar el número aleatorio al iniciar la actividad
        generarNumeroAleatorio()

        btnAdivinar.setOnClickListener {
            val numeroUsuario = numUsuarioEditText.text.toString().toIntOrNull()

            if (numeroUsuario != null) {
                when {
                    numeroUsuario < numeroSecreto -> {
                        pistaTextView.text = "El número es mayor a $numeroUsuario"
                    }
                    numeroUsuario > numeroSecreto -> {
                        pistaTextView.text = "El número es menor a $numeroUsuario"
                    }
                    else -> {
                        pistaTextView.text = "¡Correcto! Has adivinado el número."
                        incrementarPuntaje(puntajeTextView)
                        generarNumeroAleatorio() // Genera un nuevo número al adivinar
                    }
                }
            } else {
                pistaTextView.text = "Por favor ingresa un número válido"
            }
        }
    }

    // Función para generar un número aleatorio entre 1 y 50
    private fun generarNumeroAleatorio() {
        numeroSecreto = Random.nextInt(1, 51)
    }

    // Función para incrementar el puntaje
    private fun incrementarPuntaje(puntajeTextView: TextView) {
        puntaje++
        puntajeTextView.text = puntaje.toString()
        guardarPuntajeEnPreferencias(puntaje)
    }

    // Guardar el puntaje en SharedPreferences
    private fun guardarPuntajeEnPreferencias(puntaje: Int) {
        val sharedPref = getSharedPreferences("PuntajePrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt("puntaje", puntaje)
            apply() // Guarda los cambios de manera asíncrona
        }
    }

    // Recuperar el puntaje desde SharedPreferences
    private fun obtenerPuntajeDePreferencias(): Int {
        val sharedPref = getSharedPreferences("PuntajePrefs", Context.MODE_PRIVATE)
        return sharedPref.getInt("puntaje", 0) // Retorna 0 si no hay valor guardado
    }
}