package com.example.soundstation.Ventanas.Otras

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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

@Composable
fun MiniReproductor(context: Context, titulo: String, artista: String, url: String, onExpandReproductor: () -> Unit) {
    // Estado para el MediaPlayer y la reproducción
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    // Reproduce el audio cuando el URL cambia
    LaunchedEffect(url) {
        // Se libera cualquier recurso anterior si el URL cambia
        mediaPlayer?.release()
        // Creo un nuevo MediaPlayer para el nuevo URL
        mediaPlayer = MediaPlayer().apply {
            setDataSource(url) // Asigno el archivo de audio
            prepareAsync() // Sincronizo la reproducción
        }
    }

    // Contenedor de la interfaz de usuario (UI)
    Row(
        modifier = Modifier
            .fillMaxWidth() // Toma todo el ancho disponible
            .background(Color(0xFFB61C1C)) // Color de fondo
            .padding(8.dp) // Espaciao
            .clickable { onExpandReproductor() }, // Abre el reproductor expandido al hacer clic
        verticalAlignment = Alignment.CenterVertically // Centra el contenido verticalmente
    ) {
        // Imagen de la canción
        Image(
            painter = painterResource(R.drawable.logosoundstation), // Reemplaza con tu recurso de imagen
            contentDescription = "Carátula de la canción", // Descripción para accesibilidad
            modifier = Modifier
                .size(50.dp) // Tamaño de la imagen
                .background(Color.Gray, shape = RoundedCornerShape(8.dp)), // Fondo y bordes redondeados
            contentScale = ContentScale.Crop // Recorta la imagen para que llene el espacio
        )

        Spacer(modifier = Modifier.width(8.dp)) // Espacio entre la imagen y la información

        // Información de la canción (título y artista)
        Column(modifier = Modifier.weight(1f)) {
            // Título de la canción
            Text(
                text = titulo,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 1 // Limita el título a una línea
            )
            // Nombre del artista
            Text(
                text = artista,
                color = Color.LightGray,
                fontSize = 14.sp,
                maxLines = 1 // Limita el nombre del artista a una línea
            )
        }

        Spacer(modifier = Modifier.width(8.dp)) // Espacio

        // Botón para reproducir/pausar la canción
        IconButton(onClick = {
            // Si está reproduciendo, pausa la canción
            if (isPlaying) {
                mediaPlayer?.pause()
                isPlaying = false
            } else {
                // Si está pausada, inicia la reproducción
                mediaPlayer?.start()
                isPlaying = true
            }
        }) {
            // Muestra un ícono de play o pause dependiendo del estado de reproducción
            Icon(
                painter = painterResource(if (isPlaying) R.drawable.icono_pause_24 else R.drawable.icono_play_24),
                contentDescription = "Reproducir/Pausar",
                tint = Color.White // Color blanco para el ícono
            )
        }
    }
}
