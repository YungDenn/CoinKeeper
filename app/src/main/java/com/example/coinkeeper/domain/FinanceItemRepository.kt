package com.example.coinkeeper.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface FinanceItemRepository {

    suspend fun addItem(financeItem: FinanceItem)

    suspend fun deleteItem(financeItem: FinanceItem)

    suspend fun editItem(financeItem: FinanceItem)

    suspend fun getFinanceItem(financeItemId: Int): FinanceItem

    fun getFinanceItemList(): LiveData<List<FinanceItem>>

    fun getFinanceBalance(): MutableLiveData<Int>

}