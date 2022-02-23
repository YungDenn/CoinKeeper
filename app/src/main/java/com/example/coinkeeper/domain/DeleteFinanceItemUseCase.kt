package com.example.coinkeeper.domain

class DeleteFinanceItemUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun deleteItem(financeItem: FinanceItem){
        financeItemRepository.deleteItem(financeItem)
    }
}