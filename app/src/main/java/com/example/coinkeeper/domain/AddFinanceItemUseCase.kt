package com.example.coinkeeper.domain

class AddFinanceItemUseCase(private val financeItemRepository: FinanceItemRepository) {
    suspend fun addItem(financeItem: FinanceItem){
        financeItemRepository.addItem(financeItem)
    }
}