package com.example.calcuverbs.regulares

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.calcuverbs.data.Auxiliary
import com.example.calcuverbs.data.Rule
import com.example.calcuverbs.data.Verb
import com.example.calcuverbs.ui.theme.RegularesPrimary
import com.example.calcuverbs.ui.theme.RegularesSecondary
import com.example.calcuverbs.ui.theme.RegularesTertiary

import com.example.calcuverbs.viewmodels.M2AViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun M2AScreen(
    navController: NavController,
    viewModel: M2AViewModel,
    tense: String
) {
    val verbsGrouped by viewModel.getVerbsGroupedByLetter(tense, "2A").observeAsState(emptyMap())

    val verbs by viewModel.verbs.observeAsState(emptyList())
    val pronouns by viewModel.pronouns.observeAsState(emptyList())
    val auxiliaries by viewModel.auxiliaries.observeAsState(emptyList())
    val rules by viewModel.rules.observeAsState(emptyList())


    var selectedAuxiliary by remember { mutableStateOf<String?>(null) }


    var selectedPronoun by remember { mutableStateOf<String?>(null) }

    var selectedVerb by remember { mutableStateOf<String?>(null) }
    var showVerbList by remember { mutableStateOf(false) }

    var affirmativeText by remember { mutableStateOf("") }
    var negativeText by remember { mutableStateOf("") }
    var questionText by remember { mutableStateOf("") }
    var positiveResponseText by remember { mutableStateOf("") }
    var negativeResponseText by remember { mutableStateOf("") }

    var generatedTexts by remember { mutableStateOf<List<String>>(emptyList()) }


    val selectedTense by remember { mutableStateOf(tense ?: "Simple Present") }


    // Llamamos al ViewModel cuando cambia el tense
    LaunchedEffect(tense) {
        viewModel.loadVerbsByTense(tense)

        selectedAuxiliary?.let { aux ->
            viewModel.updateAuxiliary(aux, tense)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Módulo 2.A",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = RegularesTertiary
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    Text(
                        text = "Notas",
                        fontSize = 14.sp, // Tamaño más pequeño que el título
                        color = RegularesTertiary,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("NoteRegular/2")
                            }
                            .padding(horizontal = 16.dp) // Margen opcional
                    )
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
                    .padding(innerPadding)
                    .background(RegularesPrimary)
                    .verticalScroll(rememberScrollState())
            ) {
                // Selector de Pronombres
                M2AHorizontalScrollSelector(
                    items = pronouns.map { it.pronoun },
                    selectedItem = selectedPronoun,
                    onItemSelected = { selectedPronoun = it },
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Selector de Auxiliares
                M2AHorizontalScrollSelector(
                    items = auxiliaries.map { it.baseForm }, // Mostrar auxiliares base
                    selectedItem = selectedAuxiliary,
                    onItemSelected = { aux ->
                        selectedAuxiliary = aux  // Asegurar que el estado local se actualice
                        viewModel.updateAuxiliary(aux, tense) // Se actualiza el auxiliar según el tense
                    }
                )


                Spacer(modifier = Modifier.height(16.dp))

                // Botón para abrir la lista de verbos
                Button(
                    onClick = { showVerbList = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = selectedVerb ?: "Choose your verb",
                        color = RegularesPrimary,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Mostrar botones y generar textos solo si los tres elementos están seleccionados
                if (selectedPronoun != null && selectedAuxiliary != null && selectedVerb != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        // Botones primera línea
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            // Botón afirmativo (✓)
                            Button(
                                onClick = {
                                    affirmativeText = M2AgenerateText(
                                        type = "M2Aaffirmative",
                                        rules = rules,
                                        pronoun = selectedPronoun!!,
                                        tense = selectedTense!!,  // Se pasa el tense seleccionado
                                        verb = selectedVerb!!,
                                        selectedAuxiliary = selectedAuxiliary!!
                                    )
                                },
                                modifier = Modifier.size(60.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White, // Fondo blanco
                                    contentColor = RegularesPrimary
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Affirmative",
                                    tint = RegularesPrimary
                                )
                            }

                            // Botón negativo (✕)
                            Button(
                                onClick = {
                                    affirmativeText = M2AgenerateText(
                                        type = "M2Anegative",
                                        rules = rules,
                                        pronoun = selectedPronoun!!,
                                        tense = selectedTense!!,  // Se pasa el tense seleccionado
                                        verb = selectedVerb!!,
                                        selectedAuxiliary = selectedAuxiliary!!
                                    )
                                },
                                modifier = Modifier.size(60.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White, // Fondo blanco
                                    contentColor = RegularesPrimary
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Negative",
                                    tint = RegularesPrimary
                                )
                            }
                        }

                        // Textos para afirmativo y negativo
                        Text(
                            text = affirmativeText,
                            color = Color.White,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth() // Centrar el texto
                        )
                        Text(
                            text = negativeText,
                            color = Color.White,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth() // Centrar el texto
                        )

                        // Botones para las reglas de preguntas y respuestas
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            // Botón de pregunta (?)
                            Button(
                                onClick = {
                                    affirmativeText = M2AgenerateText(
                                        type = "M2Aquestion",
                                        rules = rules,
                                        pronoun = selectedPronoun!!,
                                        tense = selectedTense!!,  // Se pasa el tense seleccionado
                                        verb = selectedVerb!!,
                                        selectedAuxiliary = selectedAuxiliary!!
                                    )
                                },
                                modifier = Modifier.size(60.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White, // Fondo blanco
                                    contentColor = RegularesPrimary
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.HelpOutline,
                                    contentDescription = "Question",
                                    tint = RegularesPrimary
                                )
                            }

                            // Botón respuesta positiva (✓)
                            Button(
                                onClick = {
                                    affirmativeText = M2AgenerateText(
                                        type = "M2ApositiveResponse",
                                        rules = rules,
                                        pronoun = selectedPronoun!!,
                                        tense = selectedTense!!,  // Se pasa el tense seleccionado
                                        verb = selectedVerb!!,
                                        selectedAuxiliary = selectedAuxiliary!!
                                    )
                                },
                                modifier = Modifier.size(60.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White, // Fondo blanco
                                    contentColor = RegularesPrimary
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Positive Response",
                                    tint = RegularesPrimary
                                )
                            }

                            // Botón respuesta negativa (✕)
                            Button(
                                onClick = {
                                    affirmativeText = M2AgenerateText(
                                        type = "M2AnegativeResponse",
                                        rules = rules,
                                        pronoun = selectedPronoun!!,
                                        tense = selectedTense!!,  // Se pasa el tense seleccionado
                                        verb = selectedVerb!!,
                                        selectedAuxiliary = selectedAuxiliary!!
                                    )
                                },
                                modifier = Modifier.size(60.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White, // Fondo blanco
                                    contentColor = RegularesPrimary
                                )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Negative Response",
                                    tint = RegularesPrimary,
                                    modifier = Modifier.size(50.dp) // Tamaño del ícono
                                )
                            }
                        }

// Textos para las respuestas de pregunta
                        Text(
                            text = questionText,
                            color = Color.White,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth() // Centrar el texto
                        )
                        Text(
                            text = positiveResponseText,
                            color = Color.White,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth() // Centrar el texto
                        )
                        Text(
                            text = negativeResponseText,
                            color = Color.White,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth() // Centrar el texto
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botón Clear
                Button(
                    onClick = {
                        selectedPronoun = null
                        selectedAuxiliary = null
                        selectedVerb = null
                        affirmativeText = ""
                        negativeText = ""
                        questionText = ""
                        positiveResponseText = ""
                        negativeResponseText = ""
                    },
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(vertical = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = RegularesPrimary
                    ),
                    elevation = ButtonDefaults.buttonElevation(4.dp)
                ) {
                    Text(
                        text = "Clear",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }


                // Lista de verbos en pantalla completa
                if (showVerbList) {
                    M2AVerbListScreen(
                        verbsGrouped = verbsGrouped,
                        onVerbSelected = {
                            selectedVerb = it
                            showVerbList = false
                        },
                        onDismiss = { showVerbList = false }
                    )
                }
            }
        })
}

fun M2AgenerateText(
    type: String,
    rules: List<Rule>,
    pronoun: String,
    tense: String,
    verb: String,
    selectedAuxiliary: String // Ahora pasamos el auxiliar seleccionado
): String {
    val rule = rules.find { it.type == type }

    return rule?.structure
        ?.replace("\$pronoun", pronoun)
        ?.replace("\$auxiliary", selectedAuxiliary)  // Usamos el auxiliar seleccionado
        ?.replace("\$verb", verb)
        ?: "Rule not found"
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun M2AVerbListScreen(
    verbsGrouped: Map<Char, List<String>>, // Cambia List<Verb> a List<String>
    onVerbSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                verbsGrouped.forEach { (letter, verbs) ->
                    // Encabezado de la letra
                    stickyHeader {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(RegularesSecondary)
                                .padding(8.dp)
                        ) {
                            Text(
                                text = letter.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = RegularesTertiary
                            )
                        }
                    }

                    // Lista de verbos bajo la letra (ya conjugados)
                    items(verbs) { verb ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable { onVerbSelected(verb) } // Ahora pasa la conjugación
                                .padding(16.dp)
                        ) {
                            Text(
                                text = verb,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun M2AHorizontalScrollSelector(
    items: List<String>,
    selectedItem: String?,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    itemWidth: Dp = 120.dp, // Ancho predeterminado
    itemHeight: Dp = 60.dp, // Altura predeterminada
    selectedColor: Color = RegularesSecondary, // Color de fondo para el elemento seleccionado
    unselectedColor: Color = Color.Transparent, // Color de fondo para los elementos no seleccionados
    selectedTextColor: Color = RegularesTertiary, // Color del texto del elemento seleccionado
    unselectedTextColor: Color = Color.White, // Color del texto de los elementos no seleccionados
    textSize: TextUnit = 20.sp // Tamaño de fuente
) {
    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            val isSelected = item == selectedItem
            Box(
                modifier = Modifier
                    .width(itemWidth)
                    .height(itemHeight)
                    .background(
                        color = if (isSelected) selectedColor else unselectedColor,
                        shape = MaterialTheme.shapes.small
                    )
                    .clickable { onItemSelected(item) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item,
                    color = if (isSelected) selectedTextColor else unselectedTextColor,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    fontSize = textSize
                )
            }
        }
    }
}

