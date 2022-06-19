package com.example.coinkeeper.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.coinkeeper.R
import com.example.coinkeeper.databinding.FragmentAddBinding
import com.example.coinkeeper.domain.FinanceItem
import java.lang.RuntimeException
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.coinkeeper.domain.Account
import com.example.coinkeeper.domain.CategoryOperation
import kotlinx.android.synthetic.main.fragment_add_.*

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
        viewModelMain = ViewModelProvider(this)[MainViewModel::class.java]
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
            setupBalance()
        }

    }

    override fun onEditingFinished() {
        parentFragmentManager.popBackStack()
    }

    private fun setupBalance() {
        val tvBalance = binding.tvBalance
        viewModelMain.accountBalance.observe(this) {
            tvBalance.text = it.toString()
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
                if (item.typeOperation == 1) {
                    viewModelMain.updateBalance(-item.sum, 0, true)
                }
                else {
                    viewModelMain.updateBalance(item.sum, 0, true)
                }
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
                val account = Account(0, "Основной", 0)
                viewModelMain.addAccount(account)

                Toast.makeText(requireContext(), "FirstLaunch", Toast.LENGTH_SHORT).show()
                val arrayFinanceItems: ArrayList<FinanceItem> = ArrayList()
                arrayFinanceItems.add(FinanceItem(0, "Поступление зарплаты", "", 50000, 1, "", 1))
                arrayFinanceItems.add(FinanceItem(0, "Поступление стипендии", "", 2500, 1, "", 2))
                arrayFinanceItems.add(FinanceItem(0, "Продажа акций", "", 15500, 1, "", 3))
                arrayFinanceItems.add(FinanceItem(0, "Покупка курсов", "", 15000, 0, "", 6))
                arrayFinanceItems.add(FinanceItem(0, "Зачиление кешбека", "", 1500, 1, "", 2))
                arrayFinanceItems.add(FinanceItem(0, "Зачиление дивидендов", "", 3000, 1, "", 3))
                arrayFinanceItems.add(FinanceItem(0, "Покупка лекарств", "", 3500, 0, "", 5))
                arrayFinanceItems.add(FinanceItem(0, "Покупки в магазине", "", 3500, 0, "", 4))
                arrayFinanceItems.add(FinanceItem(0, "Покупка билетов", "", 7500, 0, "", 7))
                arrayFinanceItems.add(FinanceItem(0, "ТО Авто", "", 10500, 0, "", 4))
                val arraySizeFinanceItems = arrayFinanceItems.size -1
                for(i in 0..arraySizeFinanceItems) {
                    viewModelMain.addFinanceItem(arrayFinanceItems[i])
                }
                val arrayCategoryOperations: ArrayList<CategoryOperation> = ArrayList()
                arrayCategoryOperations.add(CategoryOperation(1,"Поступление зарплаты", R.drawable.zp,1 ))
                arrayCategoryOperations.add(CategoryOperation(2,"Переводы на карту", R.drawable.card,1 ))
                arrayCategoryOperations.add(CategoryOperation(3,"Инвестиции", R.drawable.dividend,1 ))
                arrayCategoryOperations.add(CategoryOperation(4,"Магазины", R.drawable.store,0 ))
                arrayCategoryOperations.add(CategoryOperation(5,"Медицина", R.drawable.medicine,0 ))
                arrayCategoryOperations.add(CategoryOperation(6,"Оброзование", R.drawable.education,0 ))
                arrayCategoryOperations.add(CategoryOperation(7,"Путешествия", R.drawable.travel,0 ))
                arrayCategoryOperations.add(CategoryOperation(8,"Дом", R.drawable.house,0 ))
                arrayCategoryOperations.add(CategoryOperation(9,"Автомобиль", R.drawable.car,0 ))
                val arraySizeCategoryOperationsItems = arrayCategoryOperations.size -1
                for(i in 0..arraySizeCategoryOperationsItems) {
                    viewModelMain.addCategoryOperation(arrayCategoryOperations[i])
                }
                //account = Account(0, "Основной", 35000)
                //viewModelMain.addAccount(account)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null

    }

}