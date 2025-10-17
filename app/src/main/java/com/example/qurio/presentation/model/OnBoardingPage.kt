package com.example.qurio.presentation.model

import com.example.qurio.R


data class OnboardingPage(
    val imageRes: Int,
    val title: Int,
    val description: Int
) {
    companion object {
        fun values(): List<OnboardingPage> {
            return listOf(
                OnboardingPage(
                    imageRes = R.drawable.brain_image,
                    title = R.string.welcome_to_qurio,
                    description = R.string.description1
                ),
                OnboardingPage(
                    imageRes = R.drawable.characters,
                    title = R.string.choose_your_character,
                    description = R.string.description2
                ),
                OnboardingPage(
                    imageRes = R.drawable.crown_image,
                    title = R.string.challenge_and_win,
                    description = R.string.description3
                ),
                OnboardingPage(
                    imageRes = R.drawable.cup_image,
                    title = R.string.collect_them_all,
                    description = R.string.description4
                ),
            )
        }
    }
}
