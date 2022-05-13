package com.example.coinkeeper.domain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

interface FinanceItemRepository {

    fun addItem(financeItem: FinanceItem)

    fun deleteItem(financeItem: FinanceItem)//suspend

    fun editItem(financeItem: FinanceItem)//suspend

    fun getFinanceItem(financeItemId: Int): FinanceItem//suspend

    fun getFinanceItemList(): LiveData<List<FinanceItem>>

    fun getFinanceBalance(): MutableLiveData<Int>

    fun addTypeCategory(categoryOperation: CategoryOperation)

    fun getCategoryOperationsList(): LiveData<List<CategoryOperation>>

    fun getCategoryOperation(categoryOperationId: Int): CategoryOperation

}