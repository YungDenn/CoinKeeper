package com.example.coinkeeper.domain.usecases

import androidx.lifecycle.LiveData
import com.example.coinkeeper.domain.repository.FinanceItemRepository
import com.example.coinkeeper.domain.entity.FinanceItem
import javax.inject.Inject

class GetFinanceItemListUseCase @Inject constructor(private val financeItemRepository: FinanceItemRepository){

    fun getFinanceList(): LiveData<List<FinanceItem>>{
        return financeItemRepository.getFinanceItemList()
    }
}