package com.example.tribu.ui.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PerfilScreen(
    modifier: Modifier = Modifier,
    onVolver: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var nombreFamilia by remember { mutableStateOf("") }
    var zona by remember { mutableStateOf("") }
    var edadesHijos by remember { mutableStateOf("") }
    var intereses by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    var numeroHijos by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val userId = auth.currentUser?.uid

        if (userId != null) {
            db.collection("usuarios")
                .document(userId)
                .get()
                .addOnSuccessListener { documento ->
                    if (documento.exists()) {
                        nombreFamilia = documento.getString("nombreFamilia") ?: ""
                        zona = documento.getString("zona") ?: ""
                        numeroHijos = documento.getString("numeroHijos") ?: ""
                        edadesHijos = documento.getString("edadesHijos") ?: ""
                        intereses = documento.getString("intereses") ?: ""
                    }
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
            text = "👨‍👩‍👧‍👦 Perfil familiar",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Datos de tu familia",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF2E7D32)
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = nombreFamilia,
                    onValueChange = { nombreFamilia = it },
                    label = { Text("Nombre de la familia") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = zona,
                    onValueChange = { zona = it },
                    label = { Text("Zona") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = numeroHijos,
                    onValueChange = { numeroHijos = it },
                    label = { Text("Número de hijos") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = edadesHijos,
                    onValueChange = { edadesHijos = it },
                    label = { Text("Edades de los hijos") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = intereses,
                    onValueChange = { intereses = it },
                    label = { Text("Intereses") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val userId = auth.currentUser?.uid

                if (userId != null) {
                    val perfil = hashMapOf(
                        "nombreFamilia" to nombreFamilia,
                        "zona" to zona,
                        "numeroHijos" to numeroHijos,
                        "edadesHijos" to edadesHijos,
                        "intereses" to intereses,
                        "email" to auth.currentUser?.email
                    )

                    db.collection("usuarios")
                        .document(userId)
                        .set(perfil)
                        .addOnSuccessListener {
                            mensaje = "Perfil guardado correctamente"
                        }
                        .addOnFailureListener {
                            mensaje = "Error al guardar el perfil"
                        }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF81C784)
            )
        ) {
            Text("Guardar perfil")
        }

        if (mensaje.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = mensaje,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

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