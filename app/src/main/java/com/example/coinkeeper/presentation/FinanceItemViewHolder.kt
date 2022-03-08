package com.example.coinkeeper.presentation

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coinkeeper.R

class FinanceItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {
    val tvName =view.findViewById<TextView>(R.id.tv_name)
    val tvSum =view.findViewById<TextView>(R.id.tv_sum)
}