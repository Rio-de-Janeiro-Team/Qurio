package com.example.qurio.presentation.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.qurio.QurioApp
import com.example.qurio.R
import com.example.qurio.base.BaseFragment
import com.example.qurio.databinding.FragmentOnBoardingBinding
import com.example.qurio.presentation.adapter.OnboardingAdapter
import com.example.qurio.presentation.animation.setupSwipeUpGesture
import com.example.qurio.presentation.model.OnboardingPage
import com.example.qurio.presentation.view.OnBoardingView
import com.example.qurio.presenter.OnBoardingPresenter
import swipeUpAnimation
import javax.inject.Inject

class OnBoardingFragment :  BaseFragment<
        FragmentOnBoardingBinding,
        OnBoardingView,
        >(),
    OnBoardingView
    {


    @Inject
      lateinit var presenter: OnBoardingPresenter
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            injectDependencies()
            presenter.attachView(this)

            setupViewPager()
            setupSwipeAnimation()
            setupListeners()
        }
        private fun injectDependencies() {
            (requireActivity().application as QurioApp).appComponent.inject(this)
        }
        private fun setupViewPager() {
            binding.viewPager.apply {
                adapter = OnboardingAdapter(OnboardingPage.values())
                isUserInputEnabled = false
            }
        }
        private fun setupSwipeAnimation() {
            swipeUpAnimation(binding.onboardingView)
            setupSwipeUpGesture(binding.swipeImage) { onSwipeUp() }
        }

        private fun setupListeners() = with(binding) {
            rightArrow.setOnClickListener { onRightArrowClicked() }
            leftArrow.setOnClickListener { onLeftArrowClicked() }
        }


        override fun getViewBinding(
            inflater: LayoutInflater,
            container: ViewGroup?
        ): FragmentOnBoardingBinding = FragmentOnBoardingBinding.inflate(inflater, container, false)


        override fun onRightArrowClicked() {
            val pager = binding.viewPager
            val lastIndex = pager.adapter?.itemCount?.minus(1) ?: return

            if (pager.currentItem < lastIndex) {
                pager.currentItem++
                return
            }

        }
        override fun onSwipeUp() {
            findNavController().navigate(R.id.homeFragment)
        }
        override fun onLeftArrowClicked() {
            val pager = binding.viewPager
            if (pager.currentItem > 0) pager.currentItem--
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