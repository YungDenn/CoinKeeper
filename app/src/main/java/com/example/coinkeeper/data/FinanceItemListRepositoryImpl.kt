package com.example.coinkeeper.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coinkeeper.domain.FinanceItem
import com.example.coinkeeper.domain.FinanceItemRepository
import java.lang.RuntimeException

object FinanceItemListRepositoryImpl: FinanceItemRepository {

    private val financeListLD = MutableLiveData<List<FinanceItem>>()
    private val financeList = sortedSetOf<FinanceItem>({ p0, p1 -> p0.id.compareTo(p1.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val item = FinanceItem("Test$i","test$i", i,1)
            addItem(item)
        }
    }

    override fun addItem(financeItem: FinanceItem) {
        if (financeItem.id == FinanceItem.UNDEFINED_ID) {
            financeItem.id = autoIncrementId++
        }
        financeList.add(financeItem)
        updateList()
    }

    override fun deleteItem(financeItem: FinanceItem) {
        financeList.remove(financeItem)
        updateList()
    }

    override fun editItem(financeItem: FinanceItem) {
        val oldElement = getFinanceItem(financeItem.id)
        financeList.remove(oldElement)
        addItem(financeItem)
    }

    override fun getFinanceItem(financeItemId: Int): FinanceItem {
        return financeList.find {
            it.id == financeItemId
        } ?: throw RuntimeException("Element with ID: $financeItemId not found")
    }

    override fun getFinanceItemList(): LiveData<List<FinanceItem>> {
        return financeListLD
    }

    private fun updateList(){
        financeListLD.value = financeList.toList()
    }

}