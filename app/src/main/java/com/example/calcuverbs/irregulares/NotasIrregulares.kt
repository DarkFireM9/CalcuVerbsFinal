package com.example.calcuverbs.irregulares
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calcuverbs.ui.theme.IrregularesPrimary
import com.example.calcuverbs.ui.theme.IrregularesSecondary
import com.example.calcuverbs.ui.theme.IrregularesTertiary
import com.example.calcuverbs.ui.theme.RegularesPrimary
import com.example.calcuverbs.ui.theme.RegularesSecondary
import com.example.calcuverbs.ui.theme.RegularesTertiary
import com.example.calcuverbs.viewmodels.NoteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteIrregularScreen(
    viewModel: NoteViewModel,
    navController: NavController,
    noteIndex: Int // Identificador de la nota irregular
) {
    val noteContent = viewModel.noteContent.value // Obtenemos la nota desde el ViewModel

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Nota Irregular $noteIndex",
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
                    .background(IrregularesPrimary)
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Cuadro de texto para la nota
                OutlinedTextField(
                    value = noteContent, // Solo usamos el valor directamente
                    onValueChange = { newValue ->
                        viewModel.saveNote(newValue) // Guardar cambios automáticamente
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(600.dp),
                    label = { Text(text = "Escribe tu nota aquí") },
                    textStyle = TextStyle(
                        fontSize = 18.sp, // Tamaño de letra aumentado
                        color = Color.Black // Color del texto
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color.White, // Fondo blanco
                        focusedBorderColor = IrregularesSecondary, // Color del borde enfocado
                        unfocusedBorderColor = Color.Gray, // Color del borde desenfocado
                        cursorColor = IrregularesSecondary, // Color del cursor
                        focusedLabelColor = IrregularesTertiary, // Color del label enfocado
                        unfocusedLabelColor = IrregularesTertiary // Color del label desenfocado
                    )
                )
            }
        }
    )
}
