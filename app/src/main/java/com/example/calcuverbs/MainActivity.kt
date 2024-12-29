package com.example.calcuverbs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.calcuverbs.data.AppDatabase
import com.example.calcuverbs.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    private val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            AppNavigation(database = database)
        }
    }
}
