package com.example.coinkeeper.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import com.example.coinkeeper.databinding.FragmentStatisticsBinding
import com.github.mikephil.charting.charts.PieChart
import java.lang.RuntimeException
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import android.R
import android.annotation.SuppressLint

import android.widget.ArrayAdapter

import android.widget.Spinner
import android.widget.AdapterView


class Statistics_Fragment : Fragment() {

    private lateinit var viewModelMain: MainViewModel

    private lateinit var pieChartAll: PieChart
    private lateinit var pieChartCategory: PieChart
    private lateinit var barChar: BarChart

    private lateinit var legend: com.github.mikephil.charting.components.Legend

    private var _binding: FragmentStatisticsBinding? = null
    private val binding: FragmentStatisticsBinding
        get() = _binding ?: throw RuntimeException("FragmentAddBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        pieChartCategory = binding.pieChartCategory
        barChar = binding.barChart
        pieChartAll = binding.pieChartAllOperation

        setupSpinner()

        return binding.root
    }

    @SuppressLint("ResourceType")
    private fun setupSpinner() {

        val spinner: Spinner = binding.spinner2
        val adapter: ArrayAdapter<*> = ArrayAdapter.createFromResource(
            requireContext(), com.example.coinkeeper.R.array.spinner_items,
            R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                itemSelected: View, selectedItemPosition: Int, selectedId: Long
            ) {
                when (selectedItemPosition) {
                    0 -> {
                        setupPieChartAll()
                    }
                    1 -> {
                        setupBarChart()
                    }
                    2 -> {
                        setupPieChartCategory()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                setupPieChartAll()
            }
        }
    }

    private fun setupBarChart() {

        barChar.isGone = false
        pieChartCategory.isGone = true
        pieChartAll.isGone = true



        barChar.description.isEnabled = false
        barChar.setPinchZoom(true)

        barChar.setDrawBarShadow(false)
        barChar.setDrawGridBackground(false)

        val xAxis: XAxis = barChar.xAxis
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        barChar.axisLeft.setDrawGridLines(false)
        barChar.animateY(1500)
        barChar.legend.isEnabled = true
        barChar.setNoDataText("Данные не обнаружены!")
        barChar.setFitBars(true)
        loadBarChartData()

    }

    private fun loadBarChartData() {
        val entries: ArrayList<BarEntry> = ArrayList()
        entries.add(BarEntry(1f, 3f, "wasd1"))
        entries.add(BarEntry(2f, 5f, "wasd2"))
        entries.add(BarEntry(3f, 3f, "wasd3"))
        entries.add(BarEntry(4f, 6f, "wasd4"))
        entries.add(BarEntry(5f, 4f, "wasd5"))
        entries.add(BarEntry(6f, 2f, "wasd6"))
        entries.add(BarEntry(7f, 9f, "wasd7"))

        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = BarDataSet(entries, "Доходы")
        dataSet.colors = colors
        val data = BarData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChartCategory))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        barChar.data = data
        barChar.invalidate()
        barChar.animateY(1400, Easing.EaseInOutQuad)
        legend = barChar.legend
        legend.verticalAlignment = com.github.mikephil.charting.components.Legend
            .LegendVerticalAlignment.TOP
        legend.horizontalAlignment = com.github.mikephil.charting.components.Legend
            .LegendHorizontalAlignment.RIGHT
        legend.textSize = 15f


    }

    private fun setupPieChartCategory() {

        barChar.isGone = true
        pieChartCategory.isGone = false
        pieChartAll.isGone = true


        pieChartCategory.isGone = false
        pieChartCategory.isDrawHoleEnabled = true
        pieChartCategory.setUsePercentValues(true)

        pieChartCategory.setEntryLabelTextSize(12f)
        pieChartCategory.setEntryLabelColor(Color.BLACK)
        pieChartCategory.centerText = "Траты по категориям"
        pieChartCategory.setCenterTextSize(20f)
        pieChartCategory.description.isEnabled = false
        legend = pieChartCategory.legend
        legend.verticalAlignment = com.github.mikephil.charting.components.Legend
            .LegendVerticalAlignment.TOP
        legend.horizontalAlignment = com.github.mikephil.charting.components.Legend
            .LegendHorizontalAlignment.RIGHT
        legend.orientation = com.github.mikephil.charting.components.Legend
            .LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.isEnabled = false
        legend.textSize = 15f

        loadPieChartData()
    }

    private fun loadPieChartData() {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(50000f, "Поступление зарплаты"))
        entries.add(PieEntry(14000f, "Поступление дивидендов"))
        entries.add(PieEntry(4000f, "Выплата по страховке"))
        entries.add(PieEntry(2400f, "Поступление стипендии"))
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "Доходы")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChartCategory))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChartCategory.data = data
        pieChartCategory.invalidate()
        pieChartCategory.animateY(1400, Easing.EaseInOutQuad)
    }

    private fun setupPieChartAll() {
        barChar.isGone = true
        pieChartCategory.isGone = true
        pieChartAll.isGone = false

        pieChartCategory.isDrawHoleEnabled = true
        pieChartCategory.setUsePercentValues(true)

        pieChartCategory.setEntryLabelTextSize(12f)
        pieChartCategory.setEntryLabelColor(Color.BLACK)
        pieChartCategory.centerText = "Отношение трат к доходам"
        pieChartCategory.setCenterTextSize(20f)
        pieChartCategory.description.isEnabled = false
        legend = pieChartCategory.legend
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

    private fun loadPieChartAllData() {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(70400f, "Доходы"))
        entries.add(PieEntry(33000f, "Расходы"))

        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "Операции")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChartAll))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChartAll.data = data
        pieChartAll.invalidate()
        pieChartAll.animateY(1400, Easing.EaseInOutQuad)
    }

}