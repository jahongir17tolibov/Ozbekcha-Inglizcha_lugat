package com.example.ozbekcha_inglizchalugat.domain.repo.impls

import com.example.ozbekcha_inglizchalugat.data.local.AppDao
import com.example.ozbekcha_inglizchalugat.data.models.QuizModel
import com.example.ozbekcha_inglizchalugat.domain.repo.QuizRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class QuizRepositoryImpl(private val appDao: AppDao) : QuizRepository {

    private var quizQuestionsList: List<QuizModel> = emptyList()
    private var currentQuestionIndex: Int = 0

    override suspend fun getQuizQuestion(): Flow<List<QuizModel>> = flow {
        if (quizQuestionsList.isEmpty()) {
            quizQuestionsList = generateQuizQuestions()
            currentQuestionIndex = 0
        }
        emit(quizQuestionsList)
    }.flowOn(IO)

    override suspend fun generateQuizQuestions(): List<QuizModel> {
        val favouritesList = appDao.getFavouritesForQuiz()/* favourite wordslarni roomda olamiz */
        // favourite wordsni aralashtirib tashaymiz
        val shuffledFavouritesList = favouritesList.shuffled()
        val quizQuestions = mutableListOf<QuizModel>()

        // aralashtirilgan favourites listni 5 tadan ko'p bo'lsa quizni tuzamiz
        for (i in 0 until minOf(5, shuffledFavouritesList.size)) {
            val dictionaryItem = shuffledFavouritesList[i]

            val question = dictionaryItem.english
            val correctAnswer = dictionaryItem.uzbek

            //noto'g'ri variantlar va boshqa funksiyalar
            val otherOptions = shuffledFavouritesList
                .filterNot { it == dictionaryItem }
                .shuffled()
                .take(4)
                .map { it.uzbek }

            //variantlarni birlashtiramiz
            val options = (otherOptions + correctAnswer).shuffled()

            val quizQuestionModel = QuizModel(question, options, correctAnswer)
            quizQuestions.add(quizQuestionModel)
        }
        return quizQuestions
    }

    override suspend fun getNextQuizQuestion(): QuizModel? {
        currentQuestionIndex++
        return if (currentQuestionIndex < quizQuestionsList.size) {
            quizQuestionsList[currentQuestionIndex]
        } else {
            null
        }
    }

}