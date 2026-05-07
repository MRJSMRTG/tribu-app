package com.example.tribu.ui.parque

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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

    var parque by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "Modo Parque",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Indica en qué parque estás para que otras familias puedan saberlo."
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
                        }
                } else {
                    mensaje = "Introduce el nombre del parque"
                }
            },
            modifier = Modifier.fillMaxWidth()
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
                        }
                }
            },
            modifier = Modifier.fillMaxWidth()
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

        Spacer(modifier = Modifier.height(24.dp))

        TextButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}