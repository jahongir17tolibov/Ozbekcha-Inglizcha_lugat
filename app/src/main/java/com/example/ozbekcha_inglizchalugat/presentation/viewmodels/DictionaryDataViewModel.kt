package com.example.ozbekcha_inglizchalugat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozbekcha_inglizchalugat.domain.repo.MainRepository
import com.example.ozbekcha_inglizchalugat.domain.resource.DictionaryStateUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DictionaryDataViewModel(private val repo: MainRepository) : ViewModel() {

    private val _dictionaryData = MutableStateFlow<DictionaryStateUI>(DictionaryStateUI.Loading)
    val dictionaryData: StateFlow<DictionaryStateUI> get() = _dictionaryData

    fun loadDictionaryData() = viewModelScope.launch {
        repo.getDictionaryData().collect {
            _dictionaryData.value = it
        }
    }

}