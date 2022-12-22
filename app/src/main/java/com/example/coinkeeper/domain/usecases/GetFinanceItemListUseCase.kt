package com.example.coinkeeper.domain.usecases

import androidx.lifecycle.LiveData
import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.FinanceItem

class GetFinanceItemListUseCase(private val financeItemRepository: FinanceItemRepository){

    fun getFinanceList(): LiveData<List<FinanceItem>>{
        return financeItemRepository.getFinanceItemList()
    }
}