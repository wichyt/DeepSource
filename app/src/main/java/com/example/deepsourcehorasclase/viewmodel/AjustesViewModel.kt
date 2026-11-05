package com.example.deepsourcehorasclase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.deepsourcehorasclase.model.local.guardarModoOscuro
import com.example.deepsourcehorasclase.model.local.leerModoOscuro
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//Este es el bloque del ViewModel de los ajustes
class AjustesViewModel(application: Application) : AndroidViewModel(application) {

    //Este es el bloque del modo oscuro guardado en DataStore
    val modoOscuro = leerModoOscuro(application).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), false
    )

    //Este es el bloque que guarda el modo oscuro cuando se cambia el Switch
    fun cambiarModoOscuro(activo: Boolean) {
        viewModelScope.launch {
            guardarModoOscuro(getApplication(), activo)
        }
    }
}
