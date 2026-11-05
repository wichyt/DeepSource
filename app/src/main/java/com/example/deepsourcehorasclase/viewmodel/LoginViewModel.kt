package com.example.deepsourcehorasclase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deepsourcehorasclase.model.repository.AuthRepository
import com.example.deepsourcehorasclase.model.LoginResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//Este es el bloque del ViewModel del login
class LoginViewModel: ViewModel() {
   private val repository = AuthRepository()

    //Este es el bloque de los estados del login
    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _loginMessage = MutableStateFlow("")
    val loginMessage: StateFlow<String> = _loginMessage.asStateFlow()

    private val _loginExitoso = MutableStateFlow(false)
    val loginExitoso: StateFlow<Boolean> = _loginExitoso.asStateFlow()

    //Este es el bloque de los errores de cada campo
    private val _emailError = MutableStateFlow<String?>(null)
    val emailError: StateFlow<String?> = _emailError.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError: StateFlow<String?> = _passwordError.asStateFlow()

    //Este es el bloque que guarda lo que se escribe en los campos
    fun onEmailChange(email: String){
        _email.value = email
        _emailError.value = null
    }
    fun onPasswordChange(password: String){
        _password.value = password
        _passwordError.value = null
    }

    //Este es el bloque de validaciones
    fun validarFormulario(): Boolean {
        _emailError.value = if (email.value.isBlank() || !email.value.contains("@")) "Ingresa un correo válido" else null
        _passwordError.value = if (password.value.isEmpty()) "Ingresa tu contraseña" else null
        return _emailError.value == null && _passwordError.value == null
    }

    //Este es el bloque que inicia sesión
    fun onLoginClick(){
        if (!validarFormulario()) return

        viewModelScope.launch {
            _isLoading.value = true
            _isError.value = false
            when(val result = repository.loginUser(email.value, password.value)){
            is LoginResult.Success -> {
                _loginMessage.value = "Ingreso correcto"
                _loginExitoso.value = true
            }
                is LoginResult.Error -> {
                    _loginMessage.value = "Correo o contraseña incorrectos"
                    _isError.value = true
                }
            }
            _isLoading.value = false
        }
    }
}
