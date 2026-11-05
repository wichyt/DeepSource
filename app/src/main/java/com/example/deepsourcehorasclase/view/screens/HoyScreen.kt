package com.example.deepsourcehorasclase.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.deepsourcehorasclase.model.fechaDeHoy
import com.example.deepsourcehorasclase.model.formatearHoras
import com.example.deepsourcehorasclase.view.components.BarraInferior
import com.example.deepsourcehorasclase.viewmodel.HorasViewModel

//Este es el bloque de la pantalla Hoy
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HoyScreen(
    navController: NavController,
    viewModel: HorasViewModel
) {
    val registros by viewModel.registros.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    //Este es el bloque que muestra el Snackbar
    LaunchedEffect(mensaje) {
        if (mensaje.isNotEmpty()) {
            snackbarHostState.showSnackbar(mensaje)
            viewModel.limpiarMensaje()
        }
    }

    //Este es el bloque que deja solo los registros de hoy
    val hoy = fechaDeHoy()
    val registrosDeHoy = registros.filter { it.fecha == hoy }

    //Este es el bloque que calcula el total del día
    var total = 0
    for (registro in registrosDeHoy) {
        total += registro.minutos
    }

    //Este es el bloque de la jornada
    val jornadaMinutos = 540
    val faltan = jornadaMinutos - total
    val textoJornada = if (faltan > 0) "de 9h de jornada · faltan ${formatearHoras(faltan)}" else "de 9h de jornada · jornada completa"

    Scaffold(
        topBar = { TopAppBar(title = { Text("Hoy") }) },
        bottomBar = { BarraInferior(navController, "hoy") },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            //Este es el bloque del resumen del día
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = "Registrado hoy",
                    color = MaterialTheme.colorScheme.onSurfaceVariant)

                Text(text = formatearHoras(total),
                    style = MaterialTheme.typography.displaySmall)

                Text(text = textoJornada,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)

                Text(text = "Registros de hoy",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 16.dp))

                if (registrosDeHoy.isEmpty()) {
                    Text(text = "Aún no registras horas hoy.",
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }

            //Este es el bloque de la lista de registros
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 8.dp)
            ) {
                items(registrosDeHoy, key = { it.id }) { registro ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = registro.tarea)
                            Text(text = "${registro.proyecto} · ${registro.tipoHora}",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Text(text = formatearHoras(registro.minutos))

                        //Este es el bloque del botón para borrar el registro
                        IconButton(onClick = { viewModel.eliminarRegistro(registro) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Borrar registro")
                        }
                    }
                }
            }

            //Este es el bloque del botón Registrar horas
            Button(
                onClick = { navController.navigate("registro") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar horas")
            }
        }
    }
}
