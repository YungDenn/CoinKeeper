package com.example.coinkeeper.domain

class AddCategoryOperationUseCase(private val financeItemRepository: FinanceItemRepository) {
    suspend fun addCategoryOperation(categoryOperation: CategoryOperation){
        financeItemRepository.addCategoryOperation(categoryOperation)
    }
}