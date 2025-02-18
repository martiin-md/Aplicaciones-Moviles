@file:Suppress("DEPRECATION")

package com.example.soundstation.Ventanas.Perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundstation.ui.theme.SoundStationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditarPerfilScreen() {
    // Estados para mantener los valores de la selección del menú y los datos del usuario
    val seleccionMenu = remember { mutableStateOf(2) } // Perfil seleccionado por defecto
    val nombreUsuario = remember { mutableStateOf("") }
    val correoUsuario = remember { mutableStateOf("") }

    Scaffold(
        bottomBar = {
            // Barra de navegación en la parte inferior
            NavigationBar(containerColor = Color(0xFFE53935)) { // Color rojo para la barra
                // Elementos de la barra de navegación
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = seleccionMenu.value == 0, // Marca el elemento de Home como seleccionado
                    onClick = { seleccionMenu.value = 0 } // TODO: Navegar a Home
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Search, contentDescription = "Buscar") },
                    label = { Text("Buscar") },
                    selected = seleccionMenu.value == 1, // Marca el elemento de Buscar como seleccionado
                    onClick = { seleccionMenu.value = 1 } // TODO: Navegar a Buscar
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Perfil") },
                    label = { Text("Perfil") },
                    selected = seleccionMenu.value == 2, // Marca el elemento de Perfil como seleccionado
                    onClick = { seleccionMenu.value = 2 } // Mantiene en Perfil
                )
            }
        }
    ) { padding -> // Padding para la vista
        Box(
            modifier = Modifier
                .fillMaxSize() // Ocupa todo el espacio disponible
                .padding(padding) // Aplica el padding recibido
                .background(Color(0xFFE53935)) // Fondo rojo para la pantalla
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize() // Ocupa todo el espacio disponible
                    .padding(16.dp), // Padding alrededor de la columna
                horizontalAlignment = Alignment.CenterHorizontally, // Centra horizontalmente
                verticalArrangement = Arrangement.Top // Alinea los elementos en la parte superior
            ) {
                // Título de la pantalla
                Text(
                    text = "Editar Perfil", // Título visible en la pantalla
                    fontSize = 24.sp, // Tamaño de fuente
                    fontWeight = FontWeight.Bold, // Negrita
                    color = Color.White, // Color blanco para el texto
                    modifier = Modifier.padding(bottom = 24.dp) // Padding inferior
                )

                // Campo para ingresar el nombre de usuario
                OutlinedTextField(
                    value = nombreUsuario.value, // Valor del nombre de usuario
                    onValueChange = { nombreUsuario.value = it }, // Actualiza el valor cuando cambia
                    label = { Text("Nombre de Usuario") }, // Etiqueta del campo
                    singleLine = true, // Asegura que el campo sea de una sola línea
                    modifier = Modifier.fillMaxWidth(), // Hace que el campo ocupe todo el ancho
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White, // Color del borde cuando está enfocado
                        unfocusedBorderColor = Color.LightGray, // Color del borde cuando no está enfocado
                        cursorColor = Color.White // Color del cursor
                    )
                )

                Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los campos

                // Campo para ingresar el correo electrónico
                OutlinedTextField(
                    value = correoUsuario.value, // Valor del correo electrónico
                    onValueChange = { correoUsuario.value = it }, // Actualiza el valor cuando cambia
                    label = { Text("Correo Electrónico") }, // Etiqueta del campo
                    singleLine = true, // Asegura que el campo sea de una sola línea
                    modifier = Modifier.fillMaxWidth(), // Hace que el campo ocupe todo el ancho
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color.White, // Color del borde cuando está enfocado
                        unfocusedBorderColor = Color.LightGray, // Color del borde cuando no está enfocado
                        cursorColor = Color.White // Color del cursor
                    )
                )

                Spacer(modifier = Modifier.height(32.dp)) // Espacio

                // Botón para guardar los cambios
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6F61)), // Color del botón
                    modifier = Modifier.fillMaxWidth() // Hace que el botón ocupe todo el ancho
                ) {
                    Text("Guardar Cambios", color = Color.White) // Texto del botón
                }

                Spacer(modifier = Modifier.height(16.dp)) // Espacio

                // Botón para cancelar los cambios
                OutlinedButton(
                    onClick = {

                    },
                    modifier = Modifier.fillMaxWidth() // Hace que el botón ocupe todo el ancho
                ) {
                    Text("Cancelar", color = Color.White) // Texto del botón
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewEditarPerfilScreen() {
    SoundStationTheme {
        EditarPerfilScreen()
    }
}
