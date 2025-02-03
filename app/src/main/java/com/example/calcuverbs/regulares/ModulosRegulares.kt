package com.example.calcuverbs.regulares

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calcuverbs.navigation.Routes
import com.example.calcuverbs.ui.theme.RegularesPrimary
import com.example.calcuverbs.ui.theme.RegularesSecondary
import com.example.calcuverbs.ui.theme.RegularesTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegularesModulesScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Módulos",
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(RegularesPrimary)
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                ModuleGrid(navController, context)
            }
        }
    )

}

@Composable
fun ModuleGrid(navController: NavController, context: android.content.Context) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Fila superior
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            ModuleButton("M1.A") {navController.navigate(Routes.M1ARegulares)}
            ModuleButton("M2.A") {navController.navigate(Routes.RegularSoon)
            }
        }
//        // Segunda fila
//        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//            ModuleButton("M2.A") {navController.navigate(Routes.RegularSoon)
//            }
//            ModuleButton("M2.B") {navController.navigate(Routes.RegularSoon)
//            }
//        }
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
fun ModuleButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = Modifier.size(100.dp)
    ) {
        Text(
            text = text,
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
