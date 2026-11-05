package com.example.deepsourcehorasclase.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.deepsourcehorasclase.model.formatearHoras
import com.example.deepsourcehorasclase.model.proyectosDePrueba
import com.example.deepsourcehorasclase.view.components.BotonOpcion
import com.example.deepsourcehorasclase.view.components.BotonRegistrar
import com.example.deepsourcehorasclase.view.components.CampoTexto
import com.example.deepsourcehorasclase.viewmodel.HorasViewModel

//Este es el bloque de la pantalla Registrar horas
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroHorasScreen(
    navController: NavController,
    viewModel: HorasViewModel
) {
    val estado by viewModel.estado.collectAsState()

    //Este es el bloque del texto del botón en tiempo real
    val total = viewModel.calcularMinutos(estado)
    val textoBoton = if (total > 0) "Registrar ${formatearHoras(total)}" else "Registrar"

    //Este es el bloque que dice si el formulario está listo
    val formularioListo by remember {
        derivedStateOf {
            estado.proyecto.isNotBlank() && estado.tarea.isNotBlank() && viewModel.calcularMinutos(estado) > 0
        }
    }

    //Este es el bloque que vuelve a Hoy cuando el registro se guardó
    LaunchedEffect(estado.guardado) {
        if (estado.guardado) {
            viewModel.limpiarGuardado()
            navController.popBackStack()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrar horas") },
                actions = {
                    //Este es el bloque del botón Cancelar
                    TextButton(onClick = {
                        viewModel.limpiarFormulario()
                        navController.popBackStack()
                    }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {

                //Este es el bloque del proyecto
                Text("Proyecto", style = MaterialTheme.typography.titleSmall)
                for (proyecto in proyectosDePrueba.keys) {
                    BotonOpcion(
                        texto = proyecto,
                        elegido = estado.proyecto == proyecto,
                        onClick = { viewModel.onProyectoChange(proyecto) },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                estado.errores.proyecto?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                //Este es el bloque de la tarea
                val tareas = proyectosDePrueba[estado.proyecto]
                if (tareas != null) {
                    Text("Tarea", style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(top = 8.dp))
                    for (tarea in tareas) {
                        BotonOpcion(
                            texto = tarea,
                            elegido = estado.tarea == tarea,
                            onClick = { viewModel.onTareaChange(tarea) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                estado.errores.tarea?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                //Este es el bloque de las horas y los minutos
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    CampoTexto(
                        valor = estado.horas,
                        error = estado.errores.horas,
                        label = "Horas",
                        onChange = viewModel::onHorasChange,
                        modifier = Modifier.width(150.dp)
                    )
                    CampoTexto(
                        valor = estado.minutos,
                        error = estado.errores.minutos,
                        label = "Minutos",
                        onChange = viewModel::onMinutosChange,
                        modifier = Modifier.width(150.dp)
                    )
                }
                estado.errores.duracion?.let {
                    Text(it, color = MaterialTheme.colorScheme.error)
                }

                //Este es el bloque del tipo de hora
                Text("Tipo de hora", style = MaterialTheme.typography.titleSmall)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    BotonOpcion(
                        texto = "Regular",
                        elegido = estado.tipoHora == "Regular",
                        onClick = { viewModel.onTipoHoraChange("Regular") }
                    )
                    BotonOpcion(
                        texto = "Extra",
                        elegido = estado.tipoHora == "Extra",
                        onClick = { viewModel.onTipoHoraChange("Extra") }
                    )
                }

                //Este es el bloque de la descripción
                CampoTexto(
                    valor = estado.descripcion,
                    error = null,
                    label = "Descripción (opcional)",
                    onChange = viewModel::onDescripcionChange
                )
            }

            //Este es el bloque del botón Registrar
            BotonRegistrar(
                texto = textoBoton,
                listo = formularioListo,
                guardando = estado.guardando,
                onClick = { viewModel.guardarRegistro() }
            )
        }
    }
}
