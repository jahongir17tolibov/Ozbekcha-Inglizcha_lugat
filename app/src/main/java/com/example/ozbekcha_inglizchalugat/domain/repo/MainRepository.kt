package com.example.ozbekcha_inglizchalugat.domain.repo

import com.example.ozbekcha_inglizchalugat.data.models.DictionaryModel
import com.example.ozbekcha_inglizchalugat.domain.resource.DictionaryStateUI
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getDictionaryData(): Flow<DictionaryStateUI>

    suspend fun updateDictionary(words: DictionaryModel)

}