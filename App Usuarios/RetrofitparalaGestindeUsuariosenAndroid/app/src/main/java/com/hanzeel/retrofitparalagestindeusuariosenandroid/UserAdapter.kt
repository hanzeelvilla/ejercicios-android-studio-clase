package com.hanzeel.retrofitparalagestindeusuariosenandroid

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val onClick: (User) -> Unit) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private var users = listOf<User>()
    private var filteredUsers = listOf<User>() // Lista auxiliar para los usuarios filtrados

    @SuppressLint("NotifyDataSetChanged")
    fun setUsers(users: List<User>) {
        this.users = users
        this.filteredUsers = users // Inicialmente, la lista filtrada es igual a la lista completa
        notifyDataSetChanged()
    }

    // Método para filtrar usuarios según una palabra clave
    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredUsers = if (query.isEmpty()) {
            users // Si no hay texto en la búsqueda, mostrar todos los usuarios
        } else {
            users.filter { user ->
                user.nombre.contains(query, ignoreCase = true) || // Filtrar por nombre
                        user.usuario.contains(query, ignoreCase = true)   // También se podría filtrar por username
            }
        }
        notifyDataSetChanged() // Refrescar el RecyclerView con los resultados filtrados
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = filteredUsers[position] // Usar la lista filtrada en lugar de la lista completa
        holder.bind(user)
        holder.itemView.setOnClickListener { onClick(user) }
    }

    override fun getItemCount(): Int = filteredUsers.size // Contar los usuarios filtrados

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userIdTextView: TextView = itemView.findViewById(R.id.userIdTextView)
        private val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        private val userUsuarioTextView: TextView = itemView.findViewById(R.id.userUsuarioTextView)
        private val userContrasenaTextView: TextView = itemView.findViewById(R.id.userContrasenaTextView)

        @SuppressLint("SetTextI18n")
        fun bind(user: User) {
            userIdTextView.text = "ID: ${user.id}"
            userNameTextView.text = "Nombre: ${user.nombre}"
            userUsuarioTextView.text = "Usuario: ${user.usuario}"
            userContrasenaTextView.text = "Contraseña: ${user.contrasena}"
        }
    }
}
