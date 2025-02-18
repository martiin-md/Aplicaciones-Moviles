package com.example.soundstation.modelo


data class Playlist(
    val id: String = "",
    val nombre: String = "",
    val creador: String = "",
    val fechaCreacion: String = "",
    val canciones: List<String> = emptyList() // Lista de IDs de canciones

)

