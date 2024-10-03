package com.hanzeel.notificaciones

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private val button: Button by lazy {
        findViewById<Button>(R.id.button)
    }

    private val buttonHanzeel: Button by lazy {
        findViewById<Button>(R.id.buttonHanzeel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        button.setOnClickListener {
            Toast.makeText(this, "¡Hola mundo!", Toast.LENGTH_SHORT).show()
        }

        buttonHanzeel.setOnClickListener {
            val snackbar = Snackbar.make(
                findViewById(android.R.id.content),
                "¡Hola soy Hanzeel",
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }
    }
}