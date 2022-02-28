package com.example.coinkeeper.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.coinkeeper.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Add_Fragment : Fragment(), FinanceItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var financeListAdapter: FinanceListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupAddOnClickListener()
        val buttonAddItem = view.findViewById<FloatingActionButton>(R.id.bAdd)
        buttonAddItem?.setOnClickListener{
            launchFragment(FinanceItemFragment.newInstanceAddItemIncome())
        }

    }

    override fun onEditingFinished() {
        parentFragmentManager.popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_, container, false)
        setupRecyclerView(view)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.financeList.observe(this) {
            financeListAdapter.submitList(it)
        }
        return view
    }
    //private fun setupAddOnClickListener(){
//
    //}


    private fun launchFragment(fragment: Fragment) {
        parentFragmentManager.popBackStack()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)//Добавляет фаргмент в BackStack
            .commit()
    }

    private fun setupRecyclerView(view : View) {
        val rvFinanceList = view.findViewById<RecyclerView>(R.id.rvFinanceItem)
        with(rvFinanceList) {
            financeListAdapter = FinanceListAdapter()
            adapter = financeListAdapter
            recycledViewPool.setMaxRecycledViews(
                FinanceListAdapter.VIEW_TYPE_INCOME,
                FinanceListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                FinanceListAdapter.VIEW_TYPE_EXPENSE,
                FinanceListAdapter.MAX_POOL_SIZE
            )
            //setupClickListener()
        }
    }

}