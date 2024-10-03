package com.hanzeel.sqlitecrud

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Crear la base de datos
        database = openOrCreateDatabase("mi_base_de_datos", Context.MODE_PRIVATE, null)

        // Crear la tabla
        database.execSQL("CREATE TABLE IF NOT EXISTS usuarios (id INTEGER PRIMARY KEY, nombre TEXT, correo TEXT)")

        // Agregar código para crear registros
        val botonCrear = findViewById<Button>(R.id.botonCrear)
        botonCrear.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.editTextNombre).text.toString()
            val correo = findViewById<EditText>(R.id.editTextCorreo).text.toString()

            // Insertar el registro
            database.execSQL("INSERT INTO usuarios (nombre, correo) VALUES ('$nombre','$correo')")
            Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show()

            // Limpiar los campos
            findViewById<EditText>(R.id.editTextNombre).text.clear()
            findViewById<EditText>(R.id.editTextCorreo).text.clear()
        }

        // Agregar código para leer registros
        val botonLeer = findViewById<Button>(R.id.botonLeer)
        botonLeer.setOnClickListener {
            // Consultar todos los registros
            val cursor = database.rawQuery("SELECT * FROM usuarios", null)

            // Recorrer los registros
            while (cursor.moveToNext()) {
                val id = cursor.getInt(0)
                val nombre = cursor.getString(1)
                val correo = cursor.getString(2)

                // Mostrar el registro
                println("ID: $id, Nombre: $nombre, Correo: $correo")
            }

            // Cerrar el cursor
            cursor.close()
        }

        // Agregar código para actualizar registros
        val botonActualizar = findViewById<Button>(R.id.botonActualizar)
        botonActualizar.setOnClickListener {
            val id = findViewById<EditText>(R.id.editTextId).text.toString().toInt()
            val nombre = findViewById<EditText>(R.id.editTextNombre).text.toString()
            val correo = findViewById<EditText>(R.id.editTextCorreo).text.toString()

            // Actualizar el registro
            database.execSQL("UPDATE usuarios SET nombre='$nombre', correo='$correo' WHERE id=$id")
        }

        // Agregar código para eliminar registros
        val botonEliminar = findViewById<Button>(R.id.botonEliminar)
        botonEliminar.setOnClickListener {
            val id = findViewById<EditText>(R.id.editTextId).text.toString().toInt()

            // Eliminar el registro
            database.execSQL("DELETE FROM usuarios WHERE id=$id")
        }
    }
}