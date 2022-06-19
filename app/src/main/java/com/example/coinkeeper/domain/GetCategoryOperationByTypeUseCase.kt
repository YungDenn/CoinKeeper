package com.example.coinkeeper.domain

import androidx.lifecycle.LiveData

class GetCategoryOperationByTypeUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun getCategoryOperationByType(typeOperation: Int): LiveData<List<CategoryOperation>> {
        return financeItemRepository.getCategoryOperationByType(typeOperation)
    }
}