package com.example.coinkeeper.domain

import androidx.lifecycle.LiveData

class GetAccountBalanceUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun getAccountBalance(id: Int): LiveData<Int> {
        return financeItemRepository.getAccountBalance(id)
    }
}