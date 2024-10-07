package com.hanzeel.retrofitparalagestindeusuariosenandroid

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class AddUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            val nombre = findViewById<EditText>(R.id.nombreEditText).text.toString()
            val usuario = findViewById<EditText>(R.id.usuarioEditText).text.toString()
            val contrasena = findViewById<EditText>(R.id.contrasenaEditText).text.toString()

            // Validar los campos antes de hacer la llamada a la API
            if (nombre.isNotEmpty() && usuario.isNotEmpty() && contrasena.isNotEmpty()) {
                // Usar lifecycleScope para hacer la llamada en una corrutina
                lifecycleScope.launch {
                    try {
                        // Llamada al API en el contexto de Dispatchers.IO
                        val response = RetrofitClient.api.createUser(
                            nombre = nombre,
                            usuario = usuario,
                            contrasena = contrasena
                        )

                        // Verificar si la respuesta fue exitosa
                        if (response.isSuccessful) {
                            Toast.makeText(this@AddUserActivity, "Usuario añadido exitosamente", Toast.LENGTH_SHORT).show()
                            finish() // Cerrar la actividad y volver a la pantalla principal
                        } else {
                            Toast.makeText(this@AddUserActivity, "Error al añadir el usuario", Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        // Manejar cualquier excepción, como errores de red o del backend
                        Toast.makeText(this@AddUserActivity, "Fallo la coneción con el backend: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this@AddUserActivity, "Por favor captura todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}