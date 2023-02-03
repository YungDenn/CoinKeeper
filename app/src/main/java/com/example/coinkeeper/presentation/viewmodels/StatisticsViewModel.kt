package com.example.coinkeeper.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.coinkeeper.domain.entity.CategoryOperation
import com.example.coinkeeper.domain.entity.FinanceItem
import com.example.coinkeeper.domain.usecases.GetCategoryOperationByTypeUseCase
import com.example.coinkeeper.domain.usecases.GetFinanceItemListUseCase
import com.example.coinkeeper.domain.usecases.GetFinanceListByCategoryOperationUseCase
import com.example.coinkeeper.domain.usecases.GetFinanceListByTypeOperationUseCase
import javax.inject.Inject


class StatisticsViewModel @Inject constructor(
    private val getCategoryOperationByTypeUseCase: GetCategoryOperationByTypeUseCase,
    private val getFinanceListByCategoryOperationUseCase: GetFinanceListByCategoryOperationUseCase,
    private val getFinanceListUseCase: GetFinanceItemListUseCase,
    private val getFinanceListByTypeOperationUseCase: GetFinanceListByTypeOperationUseCase
) : ViewModel() {


    val financeList = getFinanceListUseCase.getFinanceList()


    fun getFinanceItemListByTypeOperation(typeOperation: Int): LiveData<List<FinanceItem>> {
        return getFinanceListByTypeOperationUseCase.getFinanceItemListByTypeOperation(typeOperation)
    }

    fun getFinanceItemByCategoryOperation(idCategory: Int): LiveData<List<FinanceItem>> {
        return getFinanceListByCategoryOperationUseCase.getFinanceListByCategoryOperation(idCategory)
    }

    fun getCategoryOperationByType(typeOperation: Int): LiveData<List<CategoryOperation>> {
        return getCategoryOperationByTypeUseCase.getCategoryOperationByType(typeOperation)
    }
}