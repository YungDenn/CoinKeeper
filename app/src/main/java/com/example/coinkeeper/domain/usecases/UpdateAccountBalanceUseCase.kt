package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import javax.inject.Inject

class UpdateAccountBalanceUseCase @Inject constructor(private val financeItemRepository: FinanceItemRepository) {
    suspend fun updateBalance(id: Int, sum: Int){
        financeItemRepository.updateAccountBalance(id, sum)
    }

}