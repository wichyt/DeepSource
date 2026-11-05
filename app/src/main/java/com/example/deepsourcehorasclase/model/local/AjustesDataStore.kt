package com.example.deepsourcehorasclase.model.local

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//Este es el bloque que crea el DataStore con nombre 'ajustes'
val Context.dataStore by preferencesDataStore(name = "ajustes")

//Este es el bloque de la clave del modo oscuro
private val MODO_OSCURO = booleanPreferencesKey("modo_oscuro")

//Este es el bloque que guarda el modo oscuro
suspend fun guardarModoOscuro(context: Context, activo: Boolean) {
    context.dataStore.edit { it[MODO_OSCURO] = activo }
}

//Este es el bloque que lee el modo oscuro
fun leerModoOscuro(context: Context): Flow<Boolean> =
    context.dataStore.data.map { it[MODO_OSCURO] ?: false }
