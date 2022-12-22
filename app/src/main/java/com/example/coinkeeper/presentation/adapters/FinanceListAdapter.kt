package com.example.coinkeeper.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.coinkeeper.R
import com.example.coinkeeper.domain.entity.FinanceItem
import androidx.recyclerview.widget.ListAdapter
import com.example.coinkeeper.databinding.FinanceItemExpenceBinding
import com.example.coinkeeper.databinding.FinanceItemIncomeBinding
import com.example.coinkeeper.presentation.FinanceItemViewHolder


class FinanceListAdapter :
    ListAdapter<FinanceItem, FinanceItemViewHolder>(FinanceItemDiffCallback()) {

    var onFinanceItemClickListener: ((FinanceItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceItemViewHolder {

        val layout = when (viewType) {
            VIEW_TYPE_INCOME -> R.layout.finance_item_income
            VIEW_TYPE_EXPENSE -> R.layout.finance_item_expence
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return FinanceItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: FinanceItemViewHolder, position: Int) {
        val financeItem = getItem(position)
        val binding = viewHolder.binding
        binding.root.setOnClickListener {
            onFinanceItemClickListener?.invoke(financeItem)
        }
        when(binding){
            is FinanceItemExpenceBinding -> {
                binding.financeItem = financeItem
            }
            is FinanceItemIncomeBinding ->{
                binding.financeItem = financeItem
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.typeOperation == 1) {
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