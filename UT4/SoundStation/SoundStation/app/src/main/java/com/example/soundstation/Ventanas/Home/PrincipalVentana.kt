package com.example.soundstation.Ventanas.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.R
import com.example.soundstation.ui.theme.SoundStationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipalVentana(navController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController) // Uso la barra de navegación inferior
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize() // ocupa todo el tamaño de la pantalla
                .padding(padding) // Agrega el padding de la pantalla
                .background(Color(0xFFE53935)) // Fondo rojo
        ) {
            // Column para colocar los elementos de la pantalla verticalmente
            Column(
                modifier = Modifier
                    .fillMaxSize() // La columna ocupa todo el espacio disponible
                    .padding(16.dp), // espacio interno para la columna
                horizontalAlignment = Alignment.CenterHorizontally, // Alineación horizontal centrada
                verticalArrangement = Arrangement.Center // Alineación vertical centrada
            ) {

                // Imagen del logo de la app
                Image(
                    painter = painterResource(id = R.drawable.logosoundstation), // Cargar la imagen del logo
                    contentDescription = "App Logo", // Descripción de la imagen
                    modifier = Modifier.size(100.dp) // Establecer el tamaño del logo
                )

                // Título de la pantalla principal
                Text(
                    text = "SoundStation", // Texto que se muestra
                    fontSize = 28.sp, // Tamaño de la fuente
                    fontWeight = FontWeight.Bold, // Negrita
                    color = Color.White // Color del texto blanco
                )

                Spacer(modifier = Modifier.height(32.dp)) // Espacio entre elementos

                // Botón para crear una nueva Playlist
                Button(
                    onClick = {
                        // Al hacer clic, navegamos a la pantalla para crear una nueva playlist
                        navController.navigate("crear_playlist")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61)), // Color de fondo del botón
                    modifier = Modifier
                        .fillMaxWidth() // El botón ocupa todo el ancho disponible
                ) {
                    Text("Crear Playlist", color = Color.White) // Texto dentro del botón
                }

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre botones

                // Botón para ver las playlists existentes
                Button(
                    onClick = {
                        // Al hacer clic, navegamos a la pantalla de ver las playlists
                        navController.navigate("ver_playlist/{playlistId}")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61)), // Color de fondo del botón
                    modifier = Modifier.fillMaxWidth() // El botón ocupa todo el ancho disponible
                ) {
                    Text("Ver Playlist", color = Color.White) // Texto dentro del botón
                }

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre botones

                // Botón para ver canciones
                Button(
                    onClick = {
                        // Al hacer clic, navegamos a la pantalla de canciones
                        navController.navigate("canciones")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61)), // Color de fondo del botón
                    modifier = Modifier.fillMaxWidth() // El botón ocupa todo el ancho disponible
                ) {
                    Text("Ver Canciones", color = Color.White) // Texto dentro del botón
                }
            }
        }
    }
}

// Componente para la barra de navegación inferior
@Composable
fun BottomNavigationBar(navController: NavHostController) {
    // Obtiene la ruta actual de la pantalla para resaltar el ítem seleccionado
    val currentDestination = navController.currentBackStackEntry?.destination?.route

    // Barra de navegación con colores personalizados
    NavigationBar(containerColor = Color(0xFFB71C1C)) {
        // Ítem de navegación "Home"
        NavigationBarItem(
            selected = currentDestination == "home", // Si la pantalla actual es "home", se marca como seleccionado
            onClick = { navController.navigate("home") }, // Al hacer clic, se navega a "home"
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) }, // Icono de "Home"
            label = { Text("Home", color = Color.White) } // Texto para la etiqueta "Home"
        )

        // Ítem de navegación "Buscar"
        NavigationBarItem(
            selected = currentDestination == "buscar", // Si la pantalla actual es "buscar", se marca como seleccionado
            onClick = { navController.navigate("buscar") }, // Al hacer clic, se navega a "buscar"
            icon = { Icon(Icons.Default.Search, contentDescription = "Buscar", tint = Color.White) }, // Icono de "Buscar"
            label = { Text("Buscar", color = Color.White) } // Texto para la etiqueta "Buscar"
        )

        // Ítem de navegación "Perfil"
        NavigationBarItem(
            selected = currentDestination == "perfil", // Si la pantalla actual es "perfil", se marca como seleccionado
            onClick = { navController.navigate("perfil") }, // Al hacer clic, se navega a "perfil"
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.White) }, // Icono de "Perfil"
            label = { Text("Perfil", color = Color.White) } // Texto para la etiqueta "Perfil"
        )
    }
}

// Vista previa de la pantalla principal
@Composable
@Preview(showBackground = true)
fun PreviewMainScreen() {
    // Tema de la aplicación y visualización previa
    SoundStationTheme {
        PrincipalVentana(navController = rememberNavController()) // Vista previa de la pantalla principal con navegación
    }
}
