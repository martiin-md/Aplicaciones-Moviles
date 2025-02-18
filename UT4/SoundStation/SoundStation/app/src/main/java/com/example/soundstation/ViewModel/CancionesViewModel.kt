package com.example.soundstation.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.soundstation.Repositorios.FirestoreCancionRepository
import com.example.soundstation.modelo.Cancion
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CancionesViewModel : ViewModel() {

    // Instancia del repositorio para interactuar con Firestore
    private val repository = FirestoreCancionRepository()

    // MutableStateFlow que mantiene el estado de las canciones
    private val _canciones = MutableStateFlow<List<Cancion>>(emptyList())

    // variable del flujo de canciones, solo lectura
    val canciones: StateFlow<List<Cancion>> get() = _canciones

    // Función para cargar todas las canciones
    fun cargarCanciones() {
        // Ejecutamos el código dentro de un coroutine
        viewModelScope.launch {
            // Asigno las canciones obtenidas del repositorio a la variable _canciones
            _canciones.value = repository.obtenerTodasLasCanciones()
        }
    }

    // Función para agregar una canción
    fun agregarCancion(cancion: Cancion) {
        // Ejecutoel código dentro de un coroutine
        viewModelScope.launch {
            // Llamo al repositorio para insertar la canción
            repository.insertarCancion(cancion)
            // Actualizo la lista de canciones después de agregar una nueva
            cargarCanciones()
        }
    }

    // Función para eliminar una canción, pasamos el ID de la canción a eliminar
    fun eliminarCancion(id: String) {
        // Ejecuto el código dentro de un coroutine
        viewModelScope.launch {
            // Llamo al repositorio para eliminar la canción
            repository.eliminarCancion(id)
            // Actualizo la lista de canciones después de eliminar una
            cargarCanciones()
        }
    }
}
