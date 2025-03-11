package com.example.calcuverbs.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calcuverbs.data.AppDatabase
import com.example.calcuverbs.data.Auxiliary
import com.example.calcuverbs.data.Pronoun
import com.example.calcuverbs.data.Rule


import kotlinx.coroutines.launch

class M2AViewModel(private val database: AppDatabase) : ViewModel() {

    private val _verbs = MutableLiveData<List<String>>() // Ahora almacenamos solo la conjugación específica
    val verbs: LiveData<List<String>> get() = _verbs

    private val _pronouns = MutableLiveData<List<Pronoun>>()
    val pronouns: LiveData<List<Pronoun>> get() = _pronouns

    private val _auxiliaries = MutableLiveData<List<Auxiliary>>() // Cambié Modals por Auxiliaries
    val auxiliaries: LiveData<List<Auxiliary>> get() = _auxiliaries

    private val _rules = MutableLiveData<List<Rule>>()
    val rules: LiveData<List<Rule>> get() = _rules


    init {
        loadInitialData()
    }

    // Carga inicial de datos
    private fun loadInitialData() {
        viewModelScope.launch {
            _pronouns.value = database.pronounDao().getAllPronouns()
            _auxiliaries.value = database.auxiliaryDao().getAllAuxiliaries()
        }
    }

    // Carga verbos del módulo 2 según el tiempo verbal elegido
    fun loadVerbsByTense(tense: String) {
        viewModelScope.launch {
            val filteredVerbs = database.verbDao().getVerbsByModuloAndTense("2", tense)
            _verbs.postValue(filteredVerbs)
        }
    }


    private val _selectedAuxiliary = MutableLiveData<String>()
    val selectedAuxiliary: LiveData<String> get() = _selectedAuxiliary

    fun updateAuxiliary(auxiliary: String, tense: String) {
        viewModelScope.launch {
            val aux = database.auxiliaryDao().getAuxiliaryByBaseForm(auxiliary)
            val conjugatedAuxiliary = when (tense) {
                "Simple Present" -> aux?.baseForm ?: ""
                "Simple Past" -> aux?.pastSimple ?: ""
                "Present Perfect" -> aux?.presentPerfect ?: ""
                "Past Perfect" -> aux?.pastPerfect ?: ""
                else -> aux?.baseForm ?: ""
            }
            _selectedAuxiliary.postValue(conjugatedAuxiliary)
        }
    }




    fun getVerbsGroupedByLetter(tense: String, modulo: String): LiveData<Map<Char, List<String>>> {
        val result = MutableLiveData<Map<Char, List<String>>>()
        viewModelScope.launch {
            val verbs = database.verbDao().getVerbsByModulo(modulo)
            val conjugatedVerbs = verbs.mapNotNull { verb ->
                when (tense) {
                    "Simple Present" -> verb.baseForm.takeIf { it.isNotBlank() }
                    "Simple Past" -> verb.pastSimple.takeIf { it.isNotBlank() }
                    "Present Perfect" -> verb.presentPerfect.takeIf { it.isNotBlank() }
                    "Past Perfect" -> verb.pastPerfect.takeIf { it.isNotBlank() }
                    else -> null
                }
            }
            val grouped = conjugatedVerbs.groupBy { it.first() }
            result.postValue(grouped)
        }
        return result
    }

}
