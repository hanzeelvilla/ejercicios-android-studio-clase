package com.hanzeel.calendarioactividades

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter

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
        val et2=findViewById<EditText>(R.id.et2)
        val boton1=findViewById<Button>(R.id.boton1)
        val boton2=findViewById<Button>(R.id.boton2)

        boton1.setOnClickListener {
            val nomarchivo = et1.text.toString().replace('/','-')
            try {
                val archivo = OutputStreamWriter(openFileOutput(nomarchivo,
                    Activity.MODE_PRIVATE))
                archivo.write(et2.text.toString())
                archivo.flush()
                archivo.close()
            } catch (e: IOException) {
            }
            Toast.makeText(this, "Los datos fueron grabados", Toast.LENGTH_SHORT).show()
            et1.setText("")
            et2.setText("")
        }


        boton2.setOnClickListener {
            var nomarchivo = et1.text.toString().replace('/', '-')
            if (fileList().contains(nomarchivo)) {
                try {
                    val archivo = InputStreamReader(openFileInput(nomarchivo))
                    val br = BufferedReader(archivo)
                    var linea = br.readLine()
                    val todo = StringBuilder()
                    while (linea != null) {
                        todo.append(linea + "\n")
                        linea = br.readLine()
                    }
                    br.close()

                    archivo.close()
                    et2.setText(todo)
                } catch (e: IOException) {
                }
            } else {
                Toast.makeText(this, "No hay datos grabados para dicha fecha",
                    Toast.LENGTH_LONG).show()
                et2.setText("")
            }
        }
    }
}