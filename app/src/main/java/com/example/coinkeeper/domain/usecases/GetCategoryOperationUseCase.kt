package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.CategoryOperation

class GetCategoryOperationUseCase(private val financeItemRepository: FinanceItemRepository) {
    suspend fun getCategoryOperation(categoryOperationId: Int): CategoryOperation {
        return financeItemRepository.getCategoryOperation(categoryOperationId)
    }
}
