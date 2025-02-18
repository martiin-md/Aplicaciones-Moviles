package com.example.soundstation.Repositorios

import com.example.soundstation.modelo.Cancion
import com.example.soundstation.modelo.Playlist
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestorePlaylistRepository {

    // Instancia de FirebaseFirestore para interactuar con la base de datos de Firestore
    private val db = FirebaseFirestore.getInstance()

    // Referencias a las colecciones de Firestore
    private val playlistsCollection = db.collection("playlists") // Colección de playlists
    private val cancionesCollection = db.collection("canciones") // Colección de canciones

    // Crear una nueva playlist
    suspend fun crearPlaylist(playlist: Playlist): Boolean {
        return try {
            // Crea o actualiza el documento de la playlist con el ID especificado
            playlistsCollection.document(playlist.id)
                .set(playlist)
                .await()
            true
        } catch (e: Exception) {
            // Si ocurre un error, lo imprime y devuelve false
            e.printStackTrace()
            false
        }
    }

    // Obtener todas las playlists
    suspend fun obtenerPlaylists(): List<Playlist> {
        return try {
            // Obtiene todos los documentos de la colección "playlists" y los convierte en objetos Playlist
            playlistsCollection.get()
                .await()
                .toObjects(Playlist::class.java)
        } catch (e: Exception) {
            // En caso de error, devuelve una lista vacía
            emptyList()
        }
    }

    // Agregar una canción a una playlist usando el objeto Cancion
    suspend fun agregarCancionAPlaylistPorObjeto(playlistId: String, cancion: Cancion) {
        try {
            // Inserta la canción en la subcolección canciones dentro de la playlist
            playlistsCollection.document(playlistId)
                .collection("canciones")
                .document(cancion.id)
                .set(cancion)
                .await()
        } catch (e: Exception) {
            // En caso de error, imprime el error en la consola
            e.printStackTrace()
        }
    }

    // Agregar una canción a una playlist usando el ID de la canción
    suspend fun agregarCancionAPlaylistPorId(playlistId: String, cancionId: String) {
        try {
            // Obtiene una referencia al documento de la playlist
            val playlistRef = playlistsCollection.document(playlistId)
            db.runTransaction { transaction ->
                // Realiza una transacción para obtener el documento de la playlist
                val snapshot = transaction.get(playlistRef)
                // Obtiene la lista de canciones de la playlist
                val canciones = snapshot.get("canciones") as? MutableList<String> ?: mutableListOf()
                // Añade el ID de la nueva canción a la lista
                canciones.add(cancionId)
                // Actualiza el documento de la playlist con la nueva lista de canciones
                transaction.update(playlistRef, "canciones", canciones)
            }.await()
        } catch (e: Exception) {
            // En caso de error, imprime el error en la consola
            e.printStackTrace()
        }
    }

    // Agregar una canción a una playlist
    suspend fun agregarCancionAPlaylist(playlistId: String, cancion: Cancion) {
        try {
            // Inserta la canción en la subcolección canciones de la playlist
            playlistsCollection.document(playlistId).collection("canciones")
                .document(cancion.id)
                .set(cancion)
                .await()
        } catch (e: Exception) {
            // En caso de error, imprime el error en la consola
            e.printStackTrace()
        }
    }

    // Obtener todas las canciones de una playlist específica
    suspend fun obtenerCancionesDePlaylist(playlistId: String): List<Cancion> {
        return try {
            // Obtiene todos los documentos de la subcolección canciones dentro de la playlist
            playlistsCollection.document(playlistId).collection("canciones")
                .get()
                .await()
                .toObjects(Cancion::class.java)
        } catch (e: Exception) {
            // En caso de error, devuelve una lista vacía
            emptyList()
        }
    }

    // Eliminar una playlist
    suspend fun eliminarPlaylist(playlistId: String) {
        try {
            // Elimina el documento de la playlist
            playlistsCollection.document(playlistId)
                .delete()
                .await()
        } catch (e: Exception) {
            // En caso de error, imprime el error en la consola
            e.printStackTrace()
        }
    }

    // Eliminar una canción de una playlist
    suspend fun eliminarCancionDePlaylist(playlistId: String, cancionId: String) {
        try {
            // Elimina el documento de la canción en la subcolección "canciones" dentro de la playlist
            playlistsCollection.document(playlistId).collection("canciones")
                .document(cancionId)
                .delete()
                .await()
        } catch (e: Exception) {
            // En caso de error, imprime el error en la consola
            e.printStackTrace()
        }
    }

    // Nuevo método: obtener todas las canciones globales
    suspend fun obtenerTodasLasCanciones(): List<Cancion> {
        return try {
            // Obtiene todos los documentos de la colección canciones
            db.collection("canciones")
                .get()
                .await()
                .toObjects(Cancion::class.java)
        } catch (e: Exception) {
            // En caso de error, devuelve una lista vacía
            emptyList()
        }
    }

    // Agregar una canción a una playlist usando solo el ID de la canción
    suspend fun agregarCancionAPlaylist(playlistId: String, cancionId: String) {
        val playlistRef = db.collection("playlists").document(playlistId)
        db.runTransaction { transaction ->
            // Realiza una transacción para obtener el documento de la playlist
            val snapshot = transaction.get(playlistRef)
            // Obtiene la lista de canciones de la playlist
            val canciones = snapshot.get("canciones") as? MutableList<String> ?: mutableListOf()
            // Añade el ID de la nueva canción a la lista
            canciones.add(cancionId)
            // Actualiza el documento de la playlist con la nueva lista de canciones
            transaction.update(playlistRef, "canciones", canciones)
        }
            .await()
    }
}
