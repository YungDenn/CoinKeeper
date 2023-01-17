package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import javax.inject.Inject

class UpdateAccountBalanceUseCase @Inject constructor(private val financeItemRepository: FinanceItemRepository) {
    suspend fun updateBalance(balanceId: Int, sum: Int){
        financeItemRepository.updateAccountBalance(balanceId, sum)
    }

}