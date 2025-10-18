package com.example.qurio.presentation.view.game

import com.example.qurio.base.BaseView
import com.example.qurio.domain.entity.Question

interface GameView: BaseView {
    fun onGetQuestions(questions: List<Question>)
    fun showQuestion(question: Question, questionNumber: String)
    fun resetAnswers()
    fun updateTimer(secondsLeft: Long, progress: Float)
    fun onTimerFinished()
    fun showEndOfQuestions()
    fun showError(error: Throwable)
    fun toggleSkipButton(visible: Boolean)
}