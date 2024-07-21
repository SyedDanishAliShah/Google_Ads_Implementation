package com.example.funPrimeTask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.funprimetask.R
import com.example.funPrimeTask.fragments.PhotosFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add the PhotosFragment to the MainActivity
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, PhotosFragment())
            .commit()
    }
}