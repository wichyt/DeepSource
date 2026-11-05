package com.example.deepsourcehorasclase.view.screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.deepsourcehorasclase.view.components.BarraInferior
import com.example.deepsourcehorasclase.view.components.ImagenInteligente
import com.example.deepsourcehorasclase.viewmodel.AjustesViewModel
import com.example.deepsourcehorasclase.viewmodel.PerfilViewModel
import kotlinx.coroutines.launch

//Este es el bloque de la pantalla Perfil
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController,
    viewModel: PerfilViewModel,
    ajustesViewModel: AjustesViewModel
) {
    val foto by viewModel.foto.collectAsState()
    val mostrarMensaje by viewModel.mostrarMensaje.collectAsState()
    val modoOscuro by ajustesViewModel.modoOscuro.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    //Este es el bloque que abre la galería con el Photo Picker
    val lanzadorGaleria = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        viewModel.onFotoElegida(uri)
    }

    //Este es el bloque que abre la cámara
    val lanzadorCamara = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { exito ->
        viewModel.onFotoTomada(exito)
    }

    //Este es el bloque que pide el permiso de la cámara
    val lanzadorPermiso = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { concedido ->
        if (concedido) {
            lanzadorCamara.launch(viewModel.crearUriParaCamara())
        } else {
            scope.launch {
                snackbarHostState.showSnackbar("Sin cámara, elige una foto de la galería")
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Perfil") }) },
        bottomBar = { BarraInferior(navController, "perfil") },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            //Este es el bloque de la foto de perfil
            ImagenInteligente(foto = foto)

            Spacer(modifier = Modifier.height(24.dp))

            //Este es el bloque de los botones de galería y cámara
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(onClick = {
                    lanzadorGaleria.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }) {
                    Text("Galería")
                }
                Button(onClick = { lanzadorPermiso.launch(Manifest.permission.CAMERA) }) {
                    Text("Cámara")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            //Este es el bloque del Switch del modo oscuro
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Modo oscuro", modifier = Modifier.weight(1f))
                Switch(
                    checked = modoOscuro,
                    onCheckedChange = { ajustesViewModel.cambiarModoOscuro(it) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            //Este es el bloque del mensaje animado
            AnimatedVisibility(visible = mostrarMensaje) {
                Text(
                    text = "¡Foto actualizada!",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
