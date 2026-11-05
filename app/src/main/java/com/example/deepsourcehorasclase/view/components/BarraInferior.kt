package com.example.deepsourcehorasclase.view.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

//Este es el bloque de la barra inferior con Hoy y Perfil
@Composable
fun BarraInferior(navController: NavController, rutaActual: String) {
    val rutas = listOf("hoy", "perfil")

    NavigationBar {
        for (ruta in rutas) {
            NavigationBarItem(
                selected = rutaActual == ruta,
                onClick = {
                    navController.navigate(ruta) {
                        popUpTo("hoy")
                        launchSingleTop = true
                    }
                },
                label = { Text(if (ruta == "hoy") "Hoy" else "Perfil") },
                icon = {
                    Icon(
                        imageVector = if (ruta == "hoy") Icons.Default.Home else Icons.Default.Person,
                        contentDescription = null
                    )
                }
            )
        }
    }
}
