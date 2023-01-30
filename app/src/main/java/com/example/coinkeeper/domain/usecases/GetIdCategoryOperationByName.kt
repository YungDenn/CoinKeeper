package com.example.coinkeeper.domain.usecases

import com.example.coinkeeper.domain.repository.FinanceItemRepository
import javax.inject.Inject

class GetIdCategoryOperationByName @Inject constructor(private val financeItemRepository: FinanceItemRepository){
    fun getIdCategoryOperationByName(name: String): Int{
        return financeItemRepository.getIdCategoryOperationByName(name)
    }
}