package com.example.coinkeeper.domain.usecases

import androidx.lifecycle.LiveData
import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.CategoryOperation

class GetCategoryOperationByTypeUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun getCategoryOperationByType(typeOperation: Int): LiveData<List<CategoryOperation>> {
        return financeItemRepository.getCategoryOperationByType(typeOperation)
    }
}