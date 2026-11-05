package com.example.deepsourcehorasclase.viewmodel

import android.app.Application
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File

//Este es el bloque del ViewModel del perfil
class PerfilViewModel(application: Application) : AndroidViewModel(application) {

    //Este es el bloque de la foto elegida o tomada
    private val _foto = MutableStateFlow<Uri?>(null)
    val foto: StateFlow<Uri?> = _foto

    //Este es el bloque del mensaje animado
    private val _mostrarMensaje = MutableStateFlow(false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje

    //Este es el bloque de la dirección donde la cámara guarda la foto
    private var uriCamara: Uri? = null

    //Este es el bloque que crea el archivo para la foto de la cámara
    fun crearUriParaCamara(): Uri {
        val context = getApplication<Application>()
        val archivo = File(context.cacheDir, "foto_${System.currentTimeMillis()}.jpg")
        val uri = FileProvider.getUriForFile(context, "${context.packageName}.provider", archivo)
        uriCamara = uri
        return uri
    }

    //Este es el bloque que recibe la foto de la galería
    fun onFotoElegida(uri: Uri?) {
        if (uri != null) {
            _foto.value = uri
            mostrarMensajeAnimado()
        }
    }

    //Este es el bloque que recibe la foto de la cámara
    fun onFotoTomada(exito: Boolean) {
        if (exito && uriCamara != null) {
            _foto.value = uriCamara
            mostrarMensajeAnimado()
        }
    }

    //Este es el bloque que muestra el mensaje por 2 segundos
    private fun mostrarMensajeAnimado() {
        viewModelScope.launch {
            _mostrarMensaje.value = true
            delay(2000)
            _mostrarMensaje.value = false
        }
    }
}
