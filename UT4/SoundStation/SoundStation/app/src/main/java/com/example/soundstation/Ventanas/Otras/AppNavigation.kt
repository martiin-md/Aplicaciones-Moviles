package com.example.soundstation.Ventanas.Otras

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.soundstation.BuscarScreen
import com.example.soundstation.Ventanas.Home.Canciones.AgregarCancionScreen
import com.example.soundstation.Ventanas.Home.Canciones.VentanaCanciones
import com.example.soundstation.Ventanas.Home.Playlists.CrearPlaylistScreen
import com.example.soundstation.Ventanas.Home.Playlists.DetallesPlaylistScreen
import com.example.soundstation.Ventanas.Home.Playlists.VerPlaylistsScreen
import com.example.soundstation.Ventanas.Home.PrincipalVentana
import com.example.soundstation.Ventanas.Login.VentanaCrearCuenta
import com.example.soundstation.Ventanas.Login.VentanaLogin
import com.example.soundstation.Ventanas.Perfil.VentanaPerfil
import com.example.soundstation.ViewModel.PlaylistViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation(navController: NavHostController) {
    // Obtenemos el usuario autenticado actualmente de Firebase
    val usuario = FirebaseAuth.getInstance().currentUser

    // Definimos la pantalla inicial dependiendo de si el usuario está autenticado o no
    NavHost(navController = navController,
        startDestination = if (usuario != null) "home" else "login") {

        // Ruta para la pantalla de inicio de sesión
        composable("login") { VentanaLogin(navController = navController) }

        // Ruta para la pantalla de creación de cuenta
        composable("crear_cuenta") { VentanaCrearCuenta(navController) }

        // Ruta para cuando el usuario cierra sesión, se redirige al login
        composable("cerrar_sesion") { VentanaLogin(navController) }

        // Pantalla principal del usuario, se muestra después de iniciar sesión
        composable("home") { PrincipalVentana(navController) }

        // Ruta para la pantalla de búsqueda de canciones o playlists
        composable("buscar") { BuscarScreen(navController) }

        // Ruta para la pantalla de perfil del usuario
        composable("perfil") { VentanaPerfil(navController) }

        // Ruta para crear una nueva playlist
        composable("crear_playlist") { CrearPlaylistScreen(navController) }

        // Ruta para ver las canciones del usuario
        composable("canciones") { VentanaCanciones(navController) }

        // Ruta para ver una playlist específica
        composable(
            route = "ver_playlist/{playlists}",
            arguments = listOf(navArgument("playlists") { type = NavType.StringType })
        ) { backStackEntry ->
            // Obtenemos el nombre o id de la playlist
            val playlists = backStackEntry.arguments?.getString("playlists") ?: ""
            // Mostramos la pantalla para ver detalles de la playlist
            VerPlaylistsScreen(navController)
        }

        // Ruta para ver los detalles de una playlist específica por ID
        composable("playlist_details/{playlistId}") { backStackEntry ->
            // Obtenemos el ID de la playlist desde los argumentos
            val playlistId = backStackEntry.arguments?.getString("playlistId")
            if (playlistId != null) {
                // Mostramos la pantalla de detalles de la playlist, pasando el ID de la playlist
                DetallesPlaylistScreen(navController = navController, playlistId = playlistId,
                    onBack = { navController.popBackStack() })
            }
        }

        // Ruta para agregar una canción a una playlist específica, pasando el ID de la playlist como argumento
        composable("agregarCancion/{playlistId}") { backStackEntry ->
            // Obtenemos el ID de la playlist desde los argumentos
            val playlistId = backStackEntry.arguments?.getString("playlistId") ?: ""
            // Mostramos la pantalla para agregar canciones a la playlist
            AgregarCancionScreen(playlistId = playlistId, navController = navController)
        }
    }
}
