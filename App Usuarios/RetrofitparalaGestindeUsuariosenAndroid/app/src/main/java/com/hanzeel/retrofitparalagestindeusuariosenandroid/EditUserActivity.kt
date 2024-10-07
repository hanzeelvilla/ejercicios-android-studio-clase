package com.hanzeel.retrofitparalagestindeusuariosenandroid

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class EditUserActivity : AppCompatActivity() {
    private lateinit var editNombreEditText: EditText
    private lateinit var editUsuarioEditText: EditText
    private lateinit var editContrasenaEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    private var userId: Int = 0 // Variable to store the user ID


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize UI elements
        editNombreEditText = findViewById(R.id.editNombreEditText)
        editUsuarioEditText = findViewById(R.id.editUsuarioEditText)
        editContrasenaEditText = findViewById(R.id.editContrasenaEditText)
        updateButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)

        // Retrieve the user ID from the intent
        userId = intent.getIntExtra("USER_ID", 0)

        // Fetch user details and populate the fields
        fetchUserDetails(userId)

        // Set the update button click listener
        updateButton.setOnClickListener {
            updateUserDetails(userId)
        }

        // Set the delete button click listener
        deleteButton.setOnClickListener {
            deleteUser(userId)
        }
    }

    private fun fetchUserDetails(userId: Int) {
        // Lanzar una corrutina usando lifecycleScope
        lifecycleScope.launch {
            try {
                // Cambiar el contexto a IO para hacer la llamada de red
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.api.getUserById(id = userId)
                }

                // Verificar si la respuesta fue exitosa
                if (response.isSuccessful) {
                    response.body()?.let { user ->
                        // Rellenar los campos EditText con los datos del usuario
                        editNombreEditText.setText(user.nombre)
                        editUsuarioEditText.setText(user.usuario)
                        editContrasenaEditText.setText(user.contrasena)
                    } ?: run {
                        Toast.makeText(this@EditUserActivity, "User not found", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@EditUserActivity, "Failed to fetch user data", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@EditUserActivity, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun updateUserDetails(userId: Int) {
        val nombre = editNombreEditText.text.toString()
        val usuario = editUsuarioEditText.text.toString()
        val contrasena = editContrasenaEditText.text.toString()

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.updateUser(id=userId, nombre=nombre, usuario=usuario, contrasena=contrasena)
                if (response.isSuccessful) {
                    Toast.makeText(this@EditUserActivity, "Usuario actualizado con éxito", Toast.LENGTH_SHORT).show()
                    finish() // Cierra la actividad después de actualizar
                } else {
                    Toast.makeText(this@EditUserActivity, "Error al actualizar usuario", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Log.e("EditUserActivity", "Error HTTP al actualizar usuario: ${e.code()} - ${e.message()}")
                Toast.makeText(this@EditUserActivity, "Error al actualizar usuario: ${e.message()}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("EditUserActivity", "Error al conectar con el backend", e)
                Toast.makeText(this@EditUserActivity, "Error al conectar con el backend: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun deleteUser(userId: Int) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.api.deleteUser(id=userId)
                if (response.isSuccessful) {
                    Toast.makeText(this@EditUserActivity, "Usuario eliminado con éxito", Toast.LENGTH_SHORT).show()
                    finish() // Cierra la actividad después de eliminar
                } else {
                    Toast.makeText(this@EditUserActivity, "Error al eliminar usuario", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Log.e("EditUserActivity", "Error HTTP al eliminar usuario: ${e.code()} - ${e.message()}")
                Toast.makeText(this@EditUserActivity, "Error al eliminar usuario: ${e.message()}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("EditUserActivity", "Error al conectar con el backend", e)
                Toast.makeText(this@EditUserActivity, "Error al conectar con el backend: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}