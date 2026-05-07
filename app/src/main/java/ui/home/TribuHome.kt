package com.example.tribu.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun TribuHome(
    modifier: Modifier = Modifier,
    onCrearClick: () -> Unit,
    onVerClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onParqueClick: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🌿 TRIBU",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Conecta familias y crea quedadas",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onCrearClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("➕ Crear quedada")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onVerClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("📋 Ver quedadas")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onPerfilClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("👨‍👩‍👧‍👦 Mi perfil")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onParqueClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("🌳 Modo Parque")
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onLogout,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text("🚪 Cerrar sesión")
        }

        }
    }