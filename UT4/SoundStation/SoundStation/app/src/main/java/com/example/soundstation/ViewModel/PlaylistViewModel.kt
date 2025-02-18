package com.example.soundstation.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundstation.Repositorios.FirestorePlaylistRepository
import com.example.soundstation.modelo.Cancion
import com.example.soundstation.modelo.Playlist
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlaylistViewModel : ViewModel() {

    // Instancia del repositorio para interactuar con Firestore
    private val repository = FirestorePlaylistRepository()

    // MutableStateFlow que mantiene el estado de las playlists
    private val _playlists = MutableStateFlow<List<Playlist>>(emptyList())
    val playlists: StateFlow<List<Playlist>> get() = _playlists

    // MutableStateFlow que mantiene el estado de las canciones
    private val _canciones = MutableStateFlow<List<Cancion>>(emptyList())
    val canciones: StateFlow<List<Cancion>> get() = _canciones

    // Variable para gestionar el estado de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    // Variable para almacenar mensajes de error
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    // Instancia de FirebaseFirestore para acceder a la base de datos de Firestore
    private val db = FirebaseFirestore.getInstance()

    // Función para cargar las playlists desde Firestore
    fun cargarPlaylists() {
        db.collection("playlists")  // Accedemos a la colección de playlists
            .get()  // Obtenemos los documentos
            .addOnSuccessListener { snapshots ->  // Si la consulta tiene éxito
                val listaPlaylists = snapshots.documents.mapNotNull { document ->
                    document.toObject(Playlist::class.java)?.copy(id = document.id)  // Mapeo los datos a objetos Playlist
                }
                _playlists.value = listaPlaylists  // Actualizo el estado de playlists
                Log.d("PlaylistViewModel", "Playlists cargadas: $listaPlaylists")  // Log para depuración
            }
            .addOnFailureListener { e ->  // En caso de error
                Log.e("PlaylistViewModel", "Error al cargar playlists: ", e)
            }
    }

    // Método para crear una nueva playlist en Firestore
    fun crearPlaylist(playlist: Playlist) {
        val playlistData = hashMapOf(
            "nombre" to playlist.nombre,
            "creador" to playlist.creador,
            "fechaCreacion" to playlist.fechaCreacion,
            "canciones" to playlist.canciones
        )

        db.collection("playlists")  // Se Accede a la colección de playlists
            .add(playlistData)  // Añado una nueva playlist
            .addOnSuccessListener { documentReference ->  // Si la operación es exitosa
                Log.d("PlaylistViewModel", "Playlist creada con ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->  // En caso de error
                Log.e("PlaylistViewModel", "Error al crear la playlist: ", e)
            }
    }

    // Función para cargar las canciones de una playlist específica
    fun cargarCancionesDePlaylist(playlistId: String) {
        viewModelScope.launch {  // Se ejecuta dentro de un coroutine
            _canciones.value = repository.obtenerCancionesDePlaylist(playlistId)  // Se carga las canciones de la playlist
            Log.d("PlaylistViewModel", "Canciones cargadas: ${_canciones.value}")  // Log para depuración
        }
    }

    // Función para agregar una canción a una playlist
    fun agregarCancion(playlistId: String, cancion: Cancion) {
        viewModelScope.launch {
            repository.agregarCancionAPlaylist(playlistId, cancion)  // Se llama al repositorio para agregar la canción
            cargarCancionesDePlaylist(playlistId)  // Se actualiza la lista de canciones de la playlist
        }
    }

    // Función para eliminar una canción de una playlist
    fun eliminarCancion(playlistId: String, cancionId: String) {
        viewModelScope.launch {
            repository.eliminarCancionDePlaylist(playlistId, cancionId)  // Se llama al repositorio para eliminar la canción
            cargarCancionesDePlaylist(playlistId)  // Se actualiza la lista de canciones de la playlist
        }
    }

    // Método para cargar todas las canciones globalmente desde Firestore
    fun cargarCancionesGlobales() {
        val db = FirebaseFirestore.getInstance()
        db.collection("canciones")  // Coleccion de canciones en firebase firestore
            .get()
            .addOnSuccessListener { snapshot ->
                val canciones = snapshot.documents.map { document ->
                    Cancion(
                        id = document.id,
                        titulo = document.getString("titulo") ?: "Sin título"  // Creo una lista de objetos Cancion
                    )
                }
                _canciones.value = canciones  // Se actualiza el estado de canciones
            }
            .addOnFailureListener { e ->  // En caso de error
                Log.e("PlaylistViewModel", "Error al cargar las canciones", e)
            }
    }

    // Agregar una canción a una playlist usando el ID de la canción
    fun agregarCancionAPlaylist(playlistId: String, cancionId: String) {
        viewModelScope.launch {
            repository.agregarCancionAPlaylist(playlistId, cancionId)  // Se llama al repositorio para agregar la canción
            cargarCancionesDePlaylist(playlistId)  // Se actualiza la lista de canciones
        }
    }

    // Método para eliminar una playlist
    fun eliminarPlaylist(playlistId: String) {
        _isLoading.value = true  // Indicamos que está cargando
        viewModelScope.launch {
            try {
                repository.eliminarPlaylist(playlistId)  // Se llama al repositorio para eliminar la playlist
                cargarPlaylists()  // Se actualiza la lista de playlists
                _error.value = null  // Se limpie el error si la operación fue exitosa
            } catch (e: Exception) {
                _error.value = "Error al eliminar la playlist: ${e.message}"  // Si hay un error, lo asignamos a _error
            } finally {
                _isLoading.value = false  // Se finaliza  el estado de carga
            }
        }
    }

    // Método para actualizar el nombre de una playlist en Firestore
    fun actualizarNombrePlaylist(playlistId: String, nuevoNombre: String) {
        val playlistData = hashMapOf(
            "nombre" to nuevoNombre
        )
        db.collection("playlists") //Coleccion de firebase en firestore
            .document(playlistId)  // Se obtiene el documento específico de la playlist
            .update(playlistData as Map<String, Any>)  // Se actualiza el nombre de la playlist
            .addOnSuccessListener {
                Log.d("PlaylistViewModel", "Nombre de la playlist actualizado a: $nuevoNombre")
            }
            .addOnFailureListener { e ->  // En caso de error
                Log.e("PlaylistViewModel", "Error al actualizar el nombre de la playlist: ", e)
            }
    }
}
