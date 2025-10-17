package com.example.qurio.presentation.view.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.qurio.base.BaseFragment
import com.example.qurio.databinding.FragmentStartPalyBinding
import com.example.qurio.domain.entity.Question
import com.example.qurio.presentation.presenter.GamePresenter
import kotlinx.coroutines.launch

class GameFragment(gamePresenter: GamePresenter) :
    BaseFragment<FragmentStartPalyBinding, GameView, GamePresenter>(), GameView,
    GameInterActionListener {
    override val presenter: GamePresenter = gamePresenter
    val allQuestions: MutableList<Question> = mutableListOf()
    val adapter = GameAdapter(emptyList())
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStartPalyBinding {
        return FragmentStartPalyBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        TODO("Not yet implemented")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.gameView = this
        setUpAdapter()
    }

    override fun onStart() {
        super.onStart()
        viewLifecycleOwner.lifecycleScope.launch {
            presenter.getQuestions()
        }
    }

    private fun setUpAdapter() {
        binding.answersLayout.adapter = adapter
    }

    override fun onGetQuestions(questions: List<Question>) {
        allQuestions.addAll(questions)
        setQuestion()
    }

    override fun onClickGetNextQuestion() {
        setQuestion()
    }

    fun setQuestion() {
        if (allQuestions.isNotEmpty()) {
            val currentQuestion = allQuestions.first()
            binding.questionsPager.questionText.text = currentQuestion.question
            val answers = (currentQuestion.incorrectAnswers.map { Answer(it, false) } +
                    Answer(currentQuestion.correctAnswer, true))
                .shuffled()
            adapter.setItems(answers)
            allQuestions.removeAt(0)
        }
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showError(message: String) {
        TODO("Not yet implemented")
    }

    override fun showMessage(message: String) {
        TODO("Not yet implemented")
    }

}