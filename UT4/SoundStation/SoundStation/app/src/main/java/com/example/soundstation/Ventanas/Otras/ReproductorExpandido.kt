package com.example.soundstation.Ventanas.Otras

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundstation.R

// Reproductor expandido de música con controles de reproducción
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") // Suprimo una advertencia sobre el uso de Scaffold
@Composable
fun ReproductorExpandido(context: Context, titulo: String, artista: String, url: String, onClose: () -> Unit) {
    // Estado para gestionar el MediaPlayer y la reproducción
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    // Reproduce el audio cuando el URL cambia
    LaunchedEffect(url) {
        // Libera cualquier recurso anterior si el URL cambia
        mediaPlayer?.release()
        // Crea un nuevo MediaPlayer para el nuevo URL
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url) // Asigno el archivo de audio
            prepareAsync() // Despues sincronizo la  reproducción
        }
    }

    Scaffold(
        content = {
            // Columna principal que contiene todos los elementos
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Añade un relleno alrededor del contenido
                horizontalAlignment = Alignment.CenterHorizontally, // Centra el contenido horizontalmente
                verticalArrangement = Arrangement.Center // Centra el contenido verticalmente
            ) {
                // Imagen de la canción
                Image(
                    painter = painterResource(R.drawable.logosoundstation),
                    contentDescription = "Descripción de la canción", // Descripción
                    modifier = Modifier
                        .size(200.dp) // tamaño de la imagen
                        .background(Color.Gray, shape = RoundedCornerShape(16.dp)), // Fondo gris y bordes redondeados
                    contentScale = ContentScale.Crop // Se recorta la imagen para ajustarse al espacio
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espacio

                // Título de la canción
                Text(
                    text = titulo,
                    fontWeight = FontWeight.Bold, // Estilo en negrita
                    fontSize = 24.sp, // Tamaño de fuente grande
                    maxLines = 1 // Limita el título a una sola línea
                )
                // Artista de la canción
                Text(
                    text = artista,
                    color = Color.Gray, // Color gris para el nombre del artista
                    fontSize = 20.sp, // Tamaño de fuente más pequeño para el artista
                    maxLines = 1 // Limita el nombre del artista a una sola línea
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre la información y los controles

                // Controles de reproducción (anterior, reproducir/pausar, siguiente)
                Row(
                    modifier = Modifier.fillMaxWidth(), // Aseguro que el Row ocupe todo el ancho disponible
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    // Botón para la canción anterior
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(R.drawable.icono_skip_previous_24), // Ícono de "anterior"
                            contentDescription = "Anterior" // Descripción
                        )
                    }

                    // Botón para reproducir/pausar la canción
                    IconButton(onClick = {
                        if (isPlaying) { // Si está reproduciendo, pausa la canción
                            mediaPlayer?.pause()
                            isPlaying = false
                        } else { // Si está pausada, comienza la reproducción
                            mediaPlayer?.start()
                            isPlaying = true
                        }
                    }) {
                        // Ícono de play o pausa dependiendo del estado
                        Icon(
                            painter = painterResource(if (isPlaying) R.drawable.icono_pause_24 else R.drawable.icono_play_24),
                            contentDescription = "Reproducir/Pausar" // Descripción accesible
                        )
                    }

                    // Botón para la canción siguiente
                    IconButton(onClick = {  }) {
                        Icon(
                            painter = painterResource(R.drawable.icono_skip_next_24), // Ícono de "siguiente"
                            contentDescription = "Siguiente" // Descripción accesible
                        )
                    }
                }
            }
        }
    )
}
