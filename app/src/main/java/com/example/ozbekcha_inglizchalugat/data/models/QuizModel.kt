package com.example.ozbekcha_inglizchalugat.data.models

data class QuizModel(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)