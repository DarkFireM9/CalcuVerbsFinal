package com.example.calcuverbs.irregulares

import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calcuverbs.R
import com.example.calcuverbs.ui.theme.IrregularesPrimary
import com.example.calcuverbs.ui.theme.IrregularesSecondary
import com.example.calcuverbs.ui.theme.IrregularesTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IrregularesAboutUsScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "CalcuVerbs",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = IrregularesTertiary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = IrregularesSecondary,
                    titleContentColor = IrregularesTertiary,
                    navigationIconContentColor = IrregularesTertiary
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(IrregularesPrimary) // Fondo de color
                    .padding(innerPadding)
                    .padding(16.dp), // Padding adicional para margen
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Descripción
                Text(
                    text = "Calcuverbs es una herramienta educativa creada por el Ing. Javier Magallanes Martínez, basada en su experiencia aprendiendo inglés. Tras enfrentar dificultades en el idioma, descubrió que diferenciar el verbo To Be de los auxiliares (Do, Does, Did) es clave para dominarlo.\n\n" +
                            "Así nació la metodología de las \"Calculadoras de Verbos\", que simplifica la conjugación y el uso correcto de los verbos en inglés, haciendo el aprendizaje claro, accesible y práctico para todos.",
                    textAlign = TextAlign.Justify,
                    fontSize = 25.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White, // Color del texto
                    modifier = Modifier.fillMaxWidth()
                )

                // Imagen
                Image(
                    painter = painterResource(id = R.drawable.ing), // Reemplaza con tu imagen
                    contentDescription = "Ing. Javier Magallanes Martínez",
                    modifier = Modifier
                        .fillMaxWidth(0.6f) // Control del tamaño de la imagen
                        .aspectRatio(1f) // Relación de aspecto cuadrada
                )
            }
        }
    )
}


@Preview
@Composable
fun Preview(){
    IrregularesAboutUsScreen(navController = rememberNavController())
}