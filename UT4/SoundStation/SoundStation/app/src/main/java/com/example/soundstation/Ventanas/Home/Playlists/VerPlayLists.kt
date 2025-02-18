package com.example.soundstation.Ventanas.Home.Playlists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.Ventanas.Home.BottomNavigationBar
import com.example.soundstation.ViewModel.PlaylistViewModel
import com.example.soundstation.modelo.Cancion
import com.example.soundstation.modelo.Playlist
import com.example.soundstation.ui.theme.SoundStationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerPlaylistsScreen(navController: NavHostController, viewModel: PlaylistViewModel = viewModel()) {
    // Cargar las playlists al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.cargarPlaylists()
    }

    // Obtenemos las playlists desde el ViewModel
    val playlists by viewModel.playlists.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Playlists") },
                actions = {
                    // Botón para navegar a la pantalla de crear playlist
                    IconButton(onClick = {
                        navController.navigate("crear_playlist")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Agregar Playlist"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController) // Barra de navegación inferior
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE53935)) // Color de fondo
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Mis Playlists",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Si no hay playlists, muestro un mensaje
                if (playlists.isEmpty()) {
                    Text("No tienes playlists aún.", color = Color.White, fontSize = 18.sp)
                } else {
                    // Lista de playlists
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(playlists) { playlist ->
                            // Cada playlist se renderiza con un item clickable
                            PlaylistItem(playlist = playlist, onClick = {
                                // Navegar a la vista de detalles de la playlist
                                navController.navigate("playlist_details/${playlist.id}")
                            }, onDelete = {
                                // Llamamos al método para eliminar la playlist
                                viewModel.eliminarPlaylist(playlist.id)
                            })
                        }
                    }
                }
            }
        }
    }
}

// Componente para cada item de playlist
@Composable
fun PlaylistItem(playlist: Playlist, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() } // Navegar al hacer clic
            .background(Color.White) //fondo blanco
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = playlist.nombre,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp //De tamaño 18
                )
                Text(
                    text = "Creador: ${playlist.creador}",
                    fontSize = 14.sp, //Tamaño 14
                    color = Color.Gray // De color gris
                )
                Text(
                    text = "${playlist.canciones.size} Canciones", // Mostrar el número de canciones
                    fontSize = 14.sp, //Tamaño 14
                    color = Color.Gray // De color gris
                )
            }
            IconButton(onClick = onDelete) { // Botón para eliminar la playlist
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar Playlist")
            }
        }
    }
}

// Componente para cada canción en la lista de canciones de una playlist
@Composable
fun CancionItem(cancion: Cancion, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = cancion.titulo, fontWeight = FontWeight.Bold)
                Text(text = cancion.artista) // Mostrar el artista de la canción
            }
            IconButton(onClick = onDelete) { // Botón para eliminar la canción
                Icon(Icons.Filled.Delete, contentDescription = "Eliminar Canción") //Icono de eliminar
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewVerPlaylistsScreen() {
    SoundStationTheme {
        VerPlaylistsScreen(navController = rememberNavController())
    }
}
