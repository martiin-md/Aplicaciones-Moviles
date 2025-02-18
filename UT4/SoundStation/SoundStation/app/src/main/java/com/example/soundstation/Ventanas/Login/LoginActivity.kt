@file:Suppress("DEPRECATION")

package com.example.soundstation.Ventanas.Login
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.R
import com.example.soundstation.ui.theme.SoundStationTheme
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentanaLogin(navController: NavHostController) {
    // Estados para manejar los valores de email, contraseña y estado de carga
    var email by rememberSaveable { mutableStateOf("") }
    var contrasenia by rememberSaveable { mutableStateOf("") }
    val auth = remember { FirebaseAuth.getInstance() } // Instancia de FirebaseAuth
    var isLoading by remember { mutableStateOf(false) } // Estado para mostrar el indicador de carga
    val context = LocalContext.current // Contexto de la aplicación para mostrar Toasts

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE53935)) // Fondo rojo
    ) {
        // Si está cargando, se muestra un indicador de carga
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.align(Alignment.Center) // Centrado del indicador de carga
            )
        } else {
            // Diseño de la columna para mostrar los elementos en la pantalla
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                // Logo de la aplicación
                Image(
                    painter = painterResource(id = R.drawable.logosoundstation),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp) // Tamaño del logo
                )

                // Título de la aplicación
                Text(
                    text = "SoundStation",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espaciado entre los elementos

                // Campo de texto para el correo electrónico
                TextField(
                    value = email,
                    onValueChange = { email = it }, // Actualiza el estado del email
                    label = { Text("Correo Electrónico") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email) // Teclado de tipo correo
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espaciado

                // Campo de texto para la contraseña
                TextField(
                    value = contrasenia,
                    onValueChange = { contrasenia = it }, // Actualiza el estado de la contraseña
                    label = { Text("Contraseña") },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation() // Transforma la contraseña para ocultarla
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espaciado

                // Botón para iniciar sesión
                Button(
                    onClick = {
                        // Verifica si ambos campos no están vacíos
                        if (email.isNotEmpty() && contrasenia.isNotEmpty()) {
                            isLoading = true // Muestra el indicador de carga
                            auth.signInWithEmailAndPassword(email, contrasenia) // Intentar iniciar sesión
                                .addOnCompleteListener { task ->
                                    isLoading = false // Oculta el indicador de carga
                                    if (task.isSuccessful) {
                                        // Si el inicio de sesión es exitoso, navega a la pantalla principal
                                        navController.navigate("home") {
                                            popUpTo("login") { inclusive = true } // Elimina la pantalla de login
                                        }
                                    } else {
                                        // Si hay un error, muestra un mensaje
                                        Toast.makeText(
                                            context,
                                            "Error: ${task.exception?.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            // Muestra un mensaje si faltan campos
                            Toast.makeText(context, "Por favor llena todos los campos", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C)) // Color del botón
                ) {
                    Text("Iniciar sesión", color = Color.White) // Texto del botón
                }

                Spacer(modifier = Modifier.height(16.dp)) // Espaciado

                // Texto con enlace para ir a la pantalla de creación de cuenta
                TextButton(
                    onClick = { navController.navigate("crear_cuenta") }
                ) {
                    Text("¿No tienes cuenta? Crear cuenta", color = Color.White)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVentanaLogin() {
    SoundStationTheme {
        VentanaLogin(navController = rememberNavController())
    }
}
