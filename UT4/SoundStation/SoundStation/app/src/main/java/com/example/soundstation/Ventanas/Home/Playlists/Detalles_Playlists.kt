@file:Suppress("DEPRECATION")

package com.example.soundstation.Ventanas.Home.Playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.soundstation.ViewModel.PlaylistViewModel
import com.example.soundstation.modelo.Cancion
import com.example.soundstation.ui.theme.SoundStationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallesPlaylistScreen(
    navController: NavHostController, // Controlador de navegación para gestionar la navegación entre pantallas
    playlistId: String, // ID de la playlist que se mostrará
    viewModel: PlaylistViewModel = viewModel(), // ViewModel para manejar la lógica de la vista
    onBack: () -> Unit // Función para navegar hacia atrás
) {
    // Se ejecuta cuando el playlistId cambia para cargar las canciones asociadas
    LaunchedEffect(playlistId) {
        viewModel.cargarCancionesDePlaylist(playlistId)
    }

    // Se recogen los valores del ViewModel
    val canciones by viewModel.canciones.collectAsState() // Lista de canciones asociadas a la playlist
    val isLoading by viewModel.isLoading.collectAsState() // Estado de carga
    val error by viewModel.error.collectAsState() // Estado de error

    // Componente principal de la pantalla
    Scaffold(
        topBar = {
            // Barra superior con el título y el ícono de navegación
            TopAppBar(
                title = { Text("Detalles de la Playlist") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { paddingValues ->
        // Caja principal para contener la pantalla con los valores de padding
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                // Si está cargando, mostramos un indicador de carga
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                // Si ocurre un error, mostramos el mensaje de error
                error != null -> {
                    Text(
                        text = error ?: "Error desconocido", // Mensaje de error si es que existe uno
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                // Si no hay error y no está cargando, mostramos las canciones
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize() // Usamos LazyColumn para mostrar la lista de canciones
                    ) {
                        item {
                            // Información general de la playlist, mostrando el ID de la playlist
                            Text(
                                text = "Playlist ID: $playlistId",
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        // realizo una iteracion sobre las canciones y mostramos el título de cada una
                        items(canciones) { cancion ->
                            Text(
                                text = cancion.titulo, // Muestra el título de la canción
                                modifier = Modifier
                                    .fillMaxWidth() // Hace que el texto ocupe el ancho completo
                                    .padding(8.dp) // Añade padding para separación
                            )
                        }
                    }
                }
            }
        }
    }
}
