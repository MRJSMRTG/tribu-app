package com.example.tribu.ui.detalle

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tribu.model.Plan
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext

@Composable
fun DetallePlanScreen(
    modifier: Modifier = Modifier,
    plan: Plan,
    onVolver: () -> Unit,
    onEliminar: () -> Unit,
    onEditar: () -> Unit,
) {
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var apuntado by remember { mutableStateOf(false) }
    var asistentes by remember { mutableStateOf(plan.asistentes) }
    var mensaje by remember { mutableStateOf("") }
    var asistentesFamilia by remember { mutableStateOf("") }
    var comentario by remember { mutableStateOf("") }
    val comentarios = remember { mutableStateListOf<String>() }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ){
        Text(
            text = plan.titulo,
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("📍 Lugar: ${plan.lugar}")
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val uri = Uri.parse("geo:0,0?q=${Uri.encode(plan.lugar)}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        context.startActivity(intent)
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ver ubicación en mapa")
                }

                Text("📅 Fecha: ${plan.fecha}")
                Spacer(modifier = Modifier.height(8.dp))

                Text("🏷️ Tipo: ${plan.tipo}")
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = if (plan.precio.isNotEmpty()) {
                        "💶 Precio: ${plan.precio}"
                    } else {
                        "💶 Gratis"
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("📝 Descripción:")
                Text(plan.descripcion)

                Spacer(modifier = Modifier.height(16.dp))

                Text("👥 Asistentes: $asistentes")

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = asistentesFamilia,
                    onValueChange = { asistentesFamilia = it },
                    label = { Text("Número de asistentes") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        val cantidad = asistentesFamilia.toIntOrNull()

                        if (!apuntado && plan.id.isNotEmpty() && cantidad != null && cantidad > 0) {
                            val nuevosAsistentes = asistentes + cantidad

                            db.collection("planes")
                                .document(plan.id)
                                .update("asistentes", nuevosAsistentes)
                                .addOnSuccessListener {
                                    asistentes = nuevosAsistentes
                                    apuntado = true
                                    mensaje = "Os habéis apuntado correctamente"
                                }
                        } else {
                            mensaje = "Introduce un número válido de asistentes"
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (apuntado) "Ya estáis apuntados" else "Me apunto")
                }

                if (mensaje.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        Button(
            onClick = { onEditar() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar quedada")
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = comentario,
            onValueChange = { comentario = it },
            label = { Text("Escribe un comentario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (comentario.isNotBlank() && plan.id.isNotEmpty()) {

                    val nuevoComentario = hashMapOf(
                        "texto" to comentario,
                        "planId" to plan.id
                    )

                    db.collection("comentarios")
                        .add(nuevoComentario)
                        .addOnSuccessListener {
                            comentarios.add(comentario)
                            comentario = ""
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar comentario")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text("Comentarios:")

        comentarios.forEach {
            Text("• $it")
        }
        Spacer(modifier = Modifier.height(24.dp))
        LaunchedEffect(plan.id) {
            if (plan.id.isNotEmpty()) {
                db.collection("comentarios")
                    .whereEqualTo("planId", plan.id)
                    .get()
                    .addOnSuccessListener { resultado ->
                        comentarios.clear()
                        for (doc in resultado) {
                            val texto = doc.getString("texto") ?: ""
                            comentarios.add(texto)
                        }
                    }
            }
        }
        Button(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (plan.id.isNotEmpty()) {
                    db.collection("planes")
                        .document(plan.id)
                        .delete()
                        .addOnSuccessListener {
                            onEliminar()
                        }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Eliminar quedada")
        }
    }
}