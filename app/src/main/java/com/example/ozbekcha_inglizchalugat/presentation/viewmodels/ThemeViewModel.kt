package com.example.ozbekcha_inglizchalugat.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ozbekcha_inglizchalugat.data.prefs.DataStoreManager
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ThemeViewModel(private val dataStore: DataStoreManager) : ViewModel() {

    val getTheme = dataStore.getTheme().asLiveData(IO)

    fun setTheme(isDark: Boolean) = viewModelScope.launch {
        dataStore.setTheme(isDark)
    }

}