package com.example.coinkeeper.domain

import androidx.lifecycle.LiveData

class GetFinanceItemListUseCase(private val financeItemRepository: FinanceItemRepository){

    fun getFinanceList(): LiveData<List<FinanceItem>>{
        return financeItemRepository.getFinanceItemList()
    }
}