package com.example.calcuverbs.regulares

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
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
import com.example.calcuverbs.data.Rule
import com.example.calcuverbs.data.Verb
import com.example.calcuverbs.ui.module1.M1AViewModel
import com.example.calcuverbs.ui.theme.RegularesPrimary
import com.example.calcuverbs.ui.theme.RegularesSecondary
import com.example.calcuverbs.ui.theme.RegularesTertiary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun M1ARegularesScreen(
    navController: NavController,
    viewModel: M1AViewModel,
    isRegular: Boolean // Parámetro para determinar si se cargan verbos regulares o irregulares
) {
    // Observa la lista de verbos desde el ViewModel
    val verbs by viewModel.verbs.observeAsState(emptyList())
    val verbBaseForms = verbs.map { it.baseForm }

    val pronouns by viewModel.pronouns.observeAsState(emptyList())
    val modals by viewModel.modals.observeAsState(emptyList())
    val rules by viewModel.rules.observeAsState(emptyList())

    var selectedPronoun by remember { mutableStateOf<String?>(null) }
    var selectedModal by remember { mutableStateOf<String?>(null) }
    var selectedVerb by remember { mutableStateOf<String?>(null) }
    var showVerbList by remember { mutableStateOf(false) }

    var affirmativeText by remember { mutableStateOf("") }
    var negativeText by remember { mutableStateOf("") }
    var questionText by remember { mutableStateOf("") }
    var positiveResponseText by remember { mutableStateOf("") }
    var negativeResponseText by remember { mutableStateOf("") }

    var generatedTexts by remember { mutableStateOf<List<String>>(emptyList()) }


    val verbsGrouped by viewModel.getVerbsGroupedByLetter(isRegular).observeAsState(initial = emptyMap())


    // Cargar los verbos según la regularidad al iniciar la pantalla
    LaunchedEffect(isRegular) {
        viewModel.loadVerbs(isRegular)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Módulo 1",
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
                                navController.navigate("NoteRegular/1")
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
                HorizontalScrollSelector(
                    items = pronouns.map { it.pronoun },
                    selectedItem = selectedPronoun,
                    onItemSelected = { selectedPronoun = it },
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Selector de Modales
                HorizontalScrollSelector(
                    items = modals.map { it.modal },
                    selectedItem = selectedModal,
                    onItemSelected = { selectedModal = it },
                    modifier = Modifier.padding(vertical = 16.dp)
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
                if (selectedPronoun != null && selectedModal != null && selectedVerb != null) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {

                        //Botones primera linea
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
                                    affirmativeText = generateText(
                                        type = "affirmative",
                                        rules = rules,
                                        pronoun = selectedPronoun!!,
                                        modal = selectedModal!!,
                                        verb = selectedVerb!!
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
                                    negativeText = generateText(
                                        type = "negative",
                                        rules = rules,
                                        pronoun = selectedPronoun!!,
                                        modal = selectedModal!!,
                                        verb = selectedVerb!!
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
                                    questionText = generateText(
                                        type = "question",
                                        rules = rules,
                                        pronoun = selectedPronoun!!,
                                        modal = selectedModal!!,
                                        verb = selectedVerb!!
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
                                    positiveResponseText = generateText(
                                        type = "positiveResponse",
                                        rules = rules,
                                        pronoun = selectedPronoun!!,
                                        modal = selectedModal!!,
                                        verb = selectedVerb!!
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
                                    negativeResponseText = generateText(
                                        type = "negativeResponse",
                                        rules = rules,
                                        pronoun = selectedPronoun!!,
                                        modal = selectedModal!!,
                                        verb = selectedVerb!!
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
                        selectedModal = null
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
                        VerbListScreen(
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



fun generateText(type: String, rules: List<Rule>, pronoun: String, modal: String, verb: String): String {
    val rule = rules.find { it.type == type }
    return rule?.structure
        ?.replace("\$pronoun", pronoun)
        ?.replace("\$modal", modal)
        ?.replace("\$verb", verb)
        ?: "Rule not found"
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerbListScreen(
    verbsGrouped: Map<Char, List<Verb>>,
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

                    // Lista de verbos bajo la letra
                    items(verbs) { verb ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable { onVerbSelected(verb.baseForm) }
                                .padding(16.dp)
                        ) {
                            Text(
                                text = verb.baseForm,
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
fun HorizontalScrollSelector(
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
