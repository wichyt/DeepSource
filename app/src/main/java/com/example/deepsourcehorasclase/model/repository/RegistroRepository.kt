package com.example.deepsourcehorasclase.model.repository

import com.example.deepsourcehorasclase.model.local.dao.RegistroDao
import com.example.deepsourcehorasclase.model.local.entity.RegistroEntity

//Este es el bloque del repositorio de los registros
class RegistroRepository(private val dao: RegistroDao) {

    val registros = dao.obtenerTodos()

    suspend fun agregar(r: RegistroEntity) = dao.insertar(r)

    suspend fun eliminar(r: RegistroEntity) = dao.eliminar(r)
}
