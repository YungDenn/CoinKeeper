package com.example.coinkeeper.domain

class GetFinanceItemUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun getItem(financeItemId: Int): FinanceItem{
        return financeItemRepository.getFinanceItem(financeItemId)
    }
}