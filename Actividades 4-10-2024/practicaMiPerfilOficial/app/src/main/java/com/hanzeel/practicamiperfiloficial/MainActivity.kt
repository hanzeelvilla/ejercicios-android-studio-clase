package com.hanzeel.practicamiperfiloficial

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.ListView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    // Variables de clase para poder acceder a ellas desde otros métodos
    private lateinit var hobbiesList: MutableList<String>
    private lateinit var hobbiesAdapter: ArrayAdapter<String>

    val pickMedia = registerForActivityResult(PickVisualMedia()) { uri ->
        if(uri != null) {
            ivImage.setImageURI(uri)
            ivImage.tag = uri.toString() // Guardar el URI en el tag del ImageView
        } else {
            Log.i("MainActivity", "No image selected")
        }
    }

    lateinit var btnImg : Button
    lateinit var ivImage : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinnerCity = findViewById<Spinner>(R.id.spinner_city)
        val cities = arrayOf(
            "Armería",
            "Colima",
            "Comala",
            "Coquimatlán",
            "Cuauhtémoc",
            "Ixtlahuacán",
            "Manzanillo",
            "Minatitlán",
            "Tecomán",
            "Villa de Álvarez"
        )
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, cities)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCity.adapter = adapterSpinner

        val lv_hobbies = findViewById<ListView>(R.id.lv_hobbies)
        hobbiesList = mutableListOf() // Inicializa la lista de hobbies
        hobbiesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, hobbiesList)
        lv_hobbies.adapter = hobbiesAdapter

        // Ajustar altura del ListView según los elementos
        adjustListViewHeight(lv_hobbies)

        val cbSports = findViewById<CheckBox>(R.id.cb_sports)
        val cbMusic = findViewById<CheckBox>(R.id.cb_music)
        val cbBooks = findViewById<CheckBox>(R.id.cb_books)
        val cbOtros = findViewById<CheckBox>(R.id.cb_otros)

        // Función que maneja agregar/quitar un hobby de la lista según el estado del CheckBox
        fun manageHobby(checkBox: CheckBox, hobby: String) {
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    // Agrega el hobby si está marcado
                    if (!hobbiesList.contains(hobby)) {
                        hobbiesList.add(hobby)
                    }
                } else {
                    // Elimina el hobby si está desmarcado
                    hobbiesList.remove(hobby)
                }
                // Notifica al adapter que la lista ha cambiado y ajusta la altura del ListView
                hobbiesAdapter.notifyDataSetChanged()
                adjustListViewHeight(lv_hobbies)
            }
        }

        // Llamamos a la función para cada CheckBox
        manageHobby(cbSports, "Deportes")
        manageHobby(cbMusic, "Música")
        manageHobby(cbBooks, "Libros")
        manageHobby(cbOtros, "Otros")

        btnImg = findViewById(R.id.btnImg)
        ivImage = findViewById(R.id.galleryImg)

        btnImg.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
        }

        val btnCreateUser = findViewById<Button>(R.id.btnCreateUser)
        btnCreateUser.setOnClickListener {
            val name = findViewById<EditText>(R.id.et_name).text.toString()
            if (name.isBlank()) {
                // Mostrar un mensaje de error si el nombre está vacío
                Toast.makeText(this, "El nombre no puede estar vacío", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val city = spinnerCity.selectedItem.toString()

            val gender = if (findViewById<RadioButton>(R.id.rb_male).isChecked) {
                "Masculino"
            } else if (findViewById<RadioButton>(R.id.rb_female).isChecked) {
                "Femenino"
            } else {
                // Mostrar un mensaje si no se selecciona el género
                Toast.makeText(this, "Selecciona un género", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val hobbies = if (hobbiesList.isNotEmpty()) {
                hobbiesList.joinToString(", ")
            } else {
                // Mostrar un mensaje si no se selecciona ningún hobby
                Toast.makeText(this, "Selecciona al menos una afición", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, UserActivity::class.java)

            intent.putExtra("NAME", name)
            intent.putExtra("CITY", city)
            intent.putExtra("GENDER", gender)
            intent.putExtra("HOBBIES", hobbies)

            // Si hay una imagen seleccionada, envíala
            val imagenUri = ivImage.tag?.toString()
            if (imagenUri != null) {
                intent.putExtra("IMAGE_URI", imagenUri)
            }

            startActivity(intent)
        }
    }

    // Función para ajustar la altura del ListView basado en sus elementos
    private fun adjustListViewHeight(listView: ListView) {
        val listAdapter: ListAdapter = listView.adapter ?: return
        var totalHeight = 0
        for (i in 0 until listAdapter.count) {
            val listItem: View = listAdapter.getView(i, null, listView)
            listItem.measure(
                View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            )
            totalHeight += listItem.measuredHeight
        }

        val params: ViewGroup.LayoutParams = listView.layoutParams
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.count - 1))
        listView.layoutParams = params
        listView.requestLayout()
    }
}
