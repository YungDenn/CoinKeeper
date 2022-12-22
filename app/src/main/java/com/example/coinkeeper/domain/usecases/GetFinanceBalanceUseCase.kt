package com.example.coinkeeper.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.example.coinkeeper.domain.repository.FinanceItemRepository

class GetFinanceBalanceUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun getFinanceBalance(): MutableLiveData<Int>{
        return financeItemRepository.getFinanceBalance()
    }
}