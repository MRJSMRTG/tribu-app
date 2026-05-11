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
import com.example.tribu.ui.detalle.DetallePlanScreen
import com.example.tribu.ui.editar.EditarPlanScreen
import com.example.tribu.ui.parque.ModoParqueScreen

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

    var planSeleccionado by remember { mutableStateOf<Plan?>(null) }
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
                        val precio = documento.getString("precio") ?: ""

                        planes.add(
                            Plan(
                                id = documento.id,
                                titulo = titulo,
                                lugar = lugar,
                                fecha = fecha,
                                descripcion = descripcion,
                                tipo = tipo,
                                precio = precio,
                                asistentes = documento.getLong("asistentes")?.toInt() ?: 0
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
                onParqueClick = { pantallaActual = "parque" },
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
                        "tipo" to nuevoPlan.tipo,
                        "precio" to nuevoPlan.precio,
                        "asistentes" to 0
                    )

                    db.collection("planes")
                        .add(planMap)
                        .addOnSuccessListener { documento ->
                            val planConId = nuevoPlan.copy(
                                id = documento.id,
                                asistentes = 0
                            )

                            planes.add(planConId)
                            pantallaActual = "home"
                        }
                },
                onVolver = {
                    pantallaActual = "home"
                }
            )
            "detalle" -> {
                planSeleccionado?.let { plan ->
                    DetallePlanScreen(
                        modifier = modifier,
                        plan = plan,
                        onVolver = {
                            pantallaActual = "lista"
                        },
                        onEliminar = {
                            planes.remove(plan)
                            planSeleccionado = null
                            pantallaActual = "lista"
                        },
                        onEditar = {
                            pantallaActual = "editar"
                        }
                    )
                }
            }

            "lista" -> ListaPlanesScreen(
                modifier = modifier,
                planes = planes,
                onPlanClick = { plan ->
                    planSeleccionado = plan
                    pantallaActual = "detalle"
                },
                onVolver = {
                    pantallaActual = "home"
                }
            )
            "editar" -> {
                planSeleccionado?.let { plan ->
                    EditarPlanScreen(
                        modifier = modifier,
                        plan = plan,
                        onGuardar = { planEditado ->

                            db.collection("planes")
                                .document(planEditado.id)
                                .update(
                                    mapOf(
                                        "titulo" to planEditado.titulo,
                                        "lugar" to planEditado.lugar,
                                        "fecha" to planEditado.fecha,
                                        "descripcion" to planEditado.descripcion,
                                        "tipo" to planEditado.tipo,
                                        "precio" to planEditado.precio
                                    )
                                )
                                .addOnSuccessListener {
                                    val index = planes.indexOfFirst { it.id == planEditado.id }

                                    if (index != -1) {
                                        planes[index] = planEditado
                                    }

                                    planSeleccionado = planEditado
                                    pantallaActual = "detalle"
                                }
                        },
                        onVolver = {
                            pantallaActual = "detalle"
                        }
                    )
                }
            }
            "parque" -> ModoParqueScreen(
                modifier = modifier,
                onVolver = {
                    pantallaActual = "home"
                }
            )
        }
    }
}