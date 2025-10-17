package com.example.qurio.presentation.view.game

import android.view.View

interface GameInterActionListener {
    fun onClickGetNextQuestion()
    fun onClickOnAnswer(answer: Answer,view: View)
}