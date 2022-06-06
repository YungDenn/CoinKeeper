package com.example.coinkeeper.presentation

import android.content.Context.MODE_PRIVATE
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
import com.example.coinkeeper.databinding.FragmentAddBinding
import com.example.coinkeeper.domain.FinanceItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.lang.RuntimeException
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.coinkeeper.domain.CategoryOperation
import com.github.mikephil.charting.data.BarEntry

class Add_Fragment : Fragment(), FinanceItemFragment.OnEditingFinishedListener {


    private lateinit var financeListAdapter: FinanceListAdapter

    private lateinit var viewModelMain: MainViewModel


    private var _binding: FragmentAddBinding? = null
    private val binding: FragmentAddBinding
        get() = _binding ?: throw RuntimeException("FragmentGameBinding == null")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelMain = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModelMain.financeList.observe(this) {
            financeListAdapter.submitList(it)
        }
        firstStart()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        setupRecyclerView()
        //setupBalance()
        PreferenceManager.getDefaultSharedPreferences(requireContext()).edit().apply {
            putBoolean("firstStart", true)
            apply()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModelMain = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModelMain.financeList.observe(this) {
            financeListAdapter.submitList(it)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModelMain
        binding.lifecycleOwner = viewLifecycleOwner
        with(binding) {
            val buttonIncomeItem = bAdd
            val buttonExpenseItem = bMinus
            buttonIncomeItem.setOnClickListener {
                launchFragment(FinanceItemFragment.newInstanceAddItemIncome())
            }
            buttonExpenseItem.setOnClickListener {
                launchFragment(FinanceItemFragment.newInstanceAddItemExpense())
            }
        }

    }

    override fun onEditingFinished() {
        parentFragmentManager.popBackStack()
    }

    private fun setupBalance() {
        val tvBalance = binding.tvBalance
        viewModelMain.balanceLD.observe(this) {
            tvBalance.setText(it.toString())
        }
    }

    private fun launchFragment(fragment: Fragment) {
        parentFragmentManager.popBackStack()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)//Добавляет фаргмент в BackStack
            .commit()
    }

    private fun setupRecyclerView() {
        val rvFinanceList = binding.rvFinanceItem
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

    private fun firstStart(){
        PreferenceManager.getDefaultSharedPreferences(requireContext()).apply {
            if (!getBoolean("firstStart", false)){
                Toast.makeText(requireContext(), "FirstLaunch", Toast.LENGTH_SHORT).show()
                val arrayFinanceItems: ArrayList<FinanceItem> = ArrayList()
                arrayFinanceItems.add(FinanceItem(0, "Поступление зарплаты", "", 50000, 1, "", 1))
                arrayFinanceItems.add(FinanceItem(0, "Поступление стипендии", "", 2500, 1, "", 1))
                arrayFinanceItems.add(FinanceItem(0, "Покупки в магазине", "", 3500, 0, "", 1))
                val arraySizeFinanceItems = arrayFinanceItems.size -1
                for(i in 0..arraySizeFinanceItems) {
                    viewModelMain.addFinanceItem(arrayFinanceItems[i])
                }
                val arrayCategoryOperations: ArrayList<CategoryOperation> = ArrayList()
                arrayCategoryOperations.add(CategoryOperation(0,"Поступление зарплаты", R.drawable.zp,1 ))
                arrayCategoryOperations.add(CategoryOperation(0,"Пополение карты", R.drawable.zp,1 ))
                arrayCategoryOperations.add(CategoryOperation(0,"Покупки в магазине", R.drawable.store,0 ))
                arrayCategoryOperations.add(CategoryOperation(0,"Медицинские услуги", R.drawable.store,0 ))
                val arraySizeCategoryOperationsItems = arrayCategoryOperations.size -1
                for(i in 0..arraySizeCategoryOperationsItems) {
                    viewModelMain.addCategoryOperation(arrayCategoryOperations[i])
                }

            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

}