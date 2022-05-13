package com.example.coinkeeper.domain

class EditFinanceItemUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun editItem(financeItem: FinanceItem){
        financeItemRepository.editItem(financeItem)
    }
}