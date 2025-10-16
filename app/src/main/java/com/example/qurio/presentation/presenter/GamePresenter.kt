package com.example.qurio.presentation.presenter

import com.example.qurio.base.BasePresenter
import com.example.qurio.domain.repository.GameRepository
import com.example.qurio.presentation.view.game.GameView

class GamePresenter(val gameRepository: GameRepository): BasePresenter<GameView>() {
     var gameView: GameView? = null

    suspend fun getQuestions(){
        val questions = gameRepository.getQuestionsByCategoryId(genreId = 12, numberOfQuestions = 10)
        gameView?.onGetQuestions(questions = questions)
    }
}