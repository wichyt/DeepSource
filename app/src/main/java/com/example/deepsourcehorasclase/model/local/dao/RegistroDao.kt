package com.example.deepsourcehorasclase.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.deepsourcehorasclase.model.local.entity.RegistroEntity
import kotlinx.coroutines.flow.Flow

//Este es el bloque del DAO con las operaciones de los registros
@Dao
interface RegistroDao {

    //Este es el bloque que agrega un registro (Create)
    @Insert
    suspend fun insertar(registro: RegistroEntity)

    //Este es el bloque que lee todos los registros (Read)
    @Query("SELECT * FROM registros ORDER BY id")
    fun obtenerTodos(): Flow<List<RegistroEntity>>

    //Este es el bloque que borra un registro (Delete)
    @Delete
    suspend fun eliminar(registro: RegistroEntity)
}
