package com.example.coinkeeper.domain

class AddCategoryOperationUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun addTypeCategory(categoryOperation: CategoryOperation){
        financeItemRepository.addTypeCategory(categoryOperation)
    }
}