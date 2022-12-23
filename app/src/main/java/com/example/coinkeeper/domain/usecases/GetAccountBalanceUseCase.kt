package com.example.coinkeeper.domain.usecases

import androidx.lifecycle.LiveData
import com.example.coinkeeper.domain.repository.FinanceItemRepository
import javax.inject.Inject

class GetAccountBalanceUseCase @Inject constructor(private val financeItemRepository: FinanceItemRepository) {
    fun getAccountBalance(id: Int): LiveData<Int> {
        return financeItemRepository.getAccountBalance(id)
    }
}