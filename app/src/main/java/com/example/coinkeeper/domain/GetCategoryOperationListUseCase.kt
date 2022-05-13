package com.example.coinkeeper.domain

import androidx.lifecycle.LiveData

class GetCategoryOperationListUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun getCategoryOperationsList(): LiveData<List<CategoryOperation>> {
        return financeItemRepository.getCategoryOperationsList()
    }
}