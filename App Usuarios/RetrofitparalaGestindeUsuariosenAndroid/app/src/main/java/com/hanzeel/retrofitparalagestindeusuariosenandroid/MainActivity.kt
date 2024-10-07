package com.hanzeel.retrofitparalagestindeusuariosenandroid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {
    private lateinit var userAdapter: UserAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.recyclerView)
        userAdapter = UserAdapter { user -> userClicked(user) }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        // Fetch users from the backend
        fetchUsers()

        // Floating button to add new user
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }

        // Configuración del SearchView
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false // No necesitas manejar el evento de envío en este caso
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                userAdapter.filter(newText ?: "") // Filtrar cuando cambie el texto
                return true
            }
        })
    }

    // Función para obtener los usuarios
    private fun fetchUsers() {
        lifecycleScope.launch {
            try {
                val users = RetrofitClient.api.getUsers()
                userAdapter.setUsers(users)
            } catch (e: HttpException) {
                Log.e("MainActivity", "Error HTTP al obtener usuarios: ${e.code()} - ${e.message()}")
                Toast.makeText(this@MainActivity, "Error al obtener usuarios: ${e.message()}", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e("MainActivity", "Error al conectar con el backend", e)
                Toast.makeText(this@MainActivity, "Error al conectar con el backend: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun userClicked(user: User) {
        val intent = Intent(this, EditUserActivity::class.java)
        intent.putExtra("USER_ID", user.id)
        startActivity(intent)
    }

    // Sobrescribir onResume para actualizar los datos cuando regresas de EditUserActivity
    override fun onResume() {
        super.onResume()
        // Llamar nuevamente a fetchUsers para actualizar la lista de usuarios
        fetchUsers()
    }
}