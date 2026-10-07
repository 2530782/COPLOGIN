package com.poligono.cop_login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
private fun RoleScreen(
    title: String,
    name: String,
    description: String,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Bienvenido, $name")

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = description)

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onLogout) {
            Text("Cerrar sesión")
        }
    }
}

@Composable
fun AdminScreen(
    name: String,
    onLogout: () -> Unit
) {
    RoleScreen(
        title = "Vista de Administrador",
        name = name,
        description = "Aquí el administrador gestiona usuarios y el sistema.",
        onLogout = onLogout
    )
}

@Composable
fun SupervisorScreen(
    name: String,
    onLogout: () -> Unit
) {
    RoleScreen(
        title = "Vista de Supervisor",
        name = name,
        description = "Aquí el supervisor revisa y da seguimiento.",
        onLogout = onLogout
    )
}

@Composable
fun ClienteScreen(
    name: String,
    onLogout: () -> Unit
) {
    RoleScreen(
        title = "Vista de Cliente",
        name = name,
        description = "Aquí el cliente consulta y agenda sus citas.",
        onLogout = onLogout
    )
}