package com.example.deepsourcehorasclase.model.repository

import com.example.deepsourcehorasclase.model.LoginResult
import kotlinx.coroutines.withContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers

//Este es el bloque del repositorio del login
class AuthRepository{
    //Este es el bloque que revisa el correo y la contraseña
    suspend fun loginUser(email: String, password: String): LoginResult = withContext(Dispatchers.IO) {
        try {
            delay(2000)
            if (email == "luis.gomez@deepsource.cl" && password == "horas123") {
                LoginResult.Success("fake-jwt-123", "luis.gomez")
            }else {
                LoginResult.Error(Exception("Correo o contraseña incorrectos"))
            }

        }catch (e: Exception){
            LoginResult.Error(e)
        }
    }
}
