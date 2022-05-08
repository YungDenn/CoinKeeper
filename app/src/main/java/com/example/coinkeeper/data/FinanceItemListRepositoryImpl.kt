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

//    init {
//        for (i in 0 until 10) {
//            val item = FinanceItem("Test$i","test$i", i,1)
//            addItem(item)
//        }
//    }

    override fun addItem(financeItem: FinanceItem) {
        financeListDao.addFinanceList(mapper.mapEntityToDbModel(financeItem))
        updateBalance(financeItem.sum, financeItem.category)
    }

    private fun updateBalance(sum: Int, type: Int) {
        if (type == 1) {
            balance += sum
        } else {
            balance -= sum
        }

        balanceLD.value = balance
    }

    override fun getFinanceBalance(): MutableLiveData<Int> {
        return balanceLD
    }


    override fun deleteItem(financeItem: FinanceItem) {
        financeListDao.deleteFinanceItem(financeItem.id)
        updateBalance(financeItem.sum, 0)
    }

    override fun editItem(financeItem: FinanceItem) {
        financeListDao.addFinanceList(mapper.mapEntityToDbModel(financeItem))
        balance -= getFinanceItem(financeItem.id).sum
    }

    override fun getFinanceItem(financeItemId: Int): FinanceItem {
        val dbModel = financeListDao.getFinanceItem(financeItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getFinanceItemList(): LiveData<List<FinanceItem>> =
        Transformations.map(financeListDao.getFinanceList()) {
            mapper.mapListDbModelToListEntity(it)
        }
}