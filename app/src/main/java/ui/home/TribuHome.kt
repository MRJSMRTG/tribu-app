package com.example.tribu.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TribuHome(
    modifier: Modifier = Modifier,
    onCrearClick: () -> Unit,
    onVerClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🌿 TRIBU",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Conecta familias y crea quedadas",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onCrearClick) {
            Text("Crear quedada")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onVerClick) {
            Text("Ver quedadas")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = onPerfilClick) {
            Text("Mi perfil familiar")
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onLogout) {
            Text("Cerrar sesión")
        }
    }
}