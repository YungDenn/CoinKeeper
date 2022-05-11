package com.example.coinkeeper.presentation

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.anychart.core.ui.Legend
import com.example.coinkeeper.R
import com.example.coinkeeper.databinding.FragmentAddBinding
import com.example.coinkeeper.databinding.FragmentStatisticsBinding
import com.example.coinkeeper.domain.FinanceItem
import com.github.mikephil.charting.charts.PieChart
import java.lang.RuntimeException
import com.github.mikephil.charting.animation.Easing

import com.github.mikephil.charting.formatter.PercentFormatter

import com.github.mikephil.charting.data.PieData

import com.github.mikephil.charting.data.PieDataSet

import com.github.mikephil.charting.utils.ColorTemplate

import com.github.mikephil.charting.data.PieEntry





class Statistics_Fragment : Fragment() {

    private lateinit var viewModelMain : MainViewModel

    //private var chart: AnyChartView? = null

    private lateinit var pieChart: PieChart
    private lateinit var legend: com.github.mikephil.charting.components.Legend

    private var _binding: FragmentStatisticsBinding? = null
    private val binding: FragmentStatisticsBinding
        get() = _binding ?: throw RuntimeException("FragmentAddBinding == null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)

        pieChart = binding.sfPieChart

        setupPieChart()
        loadPieChartData()
        return binding.root
    }
    private fun setupPieChart() {
        pieChart.isDrawHoleEnabled = true
        pieChart.setUsePercentValues(true)
        pieChart.setEntryLabelTextSize(12f)
        pieChart.setEntryLabelColor(Color.BLACK)
        pieChart.centerText = "Траты по категориям"
        pieChart.setCenterTextSize(20f)
        pieChart.description.isEnabled = false
        legend = pieChart.legend
        legend.verticalAlignment = com.github.mikephil.charting.components.Legend
            .LegendVerticalAlignment.TOP
        legend.horizontalAlignment = com.github.mikephil.charting.components.Legend
            .LegendHorizontalAlignment.RIGHT
        legend.orientation = com.github.mikephil.charting.components.Legend
            .LegendOrientation.VERTICAL
        legend.setDrawInside(false)
        legend.isEnabled = false
        legend.textSize = 15f

    }

    private fun loadPieChartData() {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry(0.2f, "Еда и рестораны"))
        entries.add(PieEntry(0.15f, "Медицина"))
        entries.add(PieEntry(0.10f, "Развлечения"))
        entries.add(PieEntry(0.25f, "Комунальные услуги"))
        entries.add(PieEntry(0.3f, "Образование"))
        val colors: ArrayList<Int> = ArrayList()
        for (color in ColorTemplate.MATERIAL_COLORS) {
            colors.add(color)
        }
        for (color in ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color)
        }
        val dataSet = PieDataSet(entries, "Expense Category")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        pieChart.data = data
        pieChart.invalidate()
        pieChart.animateY(1400, Easing.EaseInOutQuad)
    }

//    private fun configChartView() {
//
//        val numbers2: MutableList<String> = mutableListOf()
//
//        viewModelMain = ViewModelProvider(this).get(MainViewModel::class.java)
//        viewModelMain.financeList.observe(this) {
//            var i = 0
//            while (i < it.size) {
//                numbers2.add(it.elementAt(i).name)
//                i++
//            }
//        }
//
//
//        val pie : Pie = AnyChart.pie()
//
//        val dataPieChart: MutableList<DataEntry> = mutableListOf()
//
//        for (index in salary.indices){
//            dataPieChart.add(ValueDataEntry(month.elementAt(index), salary.elementAt(index)))
//        }
//        pie.data(dataPieChart)
//        pie.title("Поступление денежных средств")
//        chart!!.setChart(pie)
//    }

}