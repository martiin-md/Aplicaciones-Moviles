package com.example.figma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.figma.ui.theme.FigmaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FigmaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Frame1()
                }
            }
        }
    }
}


@Composable
fun Frame1(modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xfffc1c1c),
        border = BorderStroke(1.dp, Color.Black),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .requiredWidth(width = 411.dp)
                .requiredHeight(height = 731.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .requiredWidth(width = 411.dp)
                    .requiredHeight(height = 731.dp)
                    .padding(horizontal = 68.dp,
                        vertical = 158.dp)
            ) {
                Button(
                    onClick = { },
                    shape = RoundedCornerShape(30.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xff0fe77f)),
                    modifier = Modifier
                        .requiredWidth(width = 215.dp)
                        .requiredHeight(height = 97.dp)){ }
            }
            Text(
                text = "MARTIN",
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 35.dp,
                        y = 90.dp)
                    .requiredWidth(width = 341.dp))
            Image(
                painter = painterResource(id = R.drawable.pachapaint),
                contentDescription = "pacha-paint 2",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
                    .offset(x = 57.dp,
                        y = 201.dp)
                    .requiredWidth(width = 297.dp)
                    .requiredHeight(height = 227.dp))
            Image(
                painter = painterResource(id = R.drawable.logoupspeed),
                contentDescription = "4nwbsipg 1",
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 6.dp,
                        y = 159.dp)
                    .requiredWidth(width = 400.dp)
                    .requiredHeight(height = 100.dp))
            Text(
                text = "CLICK\n",
                color = Color.White,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier
                    .align(alignment = Alignment.TopStart)
                    .offset(x = 120.dp,
                        y = 342.dp)
                    .requiredWidth(width = 171.dp)
                    .requiredHeight(height = 46.dp))
        }
    }
}

@Preview(widthDp = 411, heightDp = 731)
@Composable
private fun Frame1Preview() {
    Frame1(Modifier)
}

