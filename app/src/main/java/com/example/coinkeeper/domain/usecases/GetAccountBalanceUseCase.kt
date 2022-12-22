package com.example.coinkeeper.domain.usecases

import androidx.lifecycle.LiveData
import com.example.coinkeeper.domain.repository.FinanceItemRepository

class GetAccountBalanceUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun getAccountBalance(id: Int): LiveData<Int> {
        return financeItemRepository.getAccountBalance(id)
    }
}