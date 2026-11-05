package com.example.deepsourcehorasclase.view.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

//Este es el bloque del esquema de colores oscuro
private val DarkColorScheme = darkColorScheme(
    primary = AcentoOscuro,
    onPrimary = SobreAcentoOscuro,
    background = FondoOscuro,
    surface = FondoOscuro,
    onBackground = TextoOscuro,
    onSurface = TextoOscuro,
    onSurfaceVariant = Texto2Oscuro,
    outline = BordeOscuro,
    error = ErrorOscuro
)

//Este es el bloque del esquema de colores claro
private val LightColorScheme = lightColorScheme(
    primary = AcentoClaro,
    onPrimary = SobreAcentoClaro,
    background = FondoClaro,
    surface = FondoClaro,
    onBackground = TextoClaro,
    onSurface = TextoClaro,
    onSurfaceVariant = Texto2Claro,
    outline = BordeClaro,
    error = ErrorClaro
)

//Este es el bloque del tema de la app
@Composable
fun DeepSourceHorasTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
