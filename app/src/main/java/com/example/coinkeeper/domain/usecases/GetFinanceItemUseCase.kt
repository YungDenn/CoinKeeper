package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.FinanceItem

class GetFinanceItemUseCase(private val financeItemRepository: FinanceItemRepository) {
    suspend fun getItem(financeItemId: Int): FinanceItem {
        return financeItemRepository.getFinanceItem(financeItemId)
    }
}