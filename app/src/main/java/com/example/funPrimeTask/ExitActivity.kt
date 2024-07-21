package com.example.funPrimeTask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.funprimetask.R

class ExitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit)

        // Find the NavHostFragment and set up the NavController
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_exit) as NavHostFragment
        navHostFragment.navController
    }
}