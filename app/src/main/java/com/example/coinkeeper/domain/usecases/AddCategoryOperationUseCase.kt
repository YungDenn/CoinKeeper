package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.CategoryOperation

class AddCategoryOperationUseCase(private val financeItemRepository: FinanceItemRepository) {
    suspend fun addCategoryOperation(categoryOperation: CategoryOperation){
        financeItemRepository.addCategoryOperation(categoryOperation)
    }
}