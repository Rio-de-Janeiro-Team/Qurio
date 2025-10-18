package com.example.qurio.presentation.view.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.qurio.R
import com.example.qurio.base.BaseFragment
import com.example.qurio.data.repository.GameRepositoryImpl
import com.example.qurio.data.source.remote.QuizApiService
import com.example.qurio.databinding.FragmentStartPalyBinding
import com.example.qurio.domain.entity.Question
import com.example.qurio.domain.repository.GameRepository
import com.example.qurio.presentation.presenter.GamePresenter
import kotlinx.coroutines.launch

class GameFragment() :
    BaseFragment<FragmentStartPalyBinding, GameView>(),
    GameView, GameInterActionListener {

    private val gameRepository: GameRepository = GameRepositoryImpl(
        quizApiService = QuizApiService()
    )

    val presenter: GamePresenter = GamePresenter(gameRepository)
    val allQuestions: MutableList<Question> = mutableListOf()

    private val args: GameFragmentArgs by navArgs()

    var numberOfQuestions: Int = 0
    var currentSelectedAnswer: Answer? = null
    var isAnswerSelected: Boolean = false
    var currentViewId: Int? = null
    var currentView: View? = null
    var question = 1
    var isAnswerChecked = false

    val adapter = GameAdapter(emptyList(), this)

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStartPalyBinding {
        return FragmentStartPalyBinding.inflate(inflater, container, false)
    }

      fun initViews() {
        binding.checkButton.isEnabled = false

        binding.skipButton.setOnClickListener {
            goToNextQuestion()
        }

        binding.checkButton.setOnClickListener {
            if (!isAnswerChecked) {
                checkAnswer()
            } else {
                goToNextQuestion()
            }
        }

        setupRetryButton()
    }

    private fun setupRetryButton() {
        val noConnectionView = binding.noConnectionLayout.getChildAt(0)
        val retryButton = noConnectionView.findViewById<View>(R.id.retryButton)
        retryButton?.setOnClickListener {
            retryLoadingQuestions()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.gameView = this
        setUpAdapter()
    }

    override fun onStart() {
        super.onStart()
        initViews()
        loadQuestions()
    }

    private fun loadQuestions() {
        if (isNetworkAvailable()) {
            viewLifecycleOwner.lifecycleScope.launch {
                presenter.getQuestions(args.genreId)
            }
        } else {
            showNoConnection()
        }
    }

    private fun retryLoadingQuestions() {
        loadQuestions()
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
    }

    private fun showNoConnection() {
        binding.gameLayout.visibility = View.GONE
        binding.loadingLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.GONE
        binding.noConnectionLayout.visibility = View.VISIBLE
    }

    private fun hideNoConnection() {
        binding.noConnectionLayout.visibility = View.GONE
    }

    private fun setUpAdapter() {
        binding.answersLayout.adapter = adapter
    }

    override fun onGetQuestions(questions: List<Question>) {
        allQuestions.addAll(questions)
        numberOfQuestions = allQuestions.size
        hideLoading()
        hideNoConnection()
        setQuestion()
    }

    private fun checkAnswer() {
        if (currentSelectedAnswer == null || currentView == null) {
            return
        }

        adapter.setClickable(false)

        if (currentSelectedAnswer?.isCorrect == true) {
            currentView!!.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.correct_answer_background
            )

            binding.scoreIndicatorView.root.visibility = View.VISIBLE
            setBackgroundIndicatorsColor(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.correct_answer_background
                )
            )
            setScoreIndicatorsText("+1")
        } else {
            currentView!!.background = ContextCompat.getDrawable(
                requireContext(),
                R.drawable.incorrect_answer_background
            )
            binding.scoreIndicatorView.root.visibility = View.VISIBLE
            setBackgroundIndicatorsColor(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.incorrect_answer_background
                )
            )
            setScoreIndicatorsText("-1")

            highlightCorrectAnswer()
        }

        isAnswerChecked = true
        binding.checkButton.text = "Next"
        binding.skipButton.visibility = View.GONE
    }

    private fun highlightCorrectAnswer() {
        adapter.highlightCorrectAnswer()
    }

    private fun goToNextQuestion() {
        binding.scoreIndicatorView.root.visibility = View.GONE

        resetAnswerStates()

        setQuestion()

        binding.checkButton.text = "Check"
        binding.checkButton.isEnabled = false
        binding.skipButton.visibility = View.VISIBLE
        isAnswerChecked = false

        adapter.setClickable(true)
        adapter.setAnswersEnabled(true)
    }

    private fun resetAnswerStates() {
        currentSelectedAnswer = null
        isAnswerSelected = false
        currentViewId = null
        currentView = null
    }

    override fun onClickGetNextQuestion() {
        goToNextQuestion()
    }

    override fun onClickOnAnswer(answer: Answer, view: View) {
        if (isAnswerChecked) return

        // If user selects a different answer
        if (isAnswerSelected && currentViewId != view.id) {
            // Reset background of previously selected view
            currentView?.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.answer_background)

            // Update new selected answer
            view.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.selected_answer_background)

            currentSelectedAnswer = answer
            currentViewId = view.id
            currentView = view
            binding.checkButton.isEnabled = true
            return
        }

        // If user clicks again on the same answer -> deselect
        if (currentViewId == view.id && isAnswerSelected) {
            view.background =
                ContextCompat.getDrawable(requireContext(), R.drawable.answer_background)
            currentSelectedAnswer = null
            isAnswerSelected = false
            currentViewId = null
            currentView = null
            binding.checkButton.isEnabled = false
            return
        }

        // If no answer was selected yet
        view.background =
            ContextCompat.getDrawable(requireContext(), R.drawable.selected_answer_background)
        currentSelectedAnswer = answer
        isAnswerSelected = true
        currentViewId = view.id
        currentView = view
        binding.checkButton.isEnabled = true
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setQuestion() {
        if (allQuestions.isNotEmpty()) {
            val currentQuestion = allQuestions.first()
            binding.questionsPager.questionText.text = currentQuestion.question
            binding.questionsPager.questionNumber.text = "Q $question / $numberOfQuestions"

            val answers = (currentQuestion.incorrectAnswers.map { Answer(it, false) } +
                    Answer(currentQuestion.correctAnswer, true))
                .shuffled()

            adapter.setItems(answers)
            binding.answersLayout.post {
                adapter.notifyDataSetChanged()
            }

            allQuestions.removeAt(0)
            question += 1
        } else {
            showEndOfQuestions()
        }
    }

    override fun showQuestion(question: Question, questionNumber: String) {
        binding.questionsPager.questionText.text = question.question
        binding.questionsPager.questionNumber.text = questionNumber
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun resetAnswers() {
        resetAnswerStates()
        adapter.notifyDataSetChanged()
    }

    override fun updateTimer(secondsLeft: Long, progress: Float) {
        TODO("not implemented yet")
    }

    override fun onTimerFinished() {
        TODO("not implemented yet")
    }

    override fun showEndOfQuestions() {
        // TODO : Navigate to results screen or handle end of quiz
        showToastMessage("Quiz Completed! 🎉", Toast.LENGTH_LONG)
    }

    override fun showError(error: Throwable) {
        showToastMessage(error.message ?: "An error occurred")
    }

    override fun toggleSkipButton(visible: Boolean) {
        binding.skipButton.visibility = if (visible) View.VISIBLE else View.GONE
    }

    private fun showToastMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(requireContext(), message, duration).show()
    }

    override fun showLoading() {
        binding.loadingLayout.visibility = View.VISIBLE
        binding.errorLayout.visibility = View.GONE
        binding.gameLayout.visibility = View.GONE
        binding.noConnectionLayout.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.gameLayout.visibility = View.VISIBLE
        binding.loadingLayout.visibility = View.GONE
        binding.noConnectionLayout.visibility = View.GONE
    }

    override fun showError(message: String) {
        binding.gameLayout.visibility = View.GONE
        binding.loadingLayout.visibility = View.GONE
        binding.noConnectionLayout.visibility = View.GONE
        binding.errorLayout.visibility = View.VISIBLE
    }

    override fun showMessage(message: String) {
        showToastMessage(message)
    }

    private fun setBackgroundIndicatorsColor(contextCompat: Drawable?) {
        binding.scoreIndicatorView.indicator1.scoreText.background = contextCompat
        binding.scoreIndicatorView.indicator2.scoreText.background = contextCompat
        binding.scoreIndicatorView.indicator3.scoreText.background = contextCompat
        binding.scoreIndicatorView.indicator4.scoreText.background = contextCompat
        binding.scoreIndicatorView.indicator5.scoreText.background = contextCompat
        binding.scoreIndicatorView.indicator6.scoreText.background = contextCompat
        binding.scoreIndicatorView.indicator7.scoreText.background = contextCompat
        binding.scoreIndicatorView.indicator8.scoreText.background = contextCompat
    }

    private fun setScoreIndicatorsText(score: String) {
        binding.scoreIndicatorView.indicator1.scoreText.text = score
        binding.scoreIndicatorView.indicator2.scoreText.text = score
        binding.scoreIndicatorView.indicator3.scoreText.text = score
        binding.scoreIndicatorView.indicator4.scoreText.text = score
        binding.scoreIndicatorView.indicator5.scoreText.text = score
        binding.scoreIndicatorView.indicator6.scoreText.text = score
        binding.scoreIndicatorView.indicator7.scoreText.text = score
        binding.scoreIndicatorView.indicator8.scoreText.text = score
    }
}