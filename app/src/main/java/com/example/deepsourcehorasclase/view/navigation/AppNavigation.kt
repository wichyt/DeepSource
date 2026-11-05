package com.example.deepsourcehorasclase.view.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.deepsourcehorasclase.view.screens.HoyScreen
import com.example.deepsourcehorasclase.view.screens.LoginScreen
import com.example.deepsourcehorasclase.view.screens.PerfilScreen
import com.example.deepsourcehorasclase.view.screens.RegistroHorasScreen
import com.example.deepsourcehorasclase.viewmodel.AjustesViewModel
import com.example.deepsourcehorasclase.viewmodel.HorasViewModel
import com.example.deepsourcehorasclase.viewmodel.PerfilViewModel

//Este es el bloque de la navegación entre pantallas
@Composable
fun AppNavigation(ajustesViewModel: AjustesViewModel) {
    val navController = rememberNavController()

    //Este es el bloque de los ViewModel compartidos
    val horasViewModel: HorasViewModel = viewModel()
    val perfilViewModel: PerfilViewModel = viewModel()

    //Este es el bloque de las rutas
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(navController)
        }
        composable("hoy") {
            HoyScreen(navController, horasViewModel)
        }
        composable("registro") {
            RegistroHorasScreen(navController, horasViewModel)
        }
        composable("perfil") {
            PerfilScreen(navController, perfilViewModel, ajustesViewModel)
        }
    }
}
