package com.example.coinkeeper.domain

class GetCategoryOperationUseCase(private val financeItemRepository: FinanceItemRepository) {
    suspend fun getCategoryOperation(categoryOperationId: Int): CategoryOperation{
        return financeItemRepository.getCategoryOperation(categoryOperationId)
    }
}
