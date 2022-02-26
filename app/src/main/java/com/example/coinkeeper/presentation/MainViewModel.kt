package com.example.coinkeeper.presentation

import androidx.lifecycle.ViewModel
import com.example.coinkeeper.data.FinanceItemListRepositoryImpl
import com.example.coinkeeper.domain.DeleteFinanceItemUseCase
import com.example.coinkeeper.domain.EditFinanceItemUseCase
import com.example.coinkeeper.domain.FinanceItem
import com.example.coinkeeper.domain.GetFinanceItemListUseCase

class MainViewModel: ViewModel() {

    private val repository = FinanceItemListRepositoryImpl

    private val getFinanceListUseCase = GetFinanceItemListUseCase(repository)
    private val deleteFinanceItemUseCase =DeleteFinanceItemUseCase(repository)
    private val editFinanceItemUseCase = EditFinanceItemUseCase(repository)

    val financeList = getFinanceListUseCase.getFinanceList()

    fun deleteFinanceItem(financeItem: FinanceItem){
        deleteFinanceItemUseCase.deleteItem(financeItem)
    }

}