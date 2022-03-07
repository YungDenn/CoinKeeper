package com.example.coinkeeper.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coinkeeper.data.FinanceItemListRepositoryImpl
import com.example.coinkeeper.domain.*

class MainViewModel: ViewModel() {

    private val repository = FinanceItemListRepositoryImpl

    private val getFinanceListUseCase = GetFinanceItemListUseCase(repository)
    private val deleteFinanceItemUseCase = DeleteFinanceItemUseCase(repository)
    private val getFinanceBalanceUseCase = GetFinanceBalanceUseCase(repository)
    //private val editFinanceItemUseCase = EditFinanceItemUseCase(repository)

    val financeList = getFinanceListUseCase.getFinanceList()
    val balanceLD = getFinanceBalanceUseCase.getFinanceBalance()

    fun deleteFinanceItem(financeItem: FinanceItem){
        deleteFinanceItemUseCase.deleteItem(financeItem)
    }


}