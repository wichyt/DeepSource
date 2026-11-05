package com.example.deepsourcehorasclase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.deepsourcehorasclase.view.navigation.AppNavigation
import com.example.deepsourcehorasclase.view.theme.DeepSourceHorasTheme
import com.example.deepsourcehorasclase.viewmodel.AjustesViewModel

//Este es el bloque que abre la app
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            //Este es el bloque que lee el modo oscuro guardado
            val ajustesViewModel: AjustesViewModel = viewModel()
            val modoOscuro by ajustesViewModel.modoOscuro.collectAsState()

            DeepSourceHorasTheme(darkTheme = modoOscuro) {

                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    AppNavigation(ajustesViewModel)
                }
            }
        }
    }
}
