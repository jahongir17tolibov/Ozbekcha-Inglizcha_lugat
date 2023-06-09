package com.example.ozbekcha_inglizchalugat.domain.repo

import com.example.ozbekcha_inglizchalugat.data.models.FavouritesModel
import kotlinx.coroutines.flow.Flow

interface RoomRepository {

    suspend fun insertFavouriteWords(fav: FavouritesModel): Long

    suspend fun deleteFavouriteWord(id: String?)

    suspend fun getAllFavourites(): Flow<List<FavouritesModel>>

    fun getFavouritesCount(): Flow<Int>

    suspend fun deleteFavouriteByLogics(favouriteId: String)

}