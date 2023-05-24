package com.example.ozbekcha_inglizchalugat.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ozbekcha_inglizchalugat.data.models.DictionaryModel
import com.example.ozbekcha_inglizchalugat.data.models.FavouritesModel
import com.example.ozbekcha_inglizchalugat.domain.repo.MainRepository
import com.example.ozbekcha_inglizchalugat.domain.repo.RoomRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val roomRepo: RoomRepository,
    private val dictionaryRepo: MainRepository
) : ViewModel() {

    private val _favouriteWords = MutableStateFlow<List<FavouritesModel>>(emptyList())
    val favouriteWords: StateFlow<List<FavouritesModel>> get() = _favouriteWords

    fun getAllFavourites() = viewModelScope.launch {
        roomRepo.getAllFavourites().collect {
            _favouriteWords.value = it
        }
    }

    val countedData: LiveData<Int> = roomRepo.getFavouritesCount().asLiveData(IO)

    fun deleteFavWord(id: String) = viewModelScope.launch(IO) {
        roomRepo.deleteFavouriteByLogics(id)
    }

}