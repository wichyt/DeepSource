package com.example.deepsourcehorasclase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.deepsourcehorasclase.model.fechaDeHoy
import com.example.deepsourcehorasclase.model.formatearHoras
import com.example.deepsourcehorasclase.model.local.AppDatabase
import com.example.deepsourcehorasclase.model.local.entity.RegistroEntity
import com.example.deepsourcehorasclase.model.repository.RegistroRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//Este es el bloque del ViewModel compartido por Hoy y Registrar horas
class HorasViewModel(application: Application) : AndroidViewModel(application) {

    //Este es el bloque del repositorio que guarda los registros en Room
    private val repository = RegistroRepository(AppDatabase.get(application).registroDao())

    //Este es el bloque del estado del formulario
    private val _estado = MutableStateFlow(RegistroUiState())
    val estado: StateFlow<RegistroUiState> = _estado

    //Este es el bloque de los registros guardados en Room
    val registros = repository.registros.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList()
    )

    //Este es el bloque del mensaje del Snackbar
    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    //Este es el bloque que actualiza el proyecto y borra la tarea
    fun onProyectoChange(valor: String) {
        _estado.update { it.copy(proyecto = valor, tarea = "", errores = it.errores.copy(proyecto = null, tarea = null)) }
    }

    //Este es el bloque que actualiza los demás campos
    fun onTareaChange(valor: String) {
        _estado.update { it.copy(tarea = valor, errores = it.errores.copy(tarea = null)) }
    }

    fun onHorasChange(valor: String) {
        _estado.update { it.copy(horas = valor, errores = it.errores.copy(horas = null, duracion = null)) }
    }

    fun onMinutosChange(valor: String) {
        _estado.update { it.copy(minutos = valor, errores = it.errores.copy(minutos = null, duracion = null)) }
    }

    fun onTipoHoraChange(valor: String) {
        _estado.update { it.copy(tipoHora = valor) }
    }

    fun onDescripcionChange(valor: String) {
        _estado.update { it.copy(descripcion = valor) }
    }

    //Este es el bloque que suma las horas y los minutos
    fun calcularMinutos(estado: RegistroUiState): Int {
        var total = 0
        val horas = estado.horas.toIntOrNull()
        val minutos = estado.minutos.toIntOrNull()
        if (horas != null && horas >= 0 && horas <= 24) total += horas * 60
        if (minutos != null && minutos >= 0 && minutos <= 59) total += minutos
        return total
    }

    //Este es el bloque de validaciones
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val horas = estadoActual.horas.toIntOrNull()
        val minutos = estadoActual.minutos.toIntOrNull()

        //Este es el bloque que valida las horas
        var errorHoras: String? = null
        if (estadoActual.horas.isNotEmpty() && horas == null) {
            errorHoras = "Escribe solo números"
        } else if (horas != null && (horas < 0 || horas > 24)) {
            errorHoras = "Usa un valor entre 0 y 24"
        }

        //Este es el bloque que valida los minutos
        var errorMinutos: String? = null
        if (estadoActual.minutos.isNotEmpty() && minutos == null) {
            errorMinutos = "Escribe solo números"
        } else if (minutos != null && (minutos < 0 || minutos > 59)) {
            errorMinutos = "Usa un valor entre 0 y 59"
        }

        //Este es el bloque que valida el total
        var errorDuracion: String? = null
        if (errorHoras == null && errorMinutos == null) {
            val total = calcularMinutos(estadoActual)
            if (total == 0) {
                errorDuracion = "Registra al menos 1 minuto"
            } else if (total > 24 * 60) {
                errorDuracion = "No puedes registrar más de 24 horas"
            }
        }

        //Este es el bloque que junta todos los errores
        val errores = RegistroErrores(
            proyecto = if (estadoActual.proyecto.isBlank()) "Elige un proyecto" else null,
            tarea = if (estadoActual.tarea.isBlank()) "Elige una tarea" else null,
            horas = errorHoras,
            minutos = errorMinutos,
            duracion = errorDuracion
        )

        val hayErrores = listOfNotNull(
            errores.proyecto,
            errores.tarea,
            errores.horas,
            errores.minutos,
            errores.duracion
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }

        return !hayErrores
    }

    //Este es el bloque que guarda el registro
    fun guardarRegistro() {
        if (!validarFormulario()) return

        val estadoActual = _estado.value
        val total = calcularMinutos(estadoActual)
        val nuevo = RegistroEntity(
            fecha = fechaDeHoy(),
            proyecto = estadoActual.proyecto,
            tarea = estadoActual.tarea,
            minutos = total,
            tipoHora = estadoActual.tipoHora,
            descripcion = estadoActual.descripcion
        )

        //Este es el bloque que guarda el registro en Room mostrando el loader
        viewModelScope.launch {
            _estado.update { it.copy(guardando = true) }
            delay(700)
            repository.agregar(nuevo)
            _mensaje.value = "Registraste ${formatearHoras(total)} en ${estadoActual.proyecto}"
            _estado.value = RegistroUiState(guardado = true)
        }
    }

    //Este es el bloque que avisa que ya se volvió a Hoy
    fun limpiarGuardado() {
        _estado.update { it.copy(guardado = false) }
    }

    //Este es el bloque que borra un registro de Room
    fun eliminarRegistro(registro: RegistroEntity) {
        viewModelScope.launch {
            repository.eliminar(registro)
        }
    }

    //Este es el bloque que limpia el formulario
    fun limpiarFormulario() {
        _estado.value = RegistroUiState()
    }

    //Este es el bloque que limpia el mensaje
    fun limpiarMensaje() {
        _mensaje.value = ""
    }
}
