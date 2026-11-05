package com.example.deepsourcehorasclase.model

//Este es el bloque de los resultados posibles del login
sealed class LoginResult{
    data class Success(val token: String, val userId: String) : LoginResult()
    data class Error(val exception: Exception) : LoginResult()
}
