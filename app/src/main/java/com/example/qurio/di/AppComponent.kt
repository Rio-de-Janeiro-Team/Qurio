package com.example.qurio.di

import com.example.qurio.presentation.fragment.OnBoardingFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [

    AppModule::class])
interface AppComponent {
    fun inject(fragment: OnBoardingFragment)
}