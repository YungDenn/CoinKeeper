package com.example.coinkeeper.presentation

import android.R
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.coinkeeper.databinding.FragmentStatisticsBinding
import com.example.coinkeeper.presentation.viewmodels.StatisticsViewModel
import com.example.coinkeeper.presentation.viewmodels.ViewModelFactory
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import javax.inject.Inject


class StatisticsFragment: Fragment() {

    private  lateinit var statisticsViewModel: StatisticsViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val component by lazy {
        (requireActivity().application as CoinKeeperApp).component
    }

    private lateinit var pieChartAll: PieChart
    private lateinit var pieChartCategoryAdd: PieChart
    private lateinit var pieChartCategoryExpense: PieChart
    private lateinit var spinner: Spinner
    private lateinit var adapter: ArrayAdapter<*>
    private lateinit var legend: com.github.mikephil.charting.components.Legend




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
        statisticsViewModel = ViewModelProvider(this, viewModelFactory)[StatisticsViewModel::class.java]
        binding.lifecycleOwner = viewLifecycleOwner
        pieChartCategoryAdd = binding.pieChartCategoryAdd
        pieChartCategoryExpense = binding.pieChartCategoryExpense
        pieChartAll = binding.pieChartAllOperation
        setupSpinner()
        return binding.root
    }

    private fun setupSpinner() {
        spinner = binding.spinnerStatistics
        adapter = ArrayAdapter.createFromResource(
            requireContext(), com.example.coinkeeper.R.array.spinner_items,
            R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
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
                        setupPieChartAll()
                    }
                    1 -> {
                        setupPieChartCategoryAdd()
                    }
                    2 ->{
                        setupPieChartCategoryExpense()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //setupPieChartAll()
            }
        }
    }

    private fun setupPieChartCategoryAdd() {
        if (pieChartCategoryAdd.isGone) {
            pieChartCategoryAdd.isGone = false
            pieChartAll.isGone = true
            pieChartCategoryExpense.isGone = true


        }
        pieChartCategoryAdd.isGone = false
        pieChartCategoryAdd.isDrawHoleEnabled = true
        pieChartCategoryAdd.setUsePercentValues(true)

        pieChartCategoryAdd.setEntryLabelTextSize(12f)
        pieChartCategoryAdd.setEntryLabelColor(Color.BLACK)
        pieChartCategoryAdd.centerText = "Поступления по категориям"
        pieChartCategoryAdd.setCenterTextSize(20f)
        pieChartCategoryAdd.description.isEnabled = false
        legend = pieChartCategoryAdd.legend
        legend.verticalAlignment = com.github.mikephil.charting.components.Legend
            .LegendVerticalAlignment.TOP
        legend.horizontalAlignment = com.github.mikephil.charting.components.Legend
            .LegendHorizontalAlignment.RIGHT
        legend.orientation = com.github.mikephil.charting.components.Legend
            .LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.isEnabled = false
        legend.textSize = 15f

        loadPieChartDataForAdd()
    }
    //Доходы по категориям

    private fun loadPieChartDataForAdd() {
        //val sumCategory: ArrayList<Int> = ArrayList()
        val financeItems: ArrayList<Int> = ArrayList()
        val entries: ArrayList<PieEntry> = ArrayList()
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        statisticsViewModel.getCategoryOperationByType(1).observe(this) { category->
            category.forEach { category ->
                //categoryOperation.add(it)
                statisticsViewModel.getFinanceItemByCategoryOperation(category.id).observe(this){ items ->
                    items.forEach{
                        financeItems.add(it.sum)
                    }
                    entries.add(PieEntry(financeItems.sum().toFloat(), category.name))
                    val dataSet = PieDataSet(entries, "Доходы")
                    dataSet.colors = colors
                    val data = PieData(dataSet)
                    data.setDrawValues(true)
                    data.setValueFormatter(PercentFormatter(pieChartCategoryAdd))
                    data.setValueTextSize(12f)
                    data.setValueTextColor(Color.BLACK)
                    pieChartCategoryAdd.data = data
                    pieChartCategoryAdd.invalidate()
                    pieChartCategoryAdd.animateY(1400, Easing.EaseInOutQuad)
                }
            }
        }
    }

    private fun setupPieChartCategoryExpense(){
        if (pieChartCategoryExpense.isGone) {
            pieChartCategoryExpense.isGone = false
            pieChartCategoryAdd.isGone = true
            pieChartAll.isGone = true
        }
        pieChartCategoryExpense.isDrawHoleEnabled = true
        pieChartCategoryExpense.setUsePercentValues(true)
        pieChartCategoryExpense.setEntryLabelTextSize(12f)
        pieChartCategoryExpense.setEntryLabelColor(Color.BLACK)
        pieChartCategoryExpense.centerText = "Расходы по категориям"
        pieChartCategoryExpense.setCenterTextSize(20f)
        pieChartCategoryExpense.description.isEnabled = false
        legend = pieChartCategoryExpense.legend
        legend.verticalAlignment = com.github.mikephil.charting.components.Legend
            .LegendVerticalAlignment.TOP
        legend.horizontalAlignment = com.github.mikephil.charting.components.Legend
            .LegendHorizontalAlignment.RIGHT
        legend.orientation = com.github.mikephil.charting.components.Legend
            .LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.isEnabled = false
        legend.textSize = 15f

        loadPieChartDataForExpense()
    }
    private fun loadPieChartDataForExpense() {
        //val sumCategory: ArrayList<Int> = ArrayList()
        val financeItems: ArrayList<Int> = ArrayList()
        val entries: ArrayList<PieEntry> = ArrayList()
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        statisticsViewModel.getCategoryOperationByType(0).observe(this) { category->
            category.forEach { category ->
                //categoryOperation.add(it)
                statisticsViewModel.getFinanceItemByCategoryOperation(category.id).observe(this){ items ->
                    items.forEach{
                        financeItems.add(it.sum)
                    }
                    entries.add(PieEntry(financeItems.sum().toFloat(), category.name))
                    val dataSet = PieDataSet(entries, "Доходы")
                    dataSet.colors = colors
                    val data = PieData(dataSet)
                    data.setDrawValues(true)
                    data.setValueFormatter(PercentFormatter(pieChartCategoryExpense))
                    data.setValueTextSize(12f)
                    data.setValueTextColor(Color.BLACK)
                    pieChartCategoryExpense.data = data
                    pieChartCategoryExpense.invalidate()
                    pieChartCategoryExpense.animateY(1400, Easing.EaseInOutQuad)
                }
            }
        }
    }


    private fun setupPieChartAll() {
        if (pieChartAll.isGone) {
            pieChartAll.isGone = false
            pieChartCategoryAdd.isGone = true
            pieChartCategoryExpense.isGone = true
        }
        pieChartCategoryAdd.isDrawHoleEnabled = true
        pieChartCategoryAdd.setUsePercentValues(true)

        pieChartCategoryAdd.setEntryLabelTextSize(12f)
        pieChartCategoryAdd.setEntryLabelColor(Color.BLACK)
        pieChartCategoryAdd.centerText = "Отношение трат к доходам"
        pieChartCategoryAdd.setCenterTextSize(20f)
        pieChartCategoryAdd.description.isEnabled = false
        legend = pieChartCategoryAdd.legend
        legend.verticalAlignment = com.github.mikephil.charting.components.Legend
            .LegendVerticalAlignment.TOP
        legend.horizontalAlignment = com.github.mikephil.charting.components.Legend
            .LegendHorizontalAlignment.RIGHT
        legend.orientation = com.github.mikephil.charting.components.Legend
            .LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.isEnabled = false
        legend.textSize = 15f
        loadPieChartAllData()
    }
    //Загрузка диаграммы с отношением количества доходов к количеству расходов
    private fun loadPieChartAllData() {
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val entriesForAdd: ArrayList<Int> = ArrayList()
        val entriesForExpense: ArrayList<Int> = ArrayList()
        val result: ArrayList<PieEntry> = ArrayList()

        statisticsViewModel.financeList.observe(this) {
            entriesForAdd.clear()
            entriesForExpense.clear()
            result.clear()
            statisticsViewModel.getFinanceItemListByTypeOperation(1).observe(this) { addEntries ->
                //entriesForAdd.clear()
                addEntries.forEach { item ->
                    entriesForAdd.add(item.sum)
                }
                result.add(PieEntry(entriesForAdd.sum().toFloat(), "Доходы"))
                statisticsViewModel.getFinanceItemListByTypeOperation(0).observe(this) { expenseEntries ->
                    //entriesForExpense.clear()
                    expenseEntries.forEach { item ->
                        entriesForExpense.add(item.sum)
                    }
                    result.add(PieEntry(entriesForExpense.sum().toFloat(), "Расходы"))
                    val dataSet = PieDataSet(result, "Операции")
                    dataSet.colors = colors
                    val data = PieData(dataSet)
                    data.setDrawValues(true)
                    data.setValueFormatter(PercentFormatter(pieChartAll))
                    data.setValueTextSize(16f)
                    data.setValueTextColor(Color.BLACK)
                    pieChartAll.data = data
                    pieChartAll.invalidate()
                    pieChartAll.animateY(1400, Easing.EaseInOutQuad)
                }
            }
        }
//        val dataSet = PieDataSet(result, "Операции")
//        //dataSet.colors = colors
//        val data = PieData(dataSet)
//        data.setDrawValues(true)
//        data.setValueFormatter(PercentFormatter(pieChartAll))
//        data.setValueTextSize(16f)
//        data.setValueTextColor(Color.BLACK)
//        pieChartAll.data = data
//        pieChartAll.invalidate()
//        pieChartAll.animateY(1400, Easing.EaseInOutQuad)
    }
}