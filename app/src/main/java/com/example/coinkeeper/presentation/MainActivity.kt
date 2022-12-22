package com.example.coinkeeper.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.coinkeeper.R
import com.example.coinkeeper.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), FinanceItemFragment.OnEditingFinishedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigationView()
    }
    private fun setupBottomNavigationView(){
        val bottomNavigationView = binding.bottomNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.fragmentContainer
        ) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
    }

    override fun onEditingFinished() {
        supportFragmentManager.popBackStack()
    }
}