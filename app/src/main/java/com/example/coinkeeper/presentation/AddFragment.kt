package com.example.coinkeeper.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.coinkeeper.R
import com.example.coinkeeper.databinding.FragmentAddBinding
import com.example.coinkeeper.domain.entity.Account
import com.example.coinkeeper.domain.entity.CategoryOperation
import com.example.coinkeeper.domain.entity.FinanceItem
import com.example.coinkeeper.presentation.adapters.FinanceListAdapter
import com.example.coinkeeper.presentation.viewmodels.MainViewModel
import com.example.coinkeeper.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class AddFragment : Fragment(), FinanceItemFragment.OnEditingFinishedListener {

    private lateinit var financeListAdapter: FinanceListAdapter

    private lateinit var viewModelMain: MainViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CoinKeeperApp).component
    }

    private var _binding: FragmentAddBinding? = null
    private val binding: FragmentAddBinding
        get() = _binding ?: throw RuntimeException("FragmentAddBinding == null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelMain = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        setupBalance()
        viewModelMain.financeList.observe(viewLifecycleOwner) {
            financeListAdapter.submitList(it)
        }
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
        viewModelMain.accountBalance.observe(viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(requireContext(), "FirstLaunch", Toast.LENGTH_SHORT).show()
                firstStart()
            }
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
//        PreferenceManager.getDefaultSharedPreferences(requireContext()).apply {
//            if (getBoolean("firstStart", false)){
                val account = Account(0, "Основной", 0)
                viewModelMain.addAccount(account)

                Toast.makeText(requireContext(), "FirstLaunch", Toast.LENGTH_SHORT).show()
                val arrayFinanceItems: ArrayList<FinanceItem> = ArrayList()
                arrayFinanceItems.add(FinanceItem(0, "Поступление зарплаты", "", 50000, 1, "", 1, R.drawable.card))
                arrayFinanceItems.add(FinanceItem(0, "Поступление стипендии", "", 2500, 1, "", 2,R.drawable.card))
                arrayFinanceItems.add(FinanceItem(0, "Продажа акций", "", 15500, 1, "", 3,R.drawable.dividend))
                arrayFinanceItems.add(FinanceItem(0, "Покупка курсов", "", 15000, 0, "", 6, R.drawable.education))
                arrayFinanceItems.add(FinanceItem(0, "Зачиление кешбека", "", 1500, 1, "", 2, R.drawable.card))
                arrayFinanceItems.add(FinanceItem(0, "Зачиление дивидендов", "", 3000, 1, "", 3, R.drawable.dividend))
                arrayFinanceItems.add(FinanceItem(0, "Покупка лекарств", "", 3500, 0, "", 5, R.drawable.medicine))
                arrayFinanceItems.add(FinanceItem(0, "Покупки в магазине", "", 3500, 0, "", 4,R.drawable.store))
                arrayFinanceItems.add(FinanceItem(0, "Покупка билетов", "", 7500, 0, "", 7, R.drawable.travel))
                arrayFinanceItems.add(FinanceItem(0, "ТО Авто", "", 10500, 0, "", 4,R.drawable.car))
                for(i in 0 until arrayFinanceItems.size) {
                    viewModelMain.addFinanceItem(arrayFinanceItems[i])
                    //Sum: 32500
                }
                val arrayCategoryOperations: ArrayList<CategoryOperation> = ArrayList()
                arrayCategoryOperations.add(CategoryOperation(1,"Поступление зарплаты", R.drawable.zp,1 ))
                arrayCategoryOperations.add(CategoryOperation(2,"Переводы на карту", R.drawable.card,1 ))
                arrayCategoryOperations.add(CategoryOperation(3,"Инвестиции", R.drawable.dividend,1 ))
                arrayCategoryOperations.add(CategoryOperation(4,"Магазины", R.drawable.store,0 ))
                arrayCategoryOperations.add(CategoryOperation(5,"Медицина", R.drawable.medicine,0 ))
                arrayCategoryOperations.add(CategoryOperation(6,"Образование", R.drawable.education,0 ))
                arrayCategoryOperations.add(CategoryOperation(7,"Путешествия", R.drawable.travel,0 ))
                arrayCategoryOperations.add(CategoryOperation(8,"Дом", R.drawable.house,0 ))
                arrayCategoryOperations.add(CategoryOperation(9,"Автомобиль", R.drawable.car,0 ))
                for(i in 0 until arrayCategoryOperations.size) {
                    viewModelMain.addCategoryOperation(arrayCategoryOperations[i])
                }
            }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}