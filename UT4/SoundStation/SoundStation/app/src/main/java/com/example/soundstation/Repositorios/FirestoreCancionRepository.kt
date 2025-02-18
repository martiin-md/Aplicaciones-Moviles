package com.example.soundstation.Repositorios

import com.example.soundstation.modelo.Cancion
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreCancionRepository {

    // Instancia de FirebaseFirestore para interactuar con la base de datos de Firestore
    private val db = FirebaseFirestore.getInstance()

    // Referencia a la colección "canciones" en Firestore
    private val coleccionCanciones = db.collection("canciones")

    // Obtener todas las canciones
    suspend fun obtenerTodasLasCanciones(): List<Cancion> {
        return try {
            // Obtiene todos los documentos de la colección "canciones" y los convierte en objetos Cancion
            coleccionCanciones.get().await().toObjects(Cancion::class.java)
        } catch (e: Exception) {
            // En caso de error, retorna una lista vacía
            emptyList()
        }
    }

    // Insertar una canción en Firestore
    suspend fun insertarCancion(cancion: Cancion) {
        try {
            // Inserta o actualiza un documento en la colección "canciones" con el ID de la canción
            coleccionCanciones.document(cancion.id).set(cancion).await()
        } catch (e: Exception) {
            // En caso de error, imprime el error en la consola
            e.printStackTrace()
        }
    }

    // Eliminar una canción de Firestore por su ID
    suspend fun eliminarCancion(id: String) {
        try {
            // Elimina el documento con el ID proporcionado de la colección "canciones"
            coleccionCanciones.document(id).delete().await()
        } catch (e: Exception) {
            // En caso de error, imprime el error en la consola
            e.printStackTrace()
        }
    }
}
