package com.example.coinkeeper.presentation

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
import com.example.coinkeeper.R
import com.example.coinkeeper.domain.FinanceItem


class Statistics_Fragment : Fragment() {

    private lateinit var viewModelMain : MainViewModel

    private var chart: AnyChartView? = null

    private val month = listOf("Получение зарплаты","Пополнение карты","Поступление дивидендов","Начисление кешбека")
    private val salary = listOf(80000,30000,5000,15000)



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

        val numbers2: MutableList<String> = mutableListOf()

        viewModelMain = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModelMain.financeList.observe(this) {
            var i = 0
            while (i < it.size) {
                numbers2.add(it.elementAt(i).name)
                i++
            }
        }


        val pie : Pie = AnyChart.pie()

        val dataPieChart: MutableList<DataEntry> = mutableListOf()

        for (index in salary.indices){
            dataPieChart.add(ValueDataEntry(month.elementAt(index), salary.elementAt(index)))
        }
        pie.data(dataPieChart)
        pie.title("Поступление денежных средств")
        chart!!.setChart(pie)
    }

}