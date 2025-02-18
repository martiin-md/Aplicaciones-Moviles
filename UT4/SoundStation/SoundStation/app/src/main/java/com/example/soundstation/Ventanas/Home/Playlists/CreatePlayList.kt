@file:Suppress("DEPRECATION")

package com.example.soundstation.Ventanas.Home.Playlists

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
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
import com.example.soundstation.modelo.Playlist
import com.example.soundstation.ui.theme.SoundStationTheme
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearPlaylistScreen(navController: NavHostController, viewModel: PlaylistViewModel = viewModel()) {
    // estados para manejar los campos de entrada y las listas de canciones
    val nombrePlaylist = remember { mutableStateOf("") }
    val cancionesSeleccionadas = remember { mutableStateOf<List<String>>(emptyList()) }
    // Estado para las canciones disponibles
    val cancionesDisponibles by viewModel.canciones.collectAsState(initial = emptyList())

    // Obtiene el usuario actual de Firebase Auth
    val usuario = FirebaseAuth.getInstance().currentUser
    val userName = remember { mutableStateOf(usuario?.displayName ?: "Usuario Desconocido") }

    // Cargo las canciones al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.cargarCancionesGlobales() // Llama al ViewModel para cargar las canciones
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) // Barra de navegación en la parte inferior
        }
    ) { padding -> // Padding que se aplica a la vista
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFE53935)) // Color de fondo de la vista
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Crear Playlist",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White // Título en color blanco
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espaciador entre elementos

                // Campo para el nombre de la playlist
                OutlinedTextField(
                    value = nombrePlaylist.value,
                    onValueChange = { nombrePlaylist.value = it }, // Actualiza el estado cuando se cambia el texto
                    label = { Text("Nombre de la Playlist", color = Color.White) }, // Etiqueta del campo
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color.Gray,
                        cursorColor = Color.White,
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Texto indicando que se seleccionen canciones
                Text(
                    text = "Selecciona canciones para agregar:",
                    fontSize = 18.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Lista de canciones disponibles
                LazyColumn(modifier = Modifier.fillMaxHeight(0.6f)) {
                    items(cancionesDisponibles) { cancion -> // Itera sobre las canciones disponibles
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .background(
                                    if (cancion.id in cancionesSeleccionadas.value) Color.Gray else Color.Transparent
                                )
                                .clickable {
                                    cancionesSeleccionadas.value = if (cancion.id in cancionesSeleccionadas.value) {
                                        cancionesSeleccionadas.value - cancion.id // Elimina si ya está seleccionada
                                    } else {
                                        cancionesSeleccionadas.value + cancion.id // Agrega si no está seleccionada
                                    }
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = cancion.titulo,
                                color = Color.White,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para guardar la playlist
                Button(
                    onClick = {
                        if (nombrePlaylist.value.isNotBlank() && cancionesSeleccionadas.value.isNotEmpty()) {
                            val playlist = Playlist(
                                id = System.currentTimeMillis().toString(),  // ID único para la playlist
                                nombre = nombrePlaylist.value,
                                creador = userName.value,  // Nombre del usuario que crea la playlist
                                fechaCreacion = System.currentTimeMillis().toString(),
                                canciones = cancionesSeleccionadas.value // Lista de canciones seleccionadas
                            )
                            // Guarda la playlist a Firestore a través del ViewModel
                            viewModel.crearPlaylist(playlist)
                            navController.popBackStack()  // Regresa a la pantalla anterior
                        } else {
                            Log.e("CrearPlaylistScreen", "El nombre o las canciones están vacíos")
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61)), // Color del botón
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Guardar Playlist", color = Color.White) // Texto del botón
                }

            }
        }
    }
}

// Componente para mostrar cada elemento de la playlist
@Composable
fun PlaylistItem(playlist: Playlist, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth() // que ocupe la pantalla entera
            .padding(vertical = 8.dp)
            .background(Color.White), //Fondo de color blanco
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = playlist.nombre, fontWeight = FontWeight.Bold)
        }
    }
}

// Vista previa del diseño de la pantalla
@Preview(showBackground = true)
@Composable
fun PreviewCrearPlaylistVentana() {
    SoundStationTheme {
        CrearPlaylistScreen(navController = rememberNavController())
    }
}
