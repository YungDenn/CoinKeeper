package com.example.coinkeeper.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coinkeeper.domain.entity.Account
import com.example.coinkeeper.domain.entity.CategoryOperation
import com.example.coinkeeper.domain.entity.FinanceItem

interface FinanceItemRepository {

    suspend fun addItem(financeItem: FinanceItem)

    suspend fun deleteItem(financeItem: FinanceItem)

    suspend fun editItem(financeItem: FinanceItem)

    suspend fun getFinanceItem(financeItemId: Int): FinanceItem

    fun getFinanceItemList(): LiveData<List<FinanceItem>>

    fun getFinanceBalance(): MutableLiveData<Int>

    suspend fun addCategoryOperation(categoryOperation: CategoryOperation)

    fun getCategoryOperationsList(): LiveData<List<CategoryOperation>>

    suspend fun getCategoryOperation(categoryOperationId: Int): CategoryOperation

    fun getCategoryOperationByType(typeOperation: Int): LiveData<List<CategoryOperation>>

    fun getFinanceItemListByTypeOperation(typeOperation: Int): LiveData<List<FinanceItem>>

    fun getFinanceListByCategoryOperation(typeCategory: Int): LiveData<List<FinanceItem>>

    suspend fun addAccount(account: Account)

    suspend fun updateAccountBalance (id: Int, sum: Int)

    fun getAccountBalance(id: Int): LiveData<Int>

}