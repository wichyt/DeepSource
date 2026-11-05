package com.example.deepsourcehorasclase.model.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.deepsourcehorasclase.model.local.dao.RegistroDao
import com.example.deepsourcehorasclase.model.local.entity.RegistroEntity

//Este es el bloque de la base de datos de la app
@Database(entities = [RegistroEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun registroDao(): RegistroDao

    //Este es el bloque que crea una sola base de datos para toda la app
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "horas.db"
                ).build().also { INSTANCE = it }
            }
    }
}
