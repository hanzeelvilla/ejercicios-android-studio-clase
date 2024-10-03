package com.hanzeel.practicamiperfiloficial

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class UserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.userActivity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val name = intent.getStringExtra("NAME")
        val city = intent.getStringExtra("CITY")
        val gender = intent.getStringExtra("GENDER")
        val hobbies = intent.getStringExtra("HOBBIES")
        val imageUri = intent.getStringExtra("IMAGE_URI")

        findViewById<TextView>(R.id.tvUserName).text = name
        findViewById<TextView>(R.id.tvUserCity).text = city
        findViewById<TextView>(R.id.tvUserGender).text = gender
        findViewById<TextView>(R.id.tvUserHobbies).text = hobbies

        if (imageUri.isNullOrEmpty().not()) {
            val uri = Uri.parse(imageUri)
            findViewById<ImageView>(R.id.ivProfileImage).setImageURI(uri)
        }
    }
}