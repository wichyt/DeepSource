package com.example.deepsourcehorasclase.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.deepsourcehorasclase.R
import com.example.deepsourcehorasclase.view.theme.DeepSourceHorasTheme
import com.example.deepsourcehorasclase.viewmodel.LoginViewModel

//Este es el bloque que conecta la pantalla con el ViewModel
@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel = viewModel()) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()
    val loginMessage by viewModel.loginMessage.collectAsState()
    val loginExitoso by viewModel.loginExitoso.collectAsState()
    val emailError by viewModel.emailError.collectAsState()
    val passwordError by viewModel.passwordError.collectAsState()

    //Este es el bloque que pasa a Hoy cuando el login sale bien
    LaunchedEffect(loginExitoso) {
        if (loginExitoso) {
            navController.navigate("hoy") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    LoginScreen(
        email = email,
        password = password,
        isLoading = isLoading,
        isError = isError,
        loginMessage = loginMessage,
        emailError = emailError,
        passwordError = passwordError,
        onEmailChange = {viewModel.onEmailChange(email=it)},
        onPasswordChange = {viewModel.onPasswordChange(password=it)},
        onLoginClick = {viewModel.onLoginClick()}
    )
}

//Este es el bloque que dibuja la pantalla de login
@Composable
fun LoginScreen(
    email: String,
    password: String,
    isLoading: Boolean,
    isError: Boolean,
    loginMessage: String,
    emailError: String?,
    passwordError: String?,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Scaffold { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            //Este es el bloque del logo
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_logo),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "DeepSource",
                    style = MaterialTheme.typography.titleLarge)
            }

            //Este es el bloque de los textos y el formulario
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(text = "Registro\nde horas",
                    style = MaterialTheme.typography.displaySmall)

                Text(text = "Entra con la misma cuenta que usas en la plataforma de DeepSource.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)

                //Este es el bloque del campo correo
                OutlinedTextField(
                    value = email,
                    onValueChange = onEmailChange,
                    label = { Text("Correo") },
                    singleLine = true,
                    isError = emailError != null,
                    supportingText = {
                        emailError?.let {
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                //Este es el bloque del campo contraseña
                OutlinedTextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text("Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = passwordError != null,
                    supportingText = {
                        passwordError?.let {
                            Text(it, color = MaterialTheme.colorScheme.error)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                //Este es el bloque del mensaje del login
                if (loginMessage.isNotEmpty()) {
                    Text(text = loginMessage,
                        color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary)
                }

                //Este es el bloque del botón Ingresar
                Button(
                    onClick = onLoginClick,
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Ingresar")
                }
            }
        }
    }
}

//Este es el bloque de la vista previa
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    DeepSourceHorasTheme {
        LoginScreen(
            email = "",
            password = "",
            isLoading = false,
            isError = false,
            loginMessage = "",
            emailError = null,
            passwordError = null,
            onEmailChange = {},
            onPasswordChange = {},
            onLoginClick = {}
        )
    }
}
