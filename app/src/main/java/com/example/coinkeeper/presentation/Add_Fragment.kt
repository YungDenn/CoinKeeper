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


class Add_Fragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var financeListAdapter: FinanceListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



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