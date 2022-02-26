package com.example.coinkeeper.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.coinkeeper.R
import com.example.coinkeeper.domain.FinanceItem
import androidx.recyclerview.widget.ListAdapter


class FinanceListAdapter :
    ListAdapter<FinanceItem, FinanceItemViewHolder>(FinanceItemDiffCallback()) {

    var onFinanceItemClickListener: ((FinanceItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceItemViewHolder {

        val layout = when (viewType) {
            VIEW_TYPE_INCOME -> R.layout.finance_item_income
            VIEW_TYPE_EXPENSE -> R.layout.finance_item_expence
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return FinanceItemViewHolder(view)
    }


    override fun onBindViewHolder(viewHolder: FinanceItemViewHolder, position: Int) {
        val financeItem = getItem(position)
        viewHolder.view.setOnClickListener {
            onFinanceItemClickListener?.invoke(financeItem)
        }
        viewHolder.tvName.text = financeItem.name
        viewHolder.tvSum.text = financeItem.sum.toString()
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.category == 1) {
            VIEW_TYPE_INCOME
        } else {
            VIEW_TYPE_EXPENSE
        }
    }

    companion object {

        const val VIEW_TYPE_INCOME = 100
        const val VIEW_TYPE_EXPENSE = 101

        const val MAX_POOL_SIZE = 30
    }
}