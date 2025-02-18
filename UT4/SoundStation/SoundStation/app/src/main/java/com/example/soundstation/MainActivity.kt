package com.example.soundstation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.Ventanas.Otras.AppNavigation
import com.example.soundstation.ui.theme.SoundStationTheme
import com.google.firebase.auth.FirebaseAuth

class MainActivity : ComponentActivity() {
    // Método llamado cuando la actividad se crea
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Contenido
        setContent {
            SoundStationTheme {
                //Controlador para navegar
                val navController = rememberNavController()

                // LaunchedEffect se ejecuta una vez cuando se crea la pantalla
                LaunchedEffect(key1 = true) {
                    // Se obtiene el usuario actual desde FirebaseAuth
                    val usuario = FirebaseAuth.getInstance().currentUser
                    // Si el usuario está autenticado
                    if (usuario != null) {
                        // Navega directamente a la pantalla "home", eliminando la pantalla "login"
                        navController.navigate("home") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                }

                AppNavigation(navController = navController)
            }
        }
    }
}
