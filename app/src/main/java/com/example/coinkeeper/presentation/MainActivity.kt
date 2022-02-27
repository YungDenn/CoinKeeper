package com.example.coinkeeper.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.coinkeeper.R
import com.example.coinkeeper.presentation.FinanceListAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var financeListAdapter: FinanceListAdapter
    //private var financeItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBottomNavigationView()

        //setupRecyclerView()
//        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
//        viewModel.financeList.observe(this) {
//            financeListAdapter.submitList(it)
//        }

    }
    private fun setupBottomNavigationView(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
        bottomNavigationView.setupWithNavController(navController)
    }

    //private fun setupRecyclerView() {
    //    val rvShopList = findViewById<RecyclerView>(R.id.rvFinanceItem)
    //    with(rvShopList) {
    //        financeListAdapter = FinanceListAdapter()
    //        adapter = financeListAdapter
    //        recycledViewPool.setMaxRecycledViews(
    //            FinanceListAdapter.VIEW_TYPE_INCOME,
    //            FinanceListAdapter.MAX_POOL_SIZE
    //        )
    //        recycledViewPool.setMaxRecycledViews(
    //            FinanceListAdapter.VIEW_TYPE_EXPENSE,
    //            FinanceListAdapter.MAX_POOL_SIZE
    //        )
    //        setupClickListener()
    //    }
    //}

    private fun setupClickListener() {
        financeListAdapter.onFinanceItemClickListener = {

        }
    }
}