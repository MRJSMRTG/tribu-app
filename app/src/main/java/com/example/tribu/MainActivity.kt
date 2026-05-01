package com.example.tribu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.tribu.model.Plan
import com.example.tribu.ui.auth.AuthScreen
import com.example.tribu.ui.crear.CrearPlanScreen
import com.example.tribu.ui.home.TribuHome
import com.example.tribu.ui.lista.ListaPlanesScreen
import com.example.tribu.ui.theme.TribuTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.example.tribu.ui.perfil.PerfilScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TribuTheme {
                Scaffold { innerPadding ->
                    TribuAppScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TribuAppScreen(modifier: Modifier = Modifier) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    var usuarioLogueado by remember {
        mutableStateOf(auth.currentUser != null)
    }

    var pantallaActual by remember { mutableStateOf("home") }
    val planes = remember { mutableStateListOf<Plan>() }

    LaunchedEffect(usuarioLogueado) {
        if (usuarioLogueado) {
            db.collection("planes")
                .get()
                .addOnSuccessListener { resultado ->
                    planes.clear()

                    for (documento in resultado) {
                        val titulo = documento.getString("titulo") ?: ""
                        val lugar = documento.getString("lugar") ?: ""
                        val fecha = documento.getString("fecha") ?: ""
                        val descripcion = documento.getString("descripcion") ?: ""
                        val tipo = documento.getString("tipo") ?: ""

                        planes.add(
                            Plan(
                                titulo = titulo,
                                lugar = lugar,
                                fecha = fecha,
                                descripcion = descripcion,
                                tipo = tipo
                            )
                        )
                    }
                }
        }
    }

    if (!usuarioLogueado) {
        AuthScreen(
            onLoginSuccess = {
                usuarioLogueado = true
            }
        )
    } else {
        when (pantallaActual) {
            "perfil" -> PerfilScreen(
                modifier = modifier,
                onVolver = {
                    pantallaActual = "home"
                }
            )
            "home" -> TribuHome(
                modifier = modifier,
                onCrearClick = { pantallaActual = "crear" },
                onVerClick = { pantallaActual = "lista" },
                onPerfilClick = { pantallaActual = "perfil" },
                onLogout = {
                    auth.signOut()
                    usuarioLogueado = false
                    planes.clear()
                }
            )

            "crear" -> CrearPlanScreen(
                modifier = modifier,
                onGuardar = { nuevoPlan ->
                    val planMap = hashMapOf(
                        "titulo" to nuevoPlan.titulo,
                        "lugar" to nuevoPlan.lugar,
                        "fecha" to nuevoPlan.fecha,
                        "descripcion" to nuevoPlan.descripcion,
                        "tipo" to nuevoPlan.tipo
                    )

                    db.collection("planes")
                        .add(planMap)
                        .addOnSuccessListener {
                            planes.add(nuevoPlan)
                            pantallaActual = "home"
                        }
                },
                onVolver = {
                    pantallaActual = "home"
                }
            )

            "lista" -> ListaPlanesScreen(
                modifier = modifier,
                planes = planes,
                onVolver = {
                    pantallaActual = "home"
                }
            )
        }
    }
}