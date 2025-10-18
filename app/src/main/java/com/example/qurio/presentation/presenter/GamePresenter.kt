package com.example.qurio.presentation.presenter

import android.util.Log
import com.example.qurio.base.BasePresenter
import com.example.qurio.domain.entity.Question
import com.example.qurio.domain.repository.GameRepository
import com.example.qurio.presentation.view.game.GameView

class GamePresenter(
    private val gameRepository: GameRepository
) : BasePresenter<GameView>() {

    var gameView: GameView? = null

    fun getQuestions(genreId: Int) {
        tryToCall(
            block = {
                gameRepository.getQuestionsByCategoryId(
                    genreId = genreId,
                    numberOfQuestions = 10
                )
            },
            onStart = { view?.showLoading() },
            onSuccess = ::onQuestionsSuccess,
            onError = ::handleError,
            onEnd = { view?.hideLoading() }
        )
    }

    fun onQuestionsSuccess(questions: List<Question>) {
        Log.i("Questions", "Success: $questions")
        if (questions.isNotEmpty()) {
            gameView?.onGetQuestions(questions = questions)
        } else {
            gameView?.hideLoading()
            gameView?.showError("No questions available")
        }
    }

    fun handleError(throwable: Throwable) {
        gameView?.hideLoading()
        gameView?.showError(throwable.message ?: "Failed to load questions")
    }
}