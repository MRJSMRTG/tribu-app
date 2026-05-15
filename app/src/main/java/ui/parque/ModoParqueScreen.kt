package com.example.tribu.ui.parque

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ModoParqueScreen(
    modifier: Modifier = Modifier,
    onVolver: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current

    var parque by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val familiasActivas = remember { mutableStateListOf<Pair<String, String>>() }

    LaunchedEffect(Unit) {
        db.collection("modoParque")
            .whereEqualTo("activo", true)
            .get()
            .addOnSuccessListener { resultado ->
                familiasActivas.clear()

                for (doc in resultado) {
                    val email = doc.getString("email") ?: "Familia"
                    val parqueNombre = doc.getString("parque") ?: ""

                    familiasActivas.add(email to parqueNombre)
                }
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E1))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Text(
            text = "🌳 Modo Parque",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Indica en qué parque estás para que otras familias puedan encontrarte.",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = parque,
                    onValueChange = { parque = it },
                    label = { Text("Nombre del parque") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val userId = auth.currentUser?.uid

                        if (userId != null && parque.isNotBlank()) {
                            val datos = hashMapOf(
                                "userId" to userId,
                                "email" to auth.currentUser?.email,
                                "parque" to parque,
                                "activo" to true,
                                "fecha" to System.currentTimeMillis()
                            )

                            db.collection("modoParque")
                                .document(userId)
                                .set(datos)
                                .addOnSuccessListener {
                                    mensaje = "Modo Parque activado"

                                    val emailActual = auth.currentUser?.email ?: "Familia"
                                    familiasActivas.removeAll { it.first == emailActual }
                                    familiasActivas.add(emailActual to parque)
                                }
                        } else {
                            mensaje = "Introduce el nombre del parque"
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4DB6AC)
                    )
                ) {
                    Text("Activar modo parque")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        val userId = auth.currentUser?.uid

                        if (userId != null) {
                            db.collection("modoParque")
                                .document(userId)
                                .update("activo", false)
                                .addOnSuccessListener {
                                    mensaje = "Modo Parque desactivado"

                                    val emailActual = auth.currentUser?.email
                                    familiasActivas.removeAll { familia ->
                                        familia.first == emailActual
                                    }
                                }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFE57373)
                    )
                ) {
                    Text("Desactivar modo parque")
                }

                if (mensaje.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = mensaje,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Familias activas:",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        familiasActivas.forEach { familia ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("👨‍👩‍👧‍👦 ${familia.first}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("📍 ${familia.second}")

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val uri = Uri.parse(
                                "geo:0,0?q=${Uri.encode(familia.second)}"
                            )
                            val intent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(intent)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF81C784)
                        )
                    ) {
                        Text("Ver en mapa")
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