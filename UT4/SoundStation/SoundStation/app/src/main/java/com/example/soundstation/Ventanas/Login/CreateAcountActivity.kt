@file:Suppress("DEPRECATION")

package com.example.soundstation.Ventanas.Login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.TextFieldDefaults.outlinedTextFieldColors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.soundstation.R
import com.example.soundstation.ui.theme.SoundStationTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VentanaCrearCuenta(navController: NavHostController) {
    // Declaración de variables para manejar el estado de los campos de entrada y errores
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var contrasenia by remember { mutableStateOf("") }
    var contraseniaVisible by remember { mutableStateOf(false) }
    var mensajeError by remember { mutableStateOf("") }

    // Contexto para acceder a funciones relacionadas con el contexto
    val context = LocalContext.current

    // Instancia de FirebaseAuth para gestionar la autenticación
    val auth = FirebaseAuth.getInstance()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE53935)) // Fondo rojo para la ventana
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagen del logo de la aplicación
            Image(
                painter = painterResource(id = R.drawable.logosoundstation),
                contentDescription = "App Logo",
                modifier = Modifier.size(100.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título de la pantalla
            Text(
                text = "Registrar Cuenta",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 28.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campo de texto para el nombre completo
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it }, // Actualiza el estado del nombre
                label = { Text("Nombre Completo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para el email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it }, // Actualiza el estado del email
                label = { Text("Email") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para la contraseña
            OutlinedTextField(
                value = contrasenia,
                onValueChange = { contrasenia = it }, // Actualiza el estado de la contraseña
                label = { Text("Contraseña") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                visualTransformation =
                if (contraseniaVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                colors = outlinedTextFieldColors(
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje de error si hay algún problema en la creación de la cuenta
            if (mensajeError.isNotEmpty()) {
                Text(
                    text = mensajeError,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Botón para crear la cuenta
            Button(
                onClick = {
                    // Verifica si los campos están vacíos
                    if (nombre.isBlank() || email.isBlank() || contrasenia.isBlank()) {
                        mensajeError = "Por favor completa todos los campos" // Muestra mensaje de error
                    } else {
                        // Intento de creación de cuenta con Firebase Authentication
                        auth.createUserWithEmailAndPassword(email, contrasenia)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    // Si la cuenta se crea con éxito, muestra un Toast y navega a la pantalla principal
                                    Toast.makeText(context, "Cuenta creada con éxito", Toast.LENGTH_SHORT).show()
                                    mensajeError = "" // Resetea el mensaje de error
                                    navController.navigate("home") {
                                        popUpTo("signup") { inclusive = true } // Elimina la pantalla de registro de la pila de navegación
                                    }
                                } else {
                                    // Si hay un error, muestra un mensaje específico
                                    val exception = task.exception
                                    mensajeError = when (exception) {
                                        is FirebaseAuthUserCollisionException -> "Este correo ya está registrado"
                                        is FirebaseAuthInvalidCredentialsException -> "Correo o contraseña inválidos"
                                        else -> "Error al crear la cuenta"
                                    }
                                }
                            }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB71C1C)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Crear Cuenta", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Enlace para ir a la pantalla de inicio de sesión si ya se tiene una cuenta
            TextButton(
                onClick = { navController.navigate("login") }
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = Color.White)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewVentanaCrearCuenta() {
    SoundStationTheme {
        VentanaCrearCuenta(navController = rememberNavController())
    }
}
