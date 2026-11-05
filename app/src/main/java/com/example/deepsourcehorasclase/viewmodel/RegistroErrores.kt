package com.example.deepsourcehorasclase.viewmodel

//Este es el bloque del modelo de errores del formulario
data class RegistroErrores(
    val proyecto: String? = null,
    val tarea: String? = null,
    val horas: String? = null,
    val minutos: String? = null,
    val duracion: String? = null
)
