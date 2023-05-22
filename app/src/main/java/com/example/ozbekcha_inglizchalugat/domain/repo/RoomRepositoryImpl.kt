package com.example.ozbekcha_inglizchalugat.domain.repo

import com.example.ozbekcha_inglizchalugat.data.local.AppDao
import com.example.ozbekcha_inglizchalugat.data.models.DictionaryModel
import com.example.ozbekcha_inglizchalugat.data.models.FavouritesModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RoomRepositoryImpl(private val appDao: AppDao) : RoomRepository {
    override suspend fun insertFavouriteWords(fav: FavouritesModel): Long =
        appDao.insertFavLetters(fav)

    override suspend fun deleteFavouriteWord(id: String?) = appDao.deleteFavWord(id)

    override fun getAllFavourites(): Flow<List<FavouritesModel>> =
        appDao.getAllFavWords().flowOn(IO)

    override fun getFavouritesCount(): Flow<Int> = appDao.getFavDataCount()

    override suspend fun clearAll() = appDao.deleteAllFavWords()

}