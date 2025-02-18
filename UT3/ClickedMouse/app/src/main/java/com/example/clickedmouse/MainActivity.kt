package com.example.clickedmouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.clickedmouse.ui.theme.ClickedMouseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ClickedMouseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), //Ocupa toda la pantalla
                    color = Color.Red //La app tiene tambien un fondo rojo predeterminado
                ) {
                    GreetingImage { }
                }
            }
        }

    }

}


@Composable
fun GreetingImage(modifier: Modifier = Modifier, onButtonClick: () -> Unit) {
    var clickCount by remember { mutableStateOf(0) } //Creo esta variable para almacenar y contar el número de clics
    var timeLeft by remember { mutableStateOf(10) } //Creo esta variablepara disponer el tiempo máximo
    var timeExpired by remember { mutableStateOf(false) } //Esta variable comprueba si ha termido el tiempo
    var restartCount by remember { mutableStateOf(0) }  //Esta otra variable sirve para contar el número de reinicios
    val maxLevel = 80 // El máximo de clics de la app

    //Aqui creo otra variable para mostrar por mensajes el tiempo, si a alzandado el máx nivel o si esta cerca....
    val message = when {
        timeExpired -> "¡Tiempo agotado!"
        clickCount >= maxLevel -> "¡Has alcanzado el nivel máximo!"
        clickCount > maxLevel / 2 -> "¡Estás cerca del nivel máximo!"
        else -> ""
    }

    //Después se crea un efecto para reiniciar el tiempo cada vez que se cambia
    LaunchedEffect(restartCount) {
        timeExpired = false     //se reinicia el estado de la expiración
        timeLeft = 10           //se restable de nuevo el tiempo máximo
        while (timeLeft > 0) {
            kotlinx.coroutines.delay(1000L)     // Se espera 1 segundo para que se inicie el tiempo
            timeLeft--  //Esto reduce el tiempo
        }

        timeExpired = true //Aqui muestro que el tiempo se ha agotado
        clickCount = 0 //Y finalmente aqui se resetea el contador de clics
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val backgroundImage = painterResource(R.drawable.fotofondo) //Variable para mostrar de fondo una imagen
        Image(
            painter = backgroundImage,
            contentDescription = null,
            contentScale = ContentScale.Crop, //Para que se rellene la imagen al area
            modifier = Modifier.fillMaxSize() //Para que ocupe toda la pantalla
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), //Con un Margen de 16dp
            verticalArrangement = Arrangement.Center, //Se muestre en el verticalmente en el centro
            horizontalAlignment = Alignment.CenterHorizontally //y tambien de forma horizonalmente
        ) {
            val topImage = painterResource(R.drawable.fotofondo)

            Text( //El titulo del Juego
                text = "CUAL ES TU NIVEL!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White, //Muestro el texto de color Blanco
                modifier = Modifier.padding(top = 16.dp) // Con un espacio superior
            )

            Text( //El mensaje que muestra de clic
                text = message,
                fontSize = 20.sp, //Con un tamaño de 20
                fontWeight = FontWeight.Medium,
                color = Color.White, //Con un color blanco
                modifier = Modifier.padding(top = 16.dp) // con un espacio alineado desde arriba con 16.dp
            )

            Text( //Texto para los clicks
                text = "Clicks: $clickCount",
                fontSize = 20.sp, //Tamaño 20sp
                fontWeight = FontWeight.Medium,
                color = Color.White, //Color blanco
                modifier = Modifier.padding(top = 16.dp)
            )

            if (!timeExpired) { //Este temporizador se muestra cuando el tiempo no se ha agotado
                Text(
                    text = "Tiempo restante: $timeLeft segundos", //Mensaje
                    fontSize = 20.sp, //De tamaño 20sp
                    fontWeight = FontWeight.Medium,
                    color = Color.Red, //Con un Color Rojo
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Button( //Creo un boton para contar los clics
                onClick = {
                    if (clickCount < maxLevel && !timeExpired) {
                        clickCount++ //Aumenta el contador de clics
                    }
                    onButtonClick() //LLama la funcion al hacer clic
                },
                enabled = !timeExpired, //Se desactivara cuando se agote el tiempo
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("CLICK!!!!") //El texto que tendra el boton
            }

            Button( //Otro boton para reiniciar los clics y el tiempo
                onClick = {
                    clickCount = 0 //Resetea el contador de clics
                    restartCount++ //Y ya aumenta el contador una vez pulsado
                    onButtonClick() //Y despues ya llamamos la funcion
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("REINICIAR") //Texto del Boton
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClickedMouseTheme {
        GreetingImage(){}
    }
}