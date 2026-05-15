package com.example.tribu.model

data class Plan(
    val id: String = "",
    val titulo: String = "",
    val lugar: String = "",
    val fecha: String = "",
    val descripcion: String = "",
    val tipo: String = "",
    val precio: String = "",
    val asistentes: Int = 0,
    val creadorId: String = ""
)