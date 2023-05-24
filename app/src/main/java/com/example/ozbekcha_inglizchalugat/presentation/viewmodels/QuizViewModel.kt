package com.example.ozbekcha_inglizchalugat.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ozbekcha_inglizchalugat.data.models.QuizModel
import com.example.ozbekcha_inglizchalugat.domain.repo.QuizRepository
import kotlinx.coroutines.launch

class QuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {

    private val _quizQuestions = MutableLiveData<List<QuizModel>>()
    val quizQuestions: LiveData<List<QuizModel>> get() = _quizQuestions

    private val _currentQuestionIndex = MutableLiveData<Int>()
    val currentQuestionIndex: LiveData<Int> get() = _currentQuestionIndex

    private val _correctAnswersCount = MutableLiveData<Int>()
    val correctAnswersCount: LiveData<Int> get() = _correctAnswersCount

    private val _incorrectAnswersCount = MutableLiveData<Int>()
    val incorrectAnswersCount: LiveData<Int> get() = _incorrectAnswersCount


    fun startQuiz() = viewModelScope.launch {
        quizRepository.getQuizQuestion().collect {
            _quizQuestions.value = it
        }
        _currentQuestionIndex.value = 0
        _correctAnswersCount.value = 0
        _incorrectAnswersCount.value = 0
    }

    fun nextQuestion() = viewModelScope.launch {
        val currentQuestionIndex = _currentQuestionIndex.value ?: return@launch
        val quizQuestions = _quizQuestions.value ?: return@launch
        val nextQuestionIndex = currentQuestionIndex + 1
        if (nextQuestionIndex < quizQuestions.size) {
            _currentQuestionIndex.value = nextQuestionIndex
        }
    }

    fun onAnswerSelected(answer: String) = viewModelScope.launch {
        val currentQuestionIndex = _currentQuestionIndex.value ?: return@launch
        val quizQuestions = _quizQuestions.value ?: return@launch
        val currentQuestion = quizQuestions.getOrNull(currentQuestionIndex) ?: return@launch

        val isCorrect = answer == currentQuestion.correctAnswer
        if (isCorrect) {
            _correctAnswersCount.value = (_correctAnswersCount.value ?: 0) + 1
        } else {
            _incorrectAnswersCount.value = (_incorrectAnswersCount.value ?: 0) + 1
        }
    }

}