package com.example.qurio.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.qurio.domain.repository.UserPreferencesImpl
import com.example.qurio.presenter.OnBoardingPresenter
import com.example.qurio.presenter.repository.UserPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings_preferences")

@Module
object AppModule {

    lateinit var appContext: Context
        private set

    fun init(context: Context) {
        appContext = context.applicationContext
    }
    @Provides
    @Singleton
    fun provideContext(application: Application): Context =
        application.applicationContext


    @Provides
    fun provideUserPreferences(impl: UserPreferencesImpl): UserPreferences = impl

    @Provides
    fun provideDataStore(): DataStore<Preferences> {
        return appContext.dataStore
    }

    @Provides
    fun provideOnBoardingPresenter(userPreferences: UserPreferences): OnBoardingPresenter {
        return OnBoardingPresenter(userPreferences)
    }

}