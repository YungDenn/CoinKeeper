package com.example.coinkeeper.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.coinkeeper.R
import com.google.android.material.floatingactionbutton.FloatingActionButton


class Add_Fragment : Fragment(), FinanceItemFragment.OnEditingFinishedListener {


    private lateinit var financeListAdapter: FinanceListAdapter
    private lateinit var balance: MutableLiveData<Int>

    private lateinit var viewModelMain : MainViewModel
        //ViewModelProvider(this).get(MainViewModel::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelMain = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModelMain.financeList.observe(this) {
            financeListAdapter.submitList(it)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //setupAddOnClickListener()
        val buttonIncomeItem = view.findViewById<FloatingActionButton>(R.id.bAdd)
        val buttonExpenseItem = view.findViewById<FloatingActionButton>(R.id.bMinus)
        buttonIncomeItem?.setOnClickListener{
            launchFragment(FinanceItemFragment.newInstanceAddItemIncome())
        }
        buttonExpenseItem?.setOnClickListener{
            launchFragment(FinanceItemFragment.newInstanceAddItemExpense())
        }
        setupBalance()
    }

    override fun onEditingFinished() {
        parentFragmentManager.popBackStack()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_, container, false)
        setupRecyclerView(view)
        return view
    }
    private fun setupBalance(){
        val tv_Balance = requireActivity().findViewById<TextView>(R.id.tv_Balance)
        viewModelMain.balanceLD.observe(this){
            tv_Balance.setText(it.toString())
        }

    }
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
            setupSwipeListener(rvFinanceList)
            setupClickListener()
        }
    }

    private fun setupSwipeListener(rvFinanceList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = financeListAdapter.currentList[viewHolder.adapterPosition]
                viewModelMain.deleteFinanceItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvFinanceList)
    }

    private fun setupClickListener() {
        financeListAdapter.onFinanceItemClickListener = {
            launchFragment(FinanceItemFragment.newInstanceEditItem(it.id))
        }
    }

}