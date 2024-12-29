package com.example.calcuverbs.regulares

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calcuverbs.R
import com.example.calcuverbs.navigation.Routes
import com.example.calcuverbs.ui.theme.RegularesPrimary
import com.example.calcuverbs.ui.theme.RegularesTertiary


@Composable
fun RegularesMainScreen( navController: NavController)
{
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = RegularesPrimary
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo y título
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.appicon),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(150.dp)
                        .padding(8.dp)
                        .clickable {
                            navController.navigate(Routes.AboutUsRegulares)
                        },
                    contentScale = ContentScale.Crop

                )
                Text(
                    text = "Regulares",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = RegularesTertiary // Contraste con el color primario
                )
            }

            Spacer(modifier = Modifier.height(54.dp)) // Espacio entre título y botones superiores

            // Botones superiores (Módulos y Recursos)
            Row(
                horizontalArrangement = Arrangement.spacedBy(52.dp), // Espacio entre botones
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {navController.navigate(Routes.ModulosRegulares)},
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
                ) {
                    Text(text = "Módulos", color = RegularesPrimary)
                }
                Button(
                    onClick = {  },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
                ) {
                    Text(text = "Recursos", color = RegularesPrimary)
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón de "Versión Premium" hacia el centro

            // Botón Premium centrado
            Button(
                onClick = {  },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(text = "Adquiere la Versión Premium", color = RegularesPrimary)
            }

            Spacer(modifier = Modifier.weight(1f)) // Empuja el botón de "Cambiar a Irregulares" hacia abajo

            // Botón Cambiar a Irregulares
            Button(
                onClick = { navController.navigate(Routes.MainIrregulares) },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(text = "Cambiar a Irregulares", color = RegularesPrimary)
            }

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewRegularesScreen() {
        RegularesMainScreen(navController = rememberNavController()
        )
    }

