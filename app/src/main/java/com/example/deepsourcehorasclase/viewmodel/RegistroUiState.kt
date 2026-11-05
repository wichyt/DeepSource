package com.example.deepsourcehorasclase.viewmodel

//Este es el bloque del modelo del formulario
data class RegistroUiState(
    val proyecto: String = "",
    val tarea: String = "",
    val horas: String = "",
    val minutos: String = "",
    val tipoHora: String = "Regular",
    val descripcion: String = "",
    val guardando: Boolean = false,
    val guardado: Boolean = false,
    val errores: RegistroErrores = RegistroErrores()
)
