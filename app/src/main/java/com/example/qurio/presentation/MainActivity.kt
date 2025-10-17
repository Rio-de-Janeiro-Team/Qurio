package com.example.qurio.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.qurio.R
import com.example.qurio.presentation.view.GamesFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val gamesFragment = GamesFragment()

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, gamesFragment)
                .commit()
        }
    }
}