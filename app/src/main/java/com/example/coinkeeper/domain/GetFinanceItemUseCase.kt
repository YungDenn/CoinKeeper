package com.example.coinkeeper.domain

class GetFinanceItemUseCase(private val financeItemRepository: FinanceItemRepository) {
    suspend fun getItem(financeItemId: Int): FinanceItem{
        return financeItemRepository.getFinanceItem(financeItemId)
    }
}