package com.example.coinkeeper.domain

import androidx.lifecycle.MutableLiveData

class GetFinanceBalanceUseCase(private val financeItemRepository: FinanceItemRepository) {
    fun getFinanceBalance(): MutableLiveData<Int>{
        return financeItemRepository.getFinanceBalance()
    }
}