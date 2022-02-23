package com.example.coinkeeper.domain

import androidx.lifecycle.LiveData

class GetFinanceItemList(private val financeItemRepository: FinanceItemRepository){

    fun getFinanceList(): LiveData<List<FinanceItem>>{
        return financeItemRepository.getFinanceItemList()
    }
}