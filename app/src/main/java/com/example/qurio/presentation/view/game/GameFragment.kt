package com.example.qurio.presentation.view.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.qurio.R
import com.example.qurio.base.BaseFragment
import com.example.qurio.databinding.FragmentStartPalyBinding
import com.example.qurio.domain.entity.Question
import com.example.qurio.presentation.presenter.GamePresenter
import kotlinx.coroutines.launch

class GameFragment() :
    BaseFragment<FragmentStartPalyBinding, GameView, GamePresenter>(), GameView,
    GameInterActionListener {
    override val presenter: GamePresenter = GamePresenter()
    val allQuestions: MutableList<Question> = mutableListOf()

     var numberOfQuestions: Int = 0

    var currentSelectedAnswer: Answer? = null
    var isAnswerSelected: Boolean = false

    var currentViewId: Int? = null

    var currentView: View? = null

    var question = 1
    val adapter = GameAdapter(emptyList(),this)
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStartPalyBinding {
        return FragmentStartPalyBinding.inflate(inflater, container, false)
    }

    override fun initViews() {
        binding.skipButton.setOnClickListener {
            setQuestion()
            binding.answersLayout.setBackgroundColor(
                requireContext().getColor(R.color.surface)
            )
        }
        binding.checkButton.setOnClickListener {
            if (currentSelectedAnswer?.isCorrect == true && currentView != null) {
//                    currentView!!.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
                    currentView!!.background = ContextCompat.getDrawable(requireContext(), R.drawable.correct_answer_background)

            } else {
//                currentView!!.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                currentView!!.background = ContextCompat.getDrawable(requireContext(), R.drawable.incorrect_answer_background)
            }
//            binding.answersLayout.setBackgroundColor(
//                requireContext().getColor(R.color.surface)
//            )
        }
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
        initViews()
    }

    private fun setUpAdapter() {
        binding.answersLayout.adapter = adapter
    }

    override fun onGetQuestions(questions: List<Question>) {
        allQuestions.addAll(questions)
        numberOfQuestions = allQuestions.size
        setQuestion()
    }

    override fun onClickGetNextQuestion() {
        setQuestion()
    }

    override fun onClickOnAnswer(answer: Answer,view: View) {
        currentView = view
        if (currentViewId == null) currentViewId = view.id
        if (currentViewId != view.id){
            val previousSelectedView = binding.answersLayout.findViewById<View>(currentViewId!!)
//            previousSelectedView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.surface_high))
            previousSelectedView.background = ContextCompat.getDrawable(requireContext(), R.drawable.answer_background)

            currentViewId = view.id
            isAnswerSelected = false
        }
        if (isAnswerSelected.not()){
            view.background = ContextCompat.getDrawable(requireContext(), R.drawable.selected_answer_background)
            currentSelectedAnswer = answer
        }
        else{
//            view.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.surface_high))
            view.background = ContextCompat.getDrawable(requireContext(), R.drawable.answer_background)

            currentSelectedAnswer = null
        }
        isAnswerSelected = !isAnswerSelected
    }

    fun setQuestion() {
        if (allQuestions.isNotEmpty()) {
            val currentQuestion = allQuestions.first()
            binding.questionsPager.questionText.text = currentQuestion.question
            binding.questionsPager.questionNumber.setText("$question / $numberOfQuestions")
            val answers = (currentQuestion.incorrectAnswers.map { Answer(it, false) } +
                    Answer(currentQuestion.correctAnswer, true))
                .shuffled()
            adapter.setItems(answers)
            allQuestions.removeAt(0)
            question += 1
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