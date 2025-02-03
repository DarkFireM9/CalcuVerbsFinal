package com.example.calcuverbs.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calcuverbs.data.AppDatabase
import com.example.calcuverbs.irregulares.IrregularesAboutUsScreen
import com.example.calcuverbs.irregulares.IrregularesMainScreen
import com.example.calcuverbs.irregulares.IrregularesModulesScreen
import com.example.calcuverbs.irregulares.M1AIrregularesScreen
import com.example.calcuverbs.irregulares.NotAvailableIrregularesScreen
import com.example.calcuverbs.irregulares.NoteIrregularScreen
import com.example.calcuverbs.regulares.M1ARegularesScreen
import com.example.calcuverbs.regulares.NotAvailableRegularesScreen
import com.example.calcuverbs.regulares.NoteRegularScreen
import com.example.calcuverbs.regulares.RegularesAboutUsScreen
import com.example.calcuverbs.regulares.RegularesMainScreen
import com.example.calcuverbs.regulares.RegularesModulesScreen
import com.example.calcuverbs.ui.module1.M1AViewModel
import com.example.calcuverbs.viewmodels.M1AViewModelFactory
import com.example.calcuverbs.viewmodels.NoteViewModel
import com.example.calcuverbs.viewmodels.NoteViewModelFactory

@Composable
fun AppNavigation(database: AppDatabase) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.MainRegulares) {
        composable(Routes.MainRegulares) {
            RegularesMainScreen(navController)
        }
        composable(Routes.MainIrregulares) {
            IrregularesMainScreen(navController)
        }
        composable(Routes.ModulosRegulares) {
            RegularesModulesScreen(navController)
        }
        composable(Routes.ModulosIrregulares) {
            IrregularesModulesScreen(navController)
        }
        composable(Routes.AboutUsRegulares) {
            RegularesAboutUsScreen(navController)
        }
        composable(Routes.AboutUsIrregulares) {
            IrregularesAboutUsScreen(navController)
        }

        composable(Routes.M1ARegulares) {
            val factory = M1AViewModelFactory(database)
            val viewModel: M1AViewModel = viewModel(factory = factory)

            M1ARegularesScreen(navController = navController, viewModel = viewModel, isRegular = true, modulo = "1A")
        }

        composable(Routes.M1AIrregulares) {
            val factory = M1AViewModelFactory(database)
            val viewModel: M1AViewModel = viewModel(factory = factory)

            M1AIrregularesScreen(navController = navController, viewModel = viewModel, isRegular = false, modulo = "1A")
        }

        composable(
            route = "NoteRegular/{noteIndex}",
            arguments = listOf(
                navArgument("noteIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val noteIndex = backStackEntry.arguments?.getInt("noteIndex") ?: 1

            val factory = NoteViewModelFactory(database, "note_regular_$noteIndex")
            val viewModel: NoteViewModel = viewModel(factory = factory)

            NoteRegularScreen(navController = navController, viewModel = viewModel, noteIndex = noteIndex)
        }

        composable(
            route = "NoteIrregular/{noteIndex}",
            arguments = listOf(
                navArgument("noteIndex") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val noteIndex = backStackEntry.arguments?.getInt("noteIndex") ?: 1

            val factory = NoteViewModelFactory(database, "note_irregular_$noteIndex")
            val viewModel: NoteViewModel = viewModel(factory = factory)

            NoteIrregularScreen(navController = navController, viewModel = viewModel, noteIndex = noteIndex)
        }

        composable(Routes.RegularSoon) {
            NotAvailableRegularesScreen(navController)
        }

        composable(Routes.IrregularSoon) {
            NotAvailableIrregularesScreen(navController)
        }
    }
}