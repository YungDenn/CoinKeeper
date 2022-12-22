package com.example.coinkeeper.domain.usecases

import androidx.lifecycle.LiveData
import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.FinanceItem

class GetFinanceListByCategoryOperationUseCase(private val financeItemRepository: FinanceItemRepository){
    fun getFinanceListByCategoryOperation(typeCategory: Int): LiveData<List<FinanceItem>> {
        return financeItemRepository.getFinanceListByCategoryOperation(typeCategory)
    }
}