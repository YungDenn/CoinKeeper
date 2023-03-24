package com.example.coinkeeper.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.example.coinkeeper.R
import com.example.coinkeeper.databinding.FinanceItemDateSeparatorBinding
import com.example.coinkeeper.databinding.FinanceItemExpenceBinding
import com.example.coinkeeper.databinding.FinanceItemIncomeBinding
import com.example.coinkeeper.domain.entity.FinanceItem
import com.example.coinkeeper.presentation.FinanceItemViewHolder
import java.text.SimpleDateFormat
import java.util.*

class FinanceListAdapter :
    ListAdapter<FinanceItem, FinanceItemViewHolder>(FinanceItemDiffCallback()) {

    var onFinanceItemClickListener: ((FinanceItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_INCOME -> R.layout.finance_item_income
            VIEW_TYPE_EXPENSE -> R.layout.finance_item_expence
            VIEW_TYPE_SEPARATOR -> R.layout.finance_item_date_separator
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
        val imageId = financeItem.imageId
        val binding = viewHolder.binding
        binding.root.setOnClickListener {
            onFinanceItemClickListener?.invoke(financeItem)
        }
        when (binding) {
            is FinanceItemExpenceBinding -> {
                binding.financeItem = financeItem
                binding.imageView.setImageResource(imageId)
            }
            is FinanceItemIncomeBinding -> {
                binding.financeItem = financeItem
                binding.imageView.setImageResource(imageId)
            }
            is FinanceItemDateSeparatorBinding -> {
                binding.date.text = when (financeItem.date) {
                    "today" -> "Today"
                    "yesterday" -> "Yesterday"
                    else -> financeItem.date.substring(0, 10)
                }
                binding.root.setOnClickListener(null)
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val prevItem = if (position > 0) getItem(position - 1) else null
//        return if (prevItem == null || item.date.substring(0, 10) != prevItem.date.substring(0, 10)
//        ) {
//            VIEW_TYPE_SEPARATOR
//        } else if (item.typeOperation == 1) {
//            VIEW_TYPE_INCOME
//        } else {
//            VIEW_TYPE_EXPENSE
//        }
        Log.d("FinanceItemAdapter", "item date: ${item.date}")
        Log.d("FinanceItemAdapter", "prevItem date: ${prevItem?.date ?: ""}")
        return when (item.typeOperation) {
            -1 -> VIEW_TYPE_SEPARATOR
            1 -> VIEW_TYPE_INCOME
            else -> VIEW_TYPE_EXPENSE
        }
    }

    override fun submitList(list: List<FinanceItem>?) {
        Log.d("FinanceListAdapter", "$list")
        val todayCalendar = Calendar.getInstance()
        val today = todayCalendar.time

        val yesterdayCalendar = Calendar.getInstance()
        yesterdayCalendar.add(Calendar.DAY_OF_YEAR, -1)
        val yesterday = yesterdayCalendar.time

        if (list == null) return

        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val sortedList = list.sortedByDescending { dateFormat.parse(it.date.substring(0, 10)) }

        val todayString = dateFormat.format(today)
        val yesterdayString = dateFormat.format(yesterday)

        val groupedAndSeparatedList = mutableListOf<FinanceItem>()
        var prevDate: String? = null

        for (item in sortedList) {
            val currDate = item.date.substring(0, 10)
            if (currDate != prevDate) {
                if (currDate == todayString) {
                    groupedAndSeparatedList.add(FinanceItem(-1, "", "", 0, -1, "today", 1, 0))
                } else if (currDate == yesterdayString) {
                    groupedAndSeparatedList.add(FinanceItem(-1, "", "", 0, -1, "yesterday", 1, 0))
                } else {
                    if (groupedAndSeparatedList.size > 1 && groupedAndSeparatedList[0].date == groupedAndSeparatedList[1].date) {
                        groupedAndSeparatedList.removeAt(0)
                    }
                    groupedAndSeparatedList.add(FinanceItem(-1, "", "", 0, -1, currDate, 1, 0))
                }
                prevDate = currDate
            }
            groupedAndSeparatedList.add(item)
        }
        if (groupedAndSeparatedList.size > 1 && groupedAndSeparatedList[0].date == groupedAndSeparatedList[1].date) {
            groupedAndSeparatedList.removeAt(0)
        }
        super.submitList(groupedAndSeparatedList.toList())
    }



    companion object {
        const val VIEW_TYPE_INCOME = 100
        const val VIEW_TYPE_EXPENSE = 101
        const val VIEW_TYPE_SEPARATOR = 102
        const val MAX_POOL_SIZE = 30
    }
}