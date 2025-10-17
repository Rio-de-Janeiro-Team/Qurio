package com.example.qurio.presentation.view.game

import com.example.qurio.BR
import com.example.qurio.R
import com.example.qurio.presentation.view.base.BaseAdapter

class GameAdapter(var answers: List<Answer>, val listener: GameFragment) : BaseAdapter<Answer>(items = answers) {
    override val layoutID: Int = R.layout.item_answer

    override fun bind(holder: ItemViewHolder, position: Int) {
        if (answers.isNotEmpty()){
            val currentAnswer = answers[position]
            holder.binding.setVariable(BR.item,currentAnswer)
            holder.binding.setVariable(BR.listener, listener)
        }
    }

    override fun setItems(newItems: List<Answer>) {
        super.setItems(newItems)
        answers = newItems
    }
}

