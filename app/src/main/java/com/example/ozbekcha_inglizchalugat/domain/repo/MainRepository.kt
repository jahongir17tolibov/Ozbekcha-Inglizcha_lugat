package com.example.ozbekcha_inglizchalugat.domain.repo

import com.example.ozbekcha_inglizchalugat.domain.resource.DictionaryStateUI
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun getDictionaryData(): Flow<DictionaryStateUI>

}