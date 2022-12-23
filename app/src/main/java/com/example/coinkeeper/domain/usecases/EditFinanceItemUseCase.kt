package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.FinanceItem
import javax.inject.Inject

class EditFinanceItemUseCase @Inject constructor(private val financeItemRepository: FinanceItemRepository) {
    suspend fun editItem(financeItem: FinanceItem){
        financeItemRepository.editItem(financeItem)
    }
}