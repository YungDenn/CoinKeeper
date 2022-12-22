package com.example.coinkeeper.presentation.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.coinkeeper.data.repository.FinanceItemListRepositoryImpl
import com.example.coinkeeper.domain.entity.Account
import com.example.coinkeeper.domain.entity.CategoryOperation
import com.example.coinkeeper.domain.entity.FinanceItem
import com.example.coinkeeper.domain.usecases.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = FinanceItemListRepositoryImpl(application)

    private val getFinanceListUseCase = GetFinanceItemListUseCase(repository)
    private val deleteFinanceItemUseCase = DeleteFinanceItemUseCase(repository)
    private val getFinanceBalanceUseCase = GetFinanceBalanceUseCase(repository)
    private val addFinanceItemUseCase = AddFinanceItemUseCase(repository)
    private val addCategoryOperationUseCase = AddCategoryOperationUseCase(repository)
    private val getFinanceListByTypeOperationUseCase =
        GetFinanceListByTypeOperationUseCase(repository)
    private val getCategoryOperationByTypeUseCase = GetCategoryOperationByTypeUseCase(repository)
    private val getFinanceListByCategoryOperationUseCase =
        GetFinanceListByCategoryOperationUseCase(repository)
    private val getAccountBalanceUseCase = GetAccountBalanceUseCase(repository)
    private val addAccountUseCase = AddAccountUseCase(repository)
    private val updateAccountBalanceUseCase = UpdateAccountBalanceUseCase(repository)

    val financeList = getFinanceListUseCase.getFinanceList()
    var balanceLD = getFinanceBalanceUseCase.getFinanceBalance()


    val accountBalance = getAccountBalanceUseCase.getAccountBalance(1)

    fun deleteFinanceItem(financeItem: FinanceItem) {
        viewModelScope.launch {
            deleteFinanceItemUseCase.deleteItem(financeItem)
        }
        ///updateBalance(1)
    }

    fun updateBalance(sum: Int, typeOperation: Int, delete: Boolean) {
        viewModelScope.launch {
            if (delete) {
                updateAccountBalanceUseCase.updateBalance(1, sum)
            }
            else{
                if (typeOperation == 1) {
                    updateAccountBalanceUseCase.updateBalance(1, sum)
                } else {
                    updateAccountBalanceUseCase.updateBalance(1, -sum)
                }
            }
        }
    }


    fun addFinanceItem(financeItem: FinanceItem) {
        viewModelScope.launch {
            addFinanceItemUseCase.addItem(financeItem)
            updateBalance(financeItem.sum, financeItem.typeOperation, false)
        }
    }

    fun addCategoryOperation(categoryOperation: CategoryOperation) {
        viewModelScope.launch {
            addCategoryOperationUseCase.addCategoryOperation(categoryOperation)
        }
    }

    fun getFinanceItemListByTypeOperation(typeOperation: Int): LiveData<List<FinanceItem>> {
        return getFinanceListByTypeOperationUseCase.getFinanceItemListByTypeOperation(typeOperation)
    }

    fun getCategoryOperationByType(typeOperation: Int): LiveData<List<CategoryOperation>> {
        return getCategoryOperationByTypeUseCase.getCategoryOperationByType(typeOperation)
    }

    fun getFinanceItemByCategoryOperation(idCategory: Int): LiveData<List<FinanceItem>> {
        return getFinanceListByCategoryOperationUseCase.getFinanceListByCategoryOperation(idCategory)
    }

    fun addAccount(account: Account) {
        viewModelScope.launch {
            addAccountUseCase.addAccount(account)
        }
    }
}

