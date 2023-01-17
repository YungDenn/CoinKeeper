package com.example.coinkeeper.domain.usecases

import androidx.lifecycle.LiveData
import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.CategoryOperation
import javax.inject.Inject

class GetCategoryOperationByTypeUseCase @Inject constructor(private val financeItemRepository: FinanceItemRepository) {
    fun getCategoryOperationByType(typeOperation: Int): LiveData<List<CategoryOperation>> {
        return financeItemRepository.getCategoryOperationByType(typeOperation)
    }
}