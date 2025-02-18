package com.example.calculadoraconversora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculadoraconversora.ui.theme.CalculadoraConversoraTheme
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraConversoraTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background) {
                    Frame1()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Frame1(modifier: Modifier = Modifier) {
    var centimetros by remember { mutableStateOf("") } //Creo una variable para almacena el valor actual "cm"
    var pulgadas by remember { mutableStateOf("") } //Creo una variable que el almacena el valor de pulgadas "px"
    var cambios by remember { mutableStateOf("") } //Creo otra variable para que almacene el ultimo cambio

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),//Espacio vertical
        horizontalAlignment = Alignment.CenterHorizontally, //Alineo los elementos horizontalmente al centro
        modifier = modifier
            .fillMaxWidth() //HagO que oupe todo el ancho de la pantalla
            .background(color = Color(0xfffc1c1c)) //Con un fondo de color rojo
            .border(BorderStroke(1.dp, Color.Black)) //Y que tenga un borde de texto de color negro
            .padding(16.dp) // También añado un paddin
    ) {
        item {
            Text(
                text = "CONVERSOR DE CM A PULGADAS", //Muestro el contenido que tendra el texto
                color = Color.White, //De color blanco
                textAlign = TextAlign.Center, //Alineado en el centro
                style = TextStyle(fontSize = 22.sp), //Pongo un tamaño para el titulo
                modifier = Modifier.fillMaxWidth() //El texto ocupe todo el anco de la pantalla
            )
        }
        item {
            Text(
                text = "Centímetros", //Muestro el contenido del siguiente texto
                color = Color.White, //Con color blanco
                textAlign = TextAlign.Center, //Alineado tambien en el centro
                style = TextStyle(fontSize = 20.sp), //Con un tamaño de texto 20.sp
                modifier = Modifier
                    .fillMaxWidth() //Ocupe todo el ancho de pantalla
                    .padding(vertical = 8.dp) //y tambien tenga un espacio vertical de 8.dp
            )
        }
        item {
            TextField(
                value = centimetros,
                onValueChange = { input ->
                    centimetros = input
                    cambios = "centimetros" //

                    // Realizo el Calculo y actualizo despues el valor de pulgadas si el input no está vacío
                    pulgadas = if (input.isNotEmpty()) {
                        val cmValue = input.toFloatOrNull() //Convierto el input en un float
                        if (cmValue != null) {
                            (round((cmValue / 2.54) * 100) / 100).toString() //calculo el calculo en cm
                        } else pulgadas
                    } else ""
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color(0xffd9d9d9) //Color de fondo del campo cm
                ),
                modifier = Modifier.fillMaxWidth() //Realizo que ocupe todo el ancho de pantalla
                    .padding(horizontal = 16.dp), //Después tengo un espacio de 16.dp
                placeholder = { Text("Ingrese centímetros") }, //Contenido que tendra el campo
                textStyle = TextStyle(fontSize = 16.sp) //con un tamaño de estilo 16.sp
            )
        }
        item {
            Text(
                text = "Pulgadas", //etiqueta de contenido que tendra el texto
                color = Color.White, //De color también blanco
                textAlign = TextAlign.Center, //alineado en el centro
                style = TextStyle(fontSize = 20.sp), //también con un tamaño de texto 20.sp
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 8.dp)
            )
        }
        item {
            TextField(
                value = pulgadas,
                onValueChange = { input ->
                    pulgadas = input
                    cambios = "pulgadas" // Marcar como el último campo cambiado

                    // Realizo el Calculo y actualizo el valor de centímetros si el input no está vacío
                    centimetros = if (input.isNotEmpty()) {
                        val inValue = input.toFloatOrNull()
                        if (inValue != null) {
                            (round((inValue * 2.54) * 100) / 100).toString() //calculo el calculo cambiendolo a px
                        } else centimetros
                    } else ""
                },
                colors = TextFieldDefaults.textFieldColors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    containerColor = Color(0xffd9d9d9)
                ),
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text("Ingrese pulgadas") }, //En el campo le muestre este contenido
                textStyle = TextStyle(fontSize = 16.sp) //con un tamaño de 16.sp
            )
        }
    }
}

@Preview(widthDp = 411, heightDp = 731)
@Composable
private fun Frame1Preview() {
    Frame1(Modifier)
}