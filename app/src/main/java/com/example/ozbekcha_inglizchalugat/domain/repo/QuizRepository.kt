package com.example.ozbekcha_inglizchalugat.domain.repo

import com.example.ozbekcha_inglizchalugat.data.models.QuizModel
import kotlinx.coroutines.flow.Flow

interface QuizRepository {

    suspend fun getQuizQuestion(): Flow<List<QuizModel>>

    suspend fun generateQuizQuestions(): List<QuizModel>

    suspend fun getNextQuizQuestion(): QuizModel?

}