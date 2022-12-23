package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.FinanceItem
import javax.inject.Inject

class DeleteFinanceItemUseCase @Inject constructor(private val financeItemRepository: FinanceItemRepository) {
    suspend fun deleteItem(financeItem: FinanceItem){//Suspend
        financeItemRepository.deleteItem(financeItem)
    }
}