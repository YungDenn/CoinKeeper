package com.example.coinkeeper.domain

class AddFinanceItemUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun addItem(financeItem: FinanceItem){//todo suspend
        financeItemRepository.addItem(financeItem)
    }
}