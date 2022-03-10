package com.example.coinkeeper.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Pie
import com.example.coinkeeper.R


class statistics_Fragment : Fragment() {

    private var chart: AnyChartView? = null

    private val salary = listOf(200,300,400,600)
    private val month = listOf("January","February","March","April")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_statistics_, container, false)

        chart = view.findViewById(R.id.cv_pieChart)

        configChartView()
        return view
    }

    private fun configChartView() {
        val pie : Pie = AnyChart.pie()

        val dataPieChart: MutableList<DataEntry> = mutableListOf()

        for (index in salary.indices){
            dataPieChart.add(ValueDataEntry(month.elementAt(index),salary.elementAt(index)))
        }
        pie.data(dataPieChart)
        pie.title("Salaries Overview")
        chart!!.setChart(pie)
    }

}