package com.example.coinkeeper.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.coinkeeper.domain.FinanceItem
import com.example.coinkeeper.domain.FinanceItemRepository
import java.lang.RuntimeException


class FinanceItemListRepositoryImpl(application: Application): FinanceItemRepository {

    private val financeListDao = AppDatabase.getInstance(application).financeListDao()
    private val mapper = FinanceListMapper()

    private val balanceLD = MutableLiveData<Int>()
    private var balance: Int = 0

    override suspend fun addItem(financeItem: FinanceItem) {
        financeListDao.addFinanceItem(mapper.mapEntityToDbModel(financeItem))
        updateBalance(financeItem.sum, financeItem.typeOperation)
        //TODO исправить баг с отображением баланса
    }

    private fun updateBalance(sum: Int, type: Int) {
//        if (type == 1) {
//            balance += sum
//        } else {
//            balance -= sum
//        }
        balance += sum
        balanceLD.value = balance
    }

    override fun getFinanceBalance(): MutableLiveData<Int> {
        return balanceLD
    }

    override suspend fun deleteItem(financeItem: FinanceItem) {
        financeListDao.deleteFinanceItem(financeItem.id)
        updateBalance(financeItem.sum, financeItem.typeOperation)
    }

    override suspend fun editItem(financeItem: FinanceItem) {
        financeListDao.addFinanceItem(mapper.mapEntityToDbModel(financeItem))
        balance -= getFinanceItem(financeItem.id).sum
    }

    override suspend fun getFinanceItem(financeItemId: Int): FinanceItem {
        val dbModel = financeListDao.getFinanceItem(financeItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getFinanceItemList(): LiveData<List<FinanceItem>> =
        Transformations.map(financeListDao.getFinanceList()) {
            mapper.mapListDbModelToListEntity(it)
        }
}