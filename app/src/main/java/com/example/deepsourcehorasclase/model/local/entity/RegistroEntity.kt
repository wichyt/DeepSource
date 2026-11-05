package com.example.deepsourcehorasclase.model.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

//Este es el bloque de la tabla de los registros de horas
@Entity(tableName = "registros")
data class RegistroEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fecha: String,
    val proyecto: String,
    val tarea: String,
    val minutos: Int,
    val tipoHora: String,
    val descripcion: String
)
