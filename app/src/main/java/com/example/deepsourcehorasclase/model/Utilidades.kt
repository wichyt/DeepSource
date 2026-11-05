package com.example.deepsourcehorasclase.model

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Este es el bloque que convierte los minutos al formato "2h 30m"
fun formatearHoras(minutos: Int): String {
    val horas = minutos / 60
    val resto = minutos % 60
    val textoMinutos = if (resto < 10) "0$resto" else "$resto"
    return "${horas}h ${textoMinutos}m"
}

//Este es el bloque que entrega la fecha de hoy, por ejemplo "2026-10-29"
fun fechaDeHoy(): String {
    val formato = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formato.format(Date())
}
