package com.example.ozbekcha_inglizchalugat.domain.repo

import com.example.ozbekcha_inglizchalugat.data.local.AppDao
import com.example.ozbekcha_inglizchalugat.data.source.LocalDataSource
import com.example.ozbekcha_inglizchalugat.domain.resource.DictionaryStateUI
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MainRepositoryImpl(private val local: LocalDataSource, private val appDao: AppDao) :
    MainRepository {

    override suspend fun getDictionaryData(): Flow<DictionaryStateUI> = flow {
        emit(DictionaryStateUI.Loading)
        try {
            local.getItemsFromJson().let { appDao.insertWords(it) }
            val cachingData = appDao.getAllWords()
            emit(DictionaryStateUI.Success(cachingData))
        } catch (e: Exception) {
            emit(DictionaryStateUI.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }.catch {
        emit(DictionaryStateUI.Error(it.localizedMessage ?: "Unknown Error"))
    }.flowOn(IO)

}