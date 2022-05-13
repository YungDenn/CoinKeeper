package com.example.coinkeeper.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinkeeper.data.FinanceItemListRepositoryImpl
import com.example.coinkeeper.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application){

//    private val repository = FinanceItemListRepositoryImpl(application)
//
//    private val getFinanceListUseCase = GetFinanceItemListUseCase(repository)
//    private val deleteFinanceItemUseCase = DeleteFinanceItemUseCase(repository)
//    private val getFinanceBalanceUseCase = GetFinanceBalanceUseCase(repository)
//
//    private val scope = CoroutineScope(Dispatchers.Main)
//
//    val financeList = getFinanceListUseCase.getFinanceList()
//    //val balanceLD = getFinanceBalanceUseCase.getFinanceBalance()
//    val balanceLD = getFinanceBalanceUseCase.getFinanceBalance()
//
//    fun deleteFinanceItem(financeItem: FinanceItem){
//        //viewModelScope.launch {
//            deleteFinanceItemUseCase.deleteItem(financeItem)
//        //}
//    }
//    //fun getFinanceBalance(){
//        //viewModelScope.launch {
//            //balanceLD = getFinanceBalanceUseCase.getFinanceBalance()
//        //}
//    //}
    private val repository = FinanceItemListRepositoryImpl

    private val getFinanceListUseCase = GetFinanceItemListUseCase(repository)
    private val deleteFinanceItemUseCase = DeleteFinanceItemUseCase(repository)
    private val getFinanceBalanceUseCase = GetFinanceBalanceUseCase(repository)
    //private val editFinanceItemUseCase = EditFinanceItemUseCase(repository)

    val financeList = getFinanceListUseCase.getFinanceList()
    val balanceLD = getFinanceBalanceUseCase.getFinanceBalance()

    fun deleteFinanceItem(financeItem: FinanceItem){
        deleteFinanceItemUseCase.deleteItem(financeItem)
    }


}