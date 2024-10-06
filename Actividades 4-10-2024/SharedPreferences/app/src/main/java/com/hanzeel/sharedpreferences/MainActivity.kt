package com.hanzeel.sharedpreferences

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        val et1=findViewById<EditText>(R.id.et1)
        val preferencias = getSharedPreferences("datos", Context.MODE_PRIVATE)
        et1.setText(preferencias.getString("mail", ""))
        val boton1=findViewById<Button>(R.id.button)

        boton1.setOnClickListener {
            val editor = preferencias.edit()
            editor.putString("mail", et1.text.toString())
            editor.commit()
            finish()
        }
    }
}