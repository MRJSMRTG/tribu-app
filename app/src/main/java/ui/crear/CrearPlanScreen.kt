package com.example.tribu.ui.crear

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tribu.model.Plan

@Composable
fun CrearPlanScreen(
    modifier: Modifier = Modifier,
    onGuardar: (Plan) -> Unit,
    onVolver: () -> Unit
) {
    var titulo by remember { mutableStateOf("") }
    var lugar by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var mensajeError by remember { mutableStateOf("") }
    var tipo by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Crear quedada",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Título") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = lugar,
            onValueChange = { lugar = it },
            label = { Text("Lugar") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = tipo,
            onValueChange = { tipo = it },
            label = { Text("Tipo de actividad (parque, bici, museo...)") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = precio,
            onValueChange = { precio = it },
            label = { Text("Precio (opcional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        if (mensajeError.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mensajeError,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (titulo.isBlank() || lugar.isBlank() || fecha.isBlank()) {
                    mensajeError = "Completa título, lugar y fecha"
                } else {
                    mensajeError = ""

                    val nuevoPlan = Plan(
                        titulo = titulo,
                        lugar = lugar,
                        fecha = fecha,
                        descripcion = descripcion,
                        tipo = tipo,
                        precio = precio
                    )

                    mensajeError = "Quedada guardada correctamente"
                    onGuardar(nuevoPlan)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar quedada")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
