package com.example.calcuverbs.ui.module1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calcuverbs.data.*
import kotlinx.coroutines.launch

class M1AViewModel(private val database: AppDatabase) : ViewModel() {

    private val _verbs = MutableLiveData<List<Verb>>()
    val verbs: LiveData<List<Verb>> get() = _verbs

    private val _modals = MutableLiveData<List<Modal>>()
    val modals: LiveData<List<Modal>> get() = _modals

    private val _pronouns = MutableLiveData<List<Pronoun>>()
    val pronouns: LiveData<List<Pronoun>> get() = _pronouns

    private val _rules = MutableLiveData<List<Rule>>()
    val rules: LiveData<List<Rule>> get() = _rules

    init {
        loadInitialData()
    }

    // Carga inicial de datos (sin filtro)
    private fun loadInitialData() {
        viewModelScope.launch {
            _verbs.value = database.verbDao().getAllVerbs()
            _modals.value = database.modalDao().getAllModals()
            _pronouns.value = database.pronounDao().getAllPronouns()
            _rules.value = database.ruleDao().getAllRules()
        }
    }

    // Carga verbos según la regularidad (true = regulares, false = irregulares)
    fun loadVerbs(isRegular: Boolean) {
        viewModelScope.launch {
            val filteredVerbs = database.verbDao().getVerbsByRegularity(isRegular)
            _verbs.postValue(filteredVerbs)
        }
    }

    fun getVerbsGroupedByLetter(isRegular: Boolean): LiveData<Map<Char, List<Verb>>> {
        val result = MutableLiveData<Map<Char, List<Verb>>>()
        viewModelScope.launch {
            val verbs = database.verbDao().getVerbsByRegularity(isRegular)
            val grouped = verbs.groupBy { it.baseForm.first() }
            result.postValue(grouped)
        }
        return result
    }
}
