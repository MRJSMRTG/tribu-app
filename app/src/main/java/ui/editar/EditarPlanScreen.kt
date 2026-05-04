package com.example.tribu.ui.editar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tribu.model.Plan
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun EditarPlanScreen(
    modifier: Modifier = Modifier,
    plan: Plan,
    onGuardar: (Plan) -> Unit,
    onVolver: () -> Unit
) {
    var titulo by remember { mutableStateOf(plan.titulo) }
    var lugar by remember { mutableStateOf(plan.lugar) }
    var fecha by remember { mutableStateOf(plan.fecha) }
    var descripcion by remember { mutableStateOf(plan.descripcion) }
    var tipo by remember { mutableStateOf(plan.tipo) }
    var precio by remember { mutableStateOf(plan.precio) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text("Editar quedada", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(titulo, { titulo = it }, label = { Text("Título") })
        OutlinedTextField(lugar, { lugar = it }, label = { Text("Lugar") })
        OutlinedTextField(fecha, { fecha = it }, label = { Text("Fecha") })
        OutlinedTextField(descripcion, { descripcion = it }, label = { Text("Descripción") })
        OutlinedTextField(tipo, { tipo = it }, label = { Text("Tipo") })
        OutlinedTextField(precio, { precio = it }, label = { Text("Precio") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val planEditado = plan.copy(
                    titulo = titulo,
                    lugar = lugar,
                    fecha = fecha,
                    descripcion = descripcion,
                    tipo = tipo,
                    precio = precio
                )
                onGuardar(planEditado)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar cambios")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver a la quedada")
        }
    }
}