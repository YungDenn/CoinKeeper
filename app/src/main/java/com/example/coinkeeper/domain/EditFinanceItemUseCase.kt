package com.example.coinkeeper.domain

class EditFinanceItemUseCase(private val financeItemRepository: FinanceItemRepository) {
    suspend fun editItem(financeItem: FinanceItem){
        financeItemRepository.editItem(financeItem)
    }
}