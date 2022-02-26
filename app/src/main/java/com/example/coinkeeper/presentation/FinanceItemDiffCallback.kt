package com.example.coinkeeper.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.coinkeeper.domain.FinanceItem

class FinanceItemDiffCallback: DiffUtil.ItemCallback<FinanceItem>() {
    override fun areItemsTheSame(oldItem: FinanceItem, newItem: FinanceItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FinanceItem, newItem: FinanceItem): Boolean {
        return oldItem == newItem
    }
}