package com.example.soundstation.Ventanas.Home.Canciones

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.soundstation.Ventanas.Home.BottomNavigationBar
import com.example.soundstation.Ventanas.Otras.MiniReproductor
import com.example.soundstation.Ventanas.Otras.ReproductorExpandido
import com.example.soundstation.modelo.Cancion
import com.example.soundstation.ui.theme.SoundStationTheme
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentanaCanciones(navController: NavHostController) {
    /*
    Estados
    Estado para almacenar las canciones obtenidas de Firebase
    Estado para controlar el cargado de las canciones
    Instancia de Firestore para obtener las canciones
    Obtención del contexto
    Estado para manejar la canción seleccionada
    Estado para manejar si el reproductor esta expandido
    */

    val canciones = remember { mutableStateListOf<Cancion>() }
    val isLoading = remember { mutableStateOf(true) }
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    var cancionSeleccionada by remember { mutableStateOf<Cancion?>(null) }
    var isExpanded by remember { mutableStateOf(false) } // Controla el estado expandido del reproductor

    // Lógica para cargar canciones desde Firestore
    LaunchedEffect(Unit) {
        try {
            // Se obtiene el snapshot de las canciones desde Firestore
            val snapshot = db.collection("canciones").get().await()
            // Mapeo los documentos obtenidos a una lista de objetos Cancion
            val cancionesFirestore = snapshot.documents.map { doc ->
                Cancion(
                    id = doc.id,
                    titulo = doc.getString("titulo") ?: "Sin título",
                    artista = doc.getString("artista") ?: "Desconocido",
                    url = doc.getString("url") ?: ""
                )
            }
            // Limpio y agrego las canciones obtenidas al estado
            canciones.clear()
            canciones.addAll(cancionesFirestore)
        } catch (e: Exception) {
            Log.e("ErrorFirebase", "Error al cargar canciones: ${e.message}")
        } finally {
            isLoading.value = false // Cambia el estado de carga a false
        }
    }

    // Diseño de la pantalla
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController) // Barra de navegación inferior
        },
        topBar = {
            TopAppBar(
                title = {
                    Text("Canciones") // Título de la pantalla
                        },
                colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color(0xFFB61C1C)) // Color de fondo de la barra superior
            )
        },
        content = { paddingValues ->
            // Contenedor principal con una columna de canciones
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                // Si se está cargando, se muestra un indicador de progreso
                if (isLoading.value) {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                } else {
                    // Se muestra la lista de canciones en un LazyColumn
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        items(canciones) { cancion ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable { cancionSeleccionada = cancion }, // Acción al seleccionar una canción
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    // Título de la canción
                                    Text(
                                        text = cancion.titulo,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 20.sp
                                    )
                                    // Artista de la canción
                                    Text(
                                        text = cancion.artista,
                                        color = Color.Gray,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Mini reproductor que aparece si hay una canción seleccionada
                    cancionSeleccionada?.let { cancion ->
                        if (!isExpanded) {
                            // Muestra el mini reproductor si no está expandido
                            MiniReproductor(
                                context = context,
                                titulo = cancion.titulo,
                                artista = cancion.artista,
                                url = cancion.url
                            ) {
                                isExpanded = true // Expande el reproductor al hacer clic
                            }
                        } else {
                            // Muestra el reproductor expandido
                            ReproductorExpandido(
                                context = context,
                                titulo = cancion.titulo,
                                artista = cancion.artista,
                                url = cancion.url
                            ) {
                                isExpanded = false // Contrae el reproductor
                            }
                        }
                    }
                }
            }
        }
    )
}

// Función para reproducir la canción seleccionada
fun reproducirCancion(context: Context, url: String) {
    val mediaPlayer = MediaPlayer()
    try {
        mediaPlayer.apply {
            setDataSource(url)
            setOnPreparedListener {
                start() // Reproduce la canción cuando está lista
            }
            prepareAsync() // Prepara la canción de manera asíncrona
        }
    } catch (e: Exception) {
        Toast.makeText(context, "Error al reproducir la canción: ${e.message}", Toast.LENGTH_SHORT).show()
    }
}

// Vista previa de la pantalla de canciones
@Composable
@Preview(showBackground = true)
fun PreviewVentanaCanciones() {
    SoundStationTheme {
        // Vista previa sin navegación
    }
}
