package com.example.coinkeeper.presentation

import com.example.coinkeeper.R
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinkeeper.databinding.FragmentStatisticsBinding
import com.example.coinkeeper.domain.entity.FinanceItem
import com.example.coinkeeper.presentation.adapters.FinanceListAdapter
import com.example.coinkeeper.presentation.viewmodels.StatisticsViewModel
import com.example.coinkeeper.presentation.viewmodels.ViewModelFactory
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import javax.inject.Inject


class StatisticsFragment : Fragment() {

    private lateinit var statisticsViewModel: StatisticsViewModel

    private lateinit var financeListAdapter: FinanceListAdapter

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CoinKeeperApp).component
    }
    private lateinit var pieChartCategory: PieChart
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<*>
    private lateinit var legend: Legend

    private var _binding: FragmentStatisticsBinding? = null
    private val binding: FragmentStatisticsBinding
        get() = _binding ?: throw RuntimeException("FragmentAddBinding == null")


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        statisticsViewModel =
            ViewModelProvider(this, viewModelFactory)[StatisticsViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        pieChartCategory = binding.pieChartForStat

        setupRecyclerView()
        setupSpinner()

        return binding.root
    }

    private fun setupRecyclerView() {
        val rvFinanceList = binding.rcFinanceItem
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
        }
    }

    private fun setupSpinner() {
        spinner = binding.spinnerStatistics
        adapter = ArrayAdapter.createFromResource(
            requireContext(), R.array.spinner_items,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View?,
                selectedItemPosition: Int,
                selectedId: Long
            ) {
                when (selectedItemPosition) {
                    0 -> {
                        setupPieChartIncomeAndExpenses(selectedItemPosition)
                    }
                    1 -> {
                        setupPieChartIncomeAndExpenses(selectedItemPosition)
                    }
                    2 -> {
                        setupPieChartIncomeAndExpenses(selectedItemPosition)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }
    private fun loadPieChartDataForAdd(add: Int) {
        val financeItems: ArrayList<Int> = ArrayList()
        val entries: ArrayList<PieEntry> = ArrayList()
        val idCategories: ArrayList<Int> = ArrayList()
        val colors: ArrayList<Int> = ArrayList()

        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }


        statisticsViewModel.getCategoryOperationByType(add)
            .observe(viewLifecycleOwner) { categories ->
                entries.clear()
                idCategories.clear()
                pieChartCategory.data = null
                Log.d("getFinanceItemByCategoryOperation", "category - $categories")

                categories.forEach { category ->
                    Log.d("getFinanceItemByCategoryOperation", "category - $category")

                    statisticsViewModel.getFinanceItemByCategoryOperation(category.id)
                        .observe(viewLifecycleOwner) { items ->
                            Log.d("getFinanceItemByCategoryOperation", "items - $items")

                            if (!items.isNullOrEmpty()) {
                                idCategories.add(category.id)
                                Log.d(
                                    "getFinanceItemByCategoryOperation",
                                    "item: ${items[0]} - done"
                                )

                                financeItems.clear()
                                financeItems.addAll(items.map { it.sum })

                                entries.add(PieEntry(financeItems.sum().toFloat(), category.name))

                                val dataSet = PieDataSet(entries, "Income")
                                dataSet.colors = colors
                                val data = PieData(dataSet)

                                data.setDrawValues(true)
                                data.setValueFormatter(PercentFormatter(pieChartCategory))
                                data.setValueTextSize(12f)
                                data.setValueTextColor(Color.WHITE)

                                pieChartCategory.data = data
                                pieChartCategory.invalidate()
                                pieChartCategory.animateY(1400, Easing.EaseInOutQuad)
                            }
                        }
                }
            }


        pieChartCategory.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                Log.d("onValueSelected", "h - ${h?.x.toString()} entire: $e")
                val selectedCategoryId = idCategories[h?.x?.toInt() ?: 0]
                statisticsViewModel.getFinanceItemByCategoryOperation(selectedCategoryId)
                    .observe(viewLifecycleOwner) {
                        financeListAdapter.submitList(it)
                    }
            }

            override fun onNothingSelected() {}
        })
    }
    private fun setupPieChartIncomeAndExpenses(loadData: Int) {

        pieChartCategory.apply {
            setTouchEnabled(true)
            setUsePercentValues(true)
            isDrawHoleEnabled = true
            setEntryLabelTextSize(14f)
            setEntryLabelColor(Color.WHITE)

            centerText = when (loadData) {
                0 -> getString(R.string.text_for_spinner_for_add_and_expense)
                1 -> getString(R.string.text_for_spinner_for_add_categories)
                2 -> getString(R.string.text_for_spinner_for_expense_categories)
                else -> ""
            }

            setCenterTextColor(Color.WHITE)
            setCenterTextSize(20f)
            description.isEnabled = false

            setHoleColor(R.color.black)
        }

        legend = pieChartCategory.legend
        legend.apply {
            verticalAlignment = Legend.LegendVerticalAlignment.TOP
            horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
            orientation = Legend.LegendOrientation.VERTICAL
            setDrawInside(false)
            isEnabled = false
            textSize = 15f
        }

        when (loadData) {
            0 -> loadPieChartIncomeAndExpenses()
            1 -> loadPieChartDataForAdd(1)
            2 -> loadPieChartDataForAdd(0)
        }
    }

    //Загрузка диаграммы с отношением количества доходов к количеству расходов

    private fun loadPieChartIncomeAndExpenses() {
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }

        statisticsViewModel.financeList.observe(this) { financeList ->
            val entriesForAdd: ArrayList<Int> = ArrayList()
            val entriesForExpense: ArrayList<Int> = ArrayList()
            val result: ArrayList<PieEntry> = ArrayList()
            val fianceItemListExpense: ArrayList<FinanceItem> = ArrayList()
            val fianceItemListAdd: ArrayList<FinanceItem> = ArrayList()

            Log.d("getFinanceItemByCategoryOperation", "Finance List: - $financeList")
            financeList.forEach { item ->
                if (item.typeOperation == 1) {
                    entriesForAdd.add(item.sum)
                    fianceItemListAdd.add(item)
                } else {
                    entriesForExpense.add(item.sum)
                    fianceItemListExpense.add(item)
                }
            }

            result.add(PieEntry(entriesForAdd.sum().toFloat(), "Доходы"))
            result.add(PieEntry(entriesForExpense.sum().toFloat(), "Расходы"))
            val dataSet = PieDataSet(result, "Операции")
            dataSet.colors = colors
            val data = PieData(dataSet)
            data.setDrawValues(true)
            data.setValueFormatter(PercentFormatter(pieChartCategory))
            data.setValueTextSize(16f)
            data.setValueTextColor(Color.WHITE)

            pieChartCategory.data = data
            pieChartCategory.invalidate()
            pieChartCategory.animateY(1400, Easing.EaseInOutQuad)

            pieChartCategory.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
                override fun onValueSelected(e: Entry?, h: Highlight?) {
                    if (h?.x == 1.0f) {
                        financeListAdapter.submitList(fianceItemListExpense)
                        Toast.makeText(requireContext(), "Sum: ${entriesForExpense.sum()}", Toast.LENGTH_SHORT).show()
                    } else {
                        financeListAdapter.submitList(fianceItemListAdd)
                        Toast.makeText(requireContext(), "Sum: ${entriesForAdd.sum()}", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onNothingSelected() {
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
