package com.example.funPrimeTask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.funprimetask.R
import com.example.funPrimeTask.fragments.FullScreenFragment

class FullScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_full_screen, FullScreenFragment())
            .commit()
    }
}