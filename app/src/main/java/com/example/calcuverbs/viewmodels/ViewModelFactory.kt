package com.example.calcuverbs.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calcuverbs.data.AppDatabase
import com.example.calcuverbs.ui.module1.M1AViewModel

class M1AViewModelFactory(private val database: AppDatabase) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(M1AViewModel::class.java)) {
            return M1AViewModel(database) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class NoteViewModelFactory(
    private val database: AppDatabase,
    private val screenId: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            val noteDao = database.noteDao() // Obtén el NoteDao de AppDatabase
            return NoteViewModel(noteDao, screenId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
