package com.example.qurio

import android.app.Application
import com.example.qurio.di.AppComponent
import com.example.qurio.di.AppModule
import com.example.qurio.di.DaggerAppComponent

class QurioApp : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        AppModule.init(this)
        appComponent = DaggerAppComponent.create()
    }
}