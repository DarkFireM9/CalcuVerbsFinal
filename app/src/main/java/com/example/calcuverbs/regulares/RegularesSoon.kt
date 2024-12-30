package com.example.calcuverbs.regulares

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calcuverbs.ui.theme.RegularesPrimary
import com.example.calcuverbs.ui.theme.RegularesSecondary

@Composable
fun NotAvailableRegularesScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = RegularesPrimary), // Fondo para Regulares
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "This Module will be available soon",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White, // Texto en blanco para mayor contraste
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = RegularesSecondary, // Fondo del botón
                    contentColor = Color.White // Texto del botón
                )
            ) {
                Text(text = "Return")
            }
        }
    }
}
