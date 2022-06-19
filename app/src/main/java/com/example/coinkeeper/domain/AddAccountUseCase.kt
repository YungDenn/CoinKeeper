package com.example.coinkeeper.domain

class AddAccountUseCase(private val financeItemRepository: FinanceItemRepository) {
    suspend fun addAccount(account: Account){
        financeItemRepository.addAccount(account)
    }
}