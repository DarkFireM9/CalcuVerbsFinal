package com.example.calcuverbs.regulares

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calcuverbs.navigation.Routes
import com.example.calcuverbs.ui.theme.RegularesPrimary
import com.example.calcuverbs.ui.theme.RegularesSecondary
import com.example.calcuverbs.ui.theme.RegularesTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun M2ASwitchScreen(navController: NavController) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Modulo 2.A",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = RegularesTertiary
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
                    containerColor = RegularesSecondary,
                    titleContentColor = RegularesTertiary,
                    navigationIconContentColor = RegularesTertiary
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RegularesPrimary) // Fondo de color
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()), // Padding adicional para margen

                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Descripción
                Text(
                    text = "Algunos verbos irregulares en inglés mantienen la misma forma en los tres tiempos: presente, pasado y pasado participio. Ejemplos de estos son 'cut' (cortar), 'set' (poner) y 'cost' (costar), los cuales no cambian en ninguna conjugación. En este segundo capítulo, exploraremos la regla de la 's' o 'es' en los verbos cuando se usan con los pronombres 'he' (él), 'she' (ella) e 'it' (esto).",
                    textAlign = TextAlign.Justify,
                    fontSize = 25.sp,
                    lineHeight = 25.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White, // Color del texto
                    modifier = Modifier.fillMaxWidth()
                )

// Agrega espacio entre el texto y el M2ASwitchGrid
                Spacer(modifier = Modifier.height(16.dp))

                M2ASwitchGrid(navController, context)

            }
        }
    )

}

@Composable
fun M2ASwitchGrid(navController: NavController, context: android.content.Context) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Fila superior
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            M2ASwitchButton("Simple present ") {navController.navigate("M2A/Simple Present")}
            M2ASwitchButton("Simple Past") {navController.navigate("M2A/Simple Past")
            }
        }
        // Segunda fila
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            M2ASwitchButton("Present perfect") {navController.navigate("M2A/Present Perfect")
            }
            M2ASwitchButton("Past perfect") {navController.navigate("M2A/Past Perfect")
            }
        }
//        // Tercera fila
//        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//            ModuleButton("M3.A") {navController.navigate(Routes.RegularSoon)
//            }
//            ModuleButton("M3.B") {navController.navigate(Routes.RegularSoon)
//            }
//        }
//        // Fila inferior
//        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//            ModuleButton("M4.A") {navController.navigate(Routes.RegularSoon)
//            }
//            ModuleButton("M4.B") {navController.navigate(Routes.RegularSoon)
//            }
//        }
    }
}

@Composable
fun M2ASwitchButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier
            .width(200.dp)
            .height(60.dp)
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}
