package com.example.qurio.presenter

 import com.example.qurio.base.BasePresenter
 import com.example.qurio.presentation.view.OnBoardingView
 import com.example.qurio.presenter.repository.UserPreferences
 import jakarta.inject.Inject


class OnBoardingPresenter @Inject constructor(
    private val userPreferences: UserPreferences
) : BasePresenter<OnBoardingView>() {
    fun setFirstLaunch() {
        tryToCall(
            block = { userPreferences.setFirstLaunch() },
        )
    }
}