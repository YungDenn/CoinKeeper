package com.example.coinkeeper.domain.usecases

import androidx.lifecycle.LiveData
import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.FinanceItem

class GetFinanceListByTypeOperationUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun getFinanceItemListByTypeOperation(typeOperation: Int): LiveData<List<FinanceItem>> {
        return financeItemRepository.getFinanceItemListByTypeOperation(typeOperation)
    }
}