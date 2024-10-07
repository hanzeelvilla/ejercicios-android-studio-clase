package com.hanzeel.retrofitparalagestindeusuariosenandroid

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

data class User(
    val id: Int,
    val nombre: String,
    val usuario: String,
    val contrasena: String
)

interface UserApiService {
    @GET("/users")
    suspend fun getUsers(@Query("action") action: String = "read"): List<User>

    @GET("/users")
    suspend fun getUserById(
        @Query("action") action: String = "read",
        @Query("id") id: Int
    ): Response<User>

    @GET("/users")
    suspend fun createUser(
        @Query("action") action: String = "create",
        @Query("nombre") nombre: String,
        @Query("usuario") usuario: String,
        @Query("contrasena") contrasena: String
    ): Response<User>

    @GET("/users")
    suspend fun updateUser(
        @Query("action") action: String = "update",
        @Query("id") id: Int,
        @Query("nombre") nombre: String?,
        @Query("usuario") usuario: String?,
        @Query("contrasena") contrasena: String?
    ): Response<User>

    @GET("/users")
    suspend fun deleteUser(
        @Query("action") action: String = "delete",
        @Query("id") id: Int
    ): Response<Void>
}

object RetrofitClient {
    private const val BASE_URL = "https://bq5h2642-3000.usw3.devtunnels.ms/" //cambia esto por tu link

    val api: UserApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserApiService::class.java)
    }
}