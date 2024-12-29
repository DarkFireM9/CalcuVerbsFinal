package com.example.calcuverbs.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calcuverbs.data.AppDatabase
import com.example.calcuverbs.irregulares.IrregularesAboutUsScreen
import com.example.calcuverbs.irregulares.IrregularesMainScreen
import com.example.calcuverbs.irregulares.IrregularesModulesScreen
import com.example.calcuverbs.regulares.M1ARegularesScreen
import com.example.calcuverbs.regulares.RegularesAboutUsScreen
import com.example.calcuverbs.regulares.RegularesMainScreen
import com.example.calcuverbs.regulares.RegularesModulesScreen
import com.example.calcuverbs.ui.module1.M1AViewModel
import com.example.calcuverbs.viewmodels.M1AViewModelFactory

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

        // Pantalla de verbos regulares
        composable(Routes.M1ARegulares) {
            val factory = M1AViewModelFactory(database)
            val viewModel: M1AViewModel = viewModel(factory = factory)

            // Pasamos `isRegular = true` para esta pantalla
            M1ARegularesScreen(navController = navController, viewModel = viewModel, isRegular = true)
        }

        // Pantalla de verbos irregulares
        //composable(Routes.M1AIrregulares) {
          //  val factory = M1AViewModelFactory(database)
            //val viewModel: M1AViewModel = viewModel(factory = factory)

            // Pasamos `isRegular = false` para esta pantalla
            //M1ARegularesScreen(navController = navController, viewModel = viewModel, isRegular = false)
 //       }
    }
}