package com.example.coinkeeper.domain

import androidx.lifecycle.LiveData

interface FinanceItemRepository {

    fun addItem(financeItem: FinanceItem)

    fun deleteItem(financeItem: FinanceItem)

    fun editItem(financeItem: FinanceItem)

    fun getFinanceItem(financeItemId: Int): FinanceItem

    fun getFinanceItemList(): LiveData<List<FinanceItem>>
}