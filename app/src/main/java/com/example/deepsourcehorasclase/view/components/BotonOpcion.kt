package com.example.deepsourcehorasclase.view.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

//Este es el bloque del botón para elegir una opción
@Composable
fun BotonOpcion(
    texto: String,
    elegido: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    //Este es el bloque de los colores animados del botón
    val colorFondo by animateColorAsState(
        targetValue = if (elegido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
        animationSpec = tween(durationMillis = 500), label = ""
    )
    val colorTexto by animateColorAsState(
        targetValue = if (elegido) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
        animationSpec = tween(durationMillis = 500), label = ""
    )

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = colorFondo, contentColor = colorTexto),
        modifier = modifier
    ) {
        Text(texto)
    }
}
