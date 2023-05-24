package com.example.ozbekcha_inglizchalugat.domain.repo.impls

import com.example.ozbekcha_inglizchalugat.data.local.AppDao
import com.example.ozbekcha_inglizchalugat.data.models.FavouritesModel
import com.example.ozbekcha_inglizchalugat.domain.repo.RoomRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class RoomRepositoryImpl(private val appDao: AppDao) : RoomRepository {
    override suspend fun insertFavouriteWords(fav: FavouritesModel): Long =
        appDao.insertFavLetters(fav)

    override suspend fun deleteFavouriteWord(id: String?) = appDao.deleteFavWord(id)

    override suspend fun getAllFavourites(): Flow<List<FavouritesModel>> =
        appDao.getAllFavWords().flowOn(IO)

    override fun getFavouritesCount(): Flow<Int> = appDao.getFavDataCount()

    override suspend fun deleteFavouriteByLogics(favouriteId: String) {
        val favourite = appDao.getFavWordsName(favouriteId)
        if (favourite != null && favourite.isFavourite) {
            val dictionary = appDao.getWordsById(favourite.id)
            if (dictionary != null) {
                appDao.updateDictionaryById(dictionary.id, false)
            }
        }
        appDao.deleteFavWord(favouriteId)
    }

}