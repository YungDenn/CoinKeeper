package com.example.coinkeeper.domain

class UpdateAccountBalance(private val financeItemRepository: FinanceItemRepository) {
    suspend fun updateBalance(id: Int, sum: Int){
        financeItemRepository.updateAccountBalance(id, sum)
    }
}