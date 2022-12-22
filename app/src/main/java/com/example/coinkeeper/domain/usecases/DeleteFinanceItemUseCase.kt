package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.FinanceItem

class DeleteFinanceItemUseCase(private val financeItemRepository: FinanceItemRepository) {
    suspend fun deleteItem(financeItem: FinanceItem){//Suspend
        financeItemRepository.deleteItem(financeItem)
    }
}