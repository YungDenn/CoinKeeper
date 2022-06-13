package com.example.coinkeeper.domain

import androidx.lifecycle.LiveData

class GetFinanceListByCategoryOperationUseCase(private val financeItemRepository: FinanceItemRepository){
    fun getFinanceListByCategoryOperation(typeCategory: Int): LiveData<List<FinanceItem>> {
        return financeItemRepository.getFinanceListByCategoryOperation(typeCategory)
    }
}