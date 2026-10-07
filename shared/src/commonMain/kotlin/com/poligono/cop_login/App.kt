package com.poligono.cop_login

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

sealed class Screen {
    data object Login : Screen()
    data class Admin(val name: String) : Screen()
    data class Supervisor(val name: String) : Screen()
    data class Cliente(val name: String) : Screen()
}

@Composable
fun App() {
    MaterialTheme {
        var screen by remember { mutableStateOf<Screen>(Screen.Login) }

        when (val current = screen) {
            is Screen.Login -> LoginScreen(
                onLoginSuccess = { response ->
                    val name = response.name ?: "Usuario"

                    screen = when (response.role) {
                        "ADMIN" -> Screen.Admin(name)
                        "SUPERVISOR" -> Screen.Supervisor(name)
                        else -> Screen.Cliente(name)
                    }
                }
            )

            is Screen.Admin -> AdminScreen(
                name = current.name,
                onLogout = { screen = Screen.Login }
            )

            is Screen.Supervisor -> SupervisorScreen(
                name = current.name,
                onLogout = { screen = Screen.Login }
            )

            is Screen.Cliente -> ClienteScreen(
                name = current.name,
                onLogout = { screen = Screen.Login }
            )
        }
    }
}