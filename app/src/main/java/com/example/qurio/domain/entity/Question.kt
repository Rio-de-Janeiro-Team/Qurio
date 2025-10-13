package com.example.qurio.domain.entity

data class Question(
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
)
