package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.Account

class AddAccountUseCase(private val financeItemRepository: FinanceItemRepository) {
    suspend fun addAccount(account: Account){
        financeItemRepository.addAccount(account)
    }
}