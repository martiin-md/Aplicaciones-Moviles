package com.example.patoapp

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.patoapp.ui.theme.PatoAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var archivo: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PatoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GreetingImage { playSonido() }
                }
            }
        }

        archivo = MediaPlayer.create(this, R.raw.patosonido)
    }

    private fun playSonido() {

        if (archivo.isPlaying) {
            archivo.stop()
            archivo.prepare()
        }
        archivo.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        archivo.release()
    }
}

@Composable
fun GreetingImage(modifier: Modifier = Modifier, onButtonClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Imagen superior
        val topImage = painterResource(R.drawable.pachaibz)
        Image(
            painter = topImage,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(width = 300.dp, height = 250.dp)
                .padding(top = 16.dp)
        )

        // Imagen del pato
        val patoImage = painterResource(R.drawable.patoibiza)
        Image(
            painter = patoImage,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(width = 300.dp, height = 350.dp)
                .padding(top = 6.dp)
        )

        // Botón para reproducir el sonido
        Button(onClick = onButtonClick, modifier = Modifier.padding(16.dp)) {
            Text("¡Reproducir sonido del pato!")
        }

        // Imagen inferior
        val bottomImage = painterResource(R.drawable.logocerezaibz)
        Image(
            painter = bottomImage,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(width = 200.dp, height = 250.dp)
                .padding(bottom = 16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PatoAppTheme {
        GreetingImage(onButtonClick = {})
    }
}