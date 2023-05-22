package com.example.ozbekcha_inglizchalugat.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozbekcha_inglizchalugat.data.models.DictionaryModel
import com.example.ozbekcha_inglizchalugat.data.models.FavouritesModel
import com.example.ozbekcha_inglizchalugat.domain.repo.MainRepository
import com.example.ozbekcha_inglizchalugat.domain.repo.RoomRepository
import com.example.ozbekcha_inglizchalugat.domain.resource.DictionaryStateUI
import com.example.ozbekcha_inglizchalugat.utils.Constants.LOG_TXT
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DictionaryDataViewModel(
    private val repo: MainRepository,
    private val roomRepo: RoomRepository
) : ViewModel() {

    private val _dictionaryData = MutableStateFlow<DictionaryStateUI>(DictionaryStateUI.Loading)
    val dictionaryData: StateFlow<DictionaryStateUI> get() = _dictionaryData

    fun loadDictionaryData() = viewModelScope.launch {
        repo.getDictionaryData().collect {
            _dictionaryData.value = it
        }
    }

    fun toggleFavourite(dictionary: DictionaryModel) = viewModelScope.launch {
        if (dictionary.isFavourite) {
            dictionary.id.let {
                roomRepo.deleteFavouriteWord(it)
                Log.d(LOG_TXT, "toggleFavourite: $it")
            }
        } else {
            val favModel = FavouritesModel(
                dictionary.id,
                dictionary.english,
                dictionary.transcript,
                dictionary.uzbek,
                isFavourite = true
            )
            roomRepo.insertFavouriteWords(favModel)
            Log.d(LOG_TXT, favModel.toString())
        }
        dictionary.checkFavourite()
        repo.updateDictionary(dictionary)
    }

}