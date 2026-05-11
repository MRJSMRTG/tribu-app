package com.example.tribu.ui.lista

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tribu.model.Plan

@Composable
fun ListaPlanesScreen(
    modifier: Modifier = Modifier,
    planes: List<Plan>,
    onPlanClick: (Plan) -> Unit,
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
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(planes) { plan ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                            .clickable { onPlanClick(plan) },
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = plan.titulo,
                                style = MaterialTheme.typography.titleLarge
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text("📍 ${plan.lugar}")
                            Text("📅 ${plan.fecha}")

                            Text(
                                text = "🏷️ ${plan.tipo}",
                                color = MaterialTheme.colorScheme.primary
                            )

                            if (plan.precio.isNotEmpty()) {
                                Text("💶 ${plan.precio}")
                            } else {
                                Text("💶 Gratis")
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(plan.descripcion)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}