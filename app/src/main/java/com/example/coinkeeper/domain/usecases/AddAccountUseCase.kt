package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.Account
import javax.inject.Inject

class AddAccountUseCase @Inject constructor(private val financeItemRepository: FinanceItemRepository) {
    suspend fun addAccount(account: Account){
        financeItemRepository.addAccount(account)
    }
}