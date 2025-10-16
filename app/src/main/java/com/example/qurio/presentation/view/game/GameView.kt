package com.example.qurio.presentation.view.game

import com.example.qurio.base.BaseView
import com.example.qurio.domain.entity.Question

interface GameView: BaseView {
    fun onGetQuestions(questions: List<Question>)
}