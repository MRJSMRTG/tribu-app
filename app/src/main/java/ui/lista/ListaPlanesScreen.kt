package com.example.tribu.ui.lista

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            .background(Color(0xFFFFF8E1))
            .padding(24.dp)
    ) {
        Text(
            text = "📋 Quedadas disponibles",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (planes.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Text(
                    text = "Todavía no hay quedadas creadas.",
                    modifier = Modifier.padding(16.dp)
                )
            }
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
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = plan.titulo,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color(0xFF2E7D32)
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Text("📍 ${plan.lugar}")
                            Text("📅 ${plan.fecha}")
                            Text("🏷️ ${plan.tipo}")
                            Text(
                                text = if (plan.precio.isNotEmpty()) {
                                    "💶 ${plan.precio}"
                                } else {
                                    "💶 Gratis"
                                }
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(plan.descripcion)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBA68C8)
            )
        ) {
            Text("Volver")
        }
    }
}