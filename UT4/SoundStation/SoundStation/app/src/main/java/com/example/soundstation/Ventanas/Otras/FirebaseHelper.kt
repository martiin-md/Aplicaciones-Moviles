package com.example.soundstation.Ventanas.Otras

import com.google.firebase.storage.FirebaseStorage

object FirebaseHelper {

    // Referencia a Firebase Storage
    private val storage = FirebaseStorage.getInstance()

    // Referencia a la carpeta "soundstation" en Firebase Storage
    private val songsRef = storage.reference.child("soundstation")

    // Función para obtener la lista de canciones almacenadas en Firebase Storage
    fun getSongList(onComplete: (List<String>) -> Unit) {
        // Listar todos los archivos en la carpeta
        songsRef.listAll()
            .addOnSuccessListener { result ->
                // Mapeo los nombres de las canciones obtenidas
                val songNames = result.items.map { it.name }
                // Llamoi al callback con la lista de nombres de las canciones
                onComplete(songNames)
            }
            .addOnFailureListener {
                // En caso de error, devolvemos una lista vacía
                onComplete(emptyList())
            }
    }

    // Función para obtener la URL de descarga de una canción por su nombre
    fun getSongUrl(songName: String, onComplete: (String) -> Unit) {
        // Obtenemos la referencia a la canción por su nombre
        songsRef.child(songName).downloadUrl
            .addOnSuccessListener { uri ->
                // Llamamos al callback con la URL de descarga de la canción
                onComplete(uri.toString())
            }
            .addOnFailureListener {
                // En caso de error, devolvemos una cadena vacía
                onComplete("")
            }
    }
}
