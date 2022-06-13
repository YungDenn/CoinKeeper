package com.example.coinkeeper.domain

import androidx.lifecycle.LiveData

class GetFinanceListByTypeOperationUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun getFinanceItemListByTypeOperation(typeOperation: Int): LiveData<List<FinanceItem>> {
        return financeItemRepository.getFinanceItemListByTypeOperation(typeOperation)
    }
}