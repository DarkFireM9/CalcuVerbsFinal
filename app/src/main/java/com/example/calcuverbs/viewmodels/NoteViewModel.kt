package com.example.calcuverbs.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calcuverbs.data.AppDatabase
import com.example.calcuverbs.data.Note
import com.example.calcuverbs.data.NoteDao
import kotlinx.coroutines.launch

class NoteViewModel(private val noteDao: NoteDao, private val screenId: String) : ViewModel() {
    var noteContent = mutableStateOf("")
        private set

    init {
        loadNote()
    }

    private fun loadNote() {
        viewModelScope.launch {
            val note = noteDao.getNoteForScreen(screenId)
            noteContent.value = note?.content ?: ""
        }
    }

    fun saveNote(content: String) {
        noteContent.value = content
        viewModelScope.launch {
            noteDao.clearNotesForScreen(screenId)
            noteDao.insertNote(Note(screenId = screenId, content = content))
        }
    }
}

