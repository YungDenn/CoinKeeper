package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.FinanceItem
import javax.inject.Inject

class AddFinanceItemUseCase @Inject constructor(private val financeItemRepository: FinanceItemRepository) {
    suspend fun addItem(financeItem: FinanceItem){
        financeItemRepository.addItem(financeItem)
    }
}