package com.example.qurio.presentation.view.game

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.qurio.R
import com.example.qurio.databinding.ItemAnswerBinding

class GameAdapter(
    private var answers: List<Answer>,
    private val listener: GameInterActionListener
) : RecyclerView.Adapter<GameAdapter.AnswerViewHolder>() {

    private var isClickable = true
    private var selectedViewId: Int? = null
    private var areOthersDisabled = false

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(newAnswers: List<Answer>) {
        answers = newAnswers
        selectedViewId = null
        areOthersDisabled = false
        notifyDataSetChanged()
    }

    fun setClickable(clickable: Boolean) {
        isClickable = clickable
    }

    fun setAnswersEnabled(enabled: Boolean, selectedId: Int? = null) {
        areOthersDisabled = !enabled
        selectedViewId = selectedId
    }

    fun highlightCorrectAnswer() {
        answers.forEachIndexed { index, answer ->
            if (answer.isCorrect) {
                notifyItemChanged(index)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val binding = ItemAnswerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AnswerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        holder.bind(answers[position])
    }

    override fun getItemCount(): Int = answers.size

    inner class AnswerViewHolder(
        private val binding: ItemAnswerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(answer: Answer) {
            binding.textAnswer.text = answer.answer

            binding.root.background = ContextCompat.getDrawable(
                binding.root.context,
                R.drawable.answer_background
            )
            binding.root.alpha = 1.0f

            binding.root.setOnClickListener {
                val canClick = if (!isClickable) {
                    false
                } else if (areOthersDisabled && selectedViewId != null) {
                    binding.root.id == selectedViewId
                } else {
                    true
                }

                if (canClick) {
                    listener.onClickOnAnswer(answer, binding.root)
                }
            }
        }
    }
}