package com.hanzeel.imagebutton

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private val imageButton: ImageButton by lazy {

        findViewById<ImageButton>(R.id.imageButton)
    }

    private val animales = listOf(
        R.drawable.bobesponja,
        R.drawable.gary,
        R.drawable.patricio
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageButton.setOnClickListener {
            val indice = animales.random()
            imageButton.setImageResource(indice)
        }
    }
}