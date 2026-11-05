package com.example.deepsourcehorasclase.view.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

//Este es el bloque del botón Registrar animado
@Composable
fun BotonRegistrar(
    texto: String,
    listo: Boolean,
    guardando: Boolean,
    onClick: () -> Unit
) {
    //Este es el bloque del color animado: gris si falta algo y morado si está listo
    val color by animateColorAsState(
        if (listo) MaterialTheme.colorScheme.primary else Color.Gray, label = "color"
    )

    //Este es el bloque del alto animado: el botón crece cuando está listo
    val alto by animateDpAsState(
        if (listo) 60.dp else 48.dp, label = "alto"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        contentAlignment = Alignment.Center
    ) {
        //Este es el bloque del loader mientras se guarda
        if (guardando) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = color),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(alto)
            ) {
                Text(texto)
            }
        }
    }
}
