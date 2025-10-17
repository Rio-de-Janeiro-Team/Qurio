package com.example.qurio.presentation.presenter

import com.example.qurio.base.BasePresenter
import com.example.qurio.domain.entity.Question
import com.example.qurio.domain.repository.GameRepository
import com.example.qurio.presentation.view.game.GameView

class GamePresenter(): BasePresenter<GameView>() {
     var gameView: GameView? = null

    suspend fun getQuestions(){
        val fakeQuestions = listOf(
            Question(
                id = 1,
                question = "what is the capital of egypt",
                correctAnswer = "Cairo",
                incorrectAnswers = listOf("Alex", "fayoum", "giza")
            ),
            Question(
                id = 2,
                question = "who is the president of egypt",
                correctAnswer = "el-sisi",
                incorrectAnswers = listOf("morsi", "mubarak", "nasser")
            ),
        )
//        val questions = gameRepository.getQuestionsByCategoryId(genreId = 12, numberOfQuestions = 10)
        gameView?.onGetQuestions(questions = fakeQuestions)
    }
}