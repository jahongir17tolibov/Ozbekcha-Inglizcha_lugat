package com.example.ozbekcha_inglizchalugat.domain.resource

import com.example.ozbekcha_inglizchalugat.data.models.DictionaryModel

sealed class DictionaryStateUI {

    object Loading : DictionaryStateUI()
    data class Success(val dictionaryData: List<DictionaryModel>) : DictionaryStateUI()
    data class Error(val errorMessage: String) : DictionaryStateUI()

}
