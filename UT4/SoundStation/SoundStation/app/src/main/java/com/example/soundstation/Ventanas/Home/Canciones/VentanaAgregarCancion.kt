package com.example.soundstation.Ventanas.Home.Canciones

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.Ventanas.Home.BottomNavigationBar
import com.example.soundstation.Ventanas.Home.Playlists.CancionItem
import com.example.soundstation.Ventanas.Home.Playlists.PlaylistItem
import com.example.soundstation.ViewModel.PlaylistViewModel
import com.example.soundstation.modelo.Cancion
import com.example.soundstation.ui.theme.SoundStationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarCancionScreen(
    playlistId: String, // Recibe el ID de la playlist a la que se agregarán las canciones.
    navController: NavHostController, // Controlador de navegación para gestionar las pantallas de la aplicación.
    viewModel: PlaylistViewModel = viewModel() // ViewModel que maneja la lógica de la playlist
) {
    // Estado para almacenar las canciones globales (todas las canciones disponibles).
    val cancionesGlobales = remember { mutableStateListOf<Cancion>() }

    // Efecto lanzado al cargar la pantalla para obtener las canciones globales.
    LaunchedEffect(Unit) {
        // Asegurarse de que la playlist está cargada.
        viewModel.cargarCancionesDePlaylist(playlistId)
        // Método para cargar todas las canciones globales disponibles.
        viewModel.cargarCancionesGlobales()
        // Limpiar las canciones previas en el estado.
        cancionesGlobales.clear()
        // Añadir las canciones obtenidas desde el ViewModel al estado de canciones globales.
        cancionesGlobales.addAll(viewModel.canciones.value)
    }

    // Uso Scaffold para una estructura básica para la pantalla con topBar y bottomBar.
    Scaffold(
        bottomBar = {
            // Barra de navegación inferior.
            BottomNavigationBar(navController)
        },
        topBar = {
            // Barra superior con el título.
            TopAppBar(
                title = { Text("Agregar Canción") }, // Título de la pantalla
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFFE53935)) // Color de fondo de la barra superior.
            )
        }
    ) { padding ->
        // Caja principal de la pantalla (Box).
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            // Si no hay canciones disponibles, muestra un mensaje.
            if (cancionesGlobales.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center // Centro el mensaje en la pantalla.
                ) {
                    Text(text = "No hay canciones disponibles para agregar.") // Mensaje cuando no hay canciones.
                }
            } else {
                // Si hay canciones, mostrar la lista.
                LazyColumn {
                    items(cancionesGlobales) { cancion ->
                        // Cada canción se muestra en un item, con la opción de agregarla.
                        CancionItem(cancion = cancion, onDelete = { })
                    }
                }
            }
        }
    }

    // Composable que define la apariencia de cada item de canción.
    @Composable
    fun CancionItem(cancion: Cancion, onDelete: () -> Unit) {
        // Card para mostrar cada canción.
        Card(
            modifier = Modifier
                .fillMaxWidth() // Hace que el Card ocupe el ancho completo.
                .padding(8.dp) // Añade margen alrededor del Card.
        ) {
            // Fila para alinear los elementos de cada canción (título, artista, ícono de agregar).
            Row(
                modifier = Modifier.padding(16.dp), // Espaciado dentro de la fila.
                horizontalArrangement = Arrangement.SpaceBetween, // Espacio entre los elementos en horizontal.
                verticalAlignment = Alignment.CenterVertically // Alineación vertical al centro.
            ) {
                // Columna para mostrar título y artista de la canción.
                Column {
                    Text(text = cancion.titulo, fontWeight = FontWeight.Bold) // Titulo de la canción en negrita.
                    Text(text = cancion.artista) // Artista de la canción.
                }
                // Botón para agregar la canción a la playlist.
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar Canción") // Ícono de agregar.
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVentanaAgregarCancion() {
    /*
    // Crear valores simulados para el preview (para ver la pantalla sin necesidad de datos reales).
    val fakePlaylistId = "fakePlaylistId" // ID ficticio de la playlist.
    val fakeNavController = rememberNavController() // Controlador de navegación simulado.
    val fakeViewModel = PlaylistViewModel() // Instancia simulada del ViewModel.

    SoundStationTheme {
        // Vista previa de la pantalla de agregar canción, usando los valores simulados.
        AgregarCancionScreen(
            playlistId = fakePlaylistId,
            navController = fakeNavController,
            viewModel = fakeViewModel
        )
    }

     */
}
