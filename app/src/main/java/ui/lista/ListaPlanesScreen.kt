package com.example.tribu.ui.lista

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tribu.model.Plan

@Composable
fun ListaPlanesScreen(
    modifier: Modifier = Modifier,
    planes: List<Plan>,
    onVolver: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Quedadas disponibles",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (planes.isEmpty()) {
            Text("Todavía no hay quedadas creadas.")
        } else {
            planes.forEach { plan ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = plan.titulo,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text("Lugar: ${plan.lugar}")
                        Text("Fecha: ${plan.fecha}")
                        Text("Descripción: ${plan.descripcion}")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onVolver) {
            Text("Volver")
        }
    }
}