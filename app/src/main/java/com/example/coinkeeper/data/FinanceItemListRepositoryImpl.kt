package com.example.coinkeeper.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.coinkeeper.domain.CategoryOperation
import com.example.coinkeeper.domain.FinanceItem
import com.example.coinkeeper.domain.FinanceItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class FinanceItemListRepositoryImpl(application: Application) : FinanceItemRepository {

    private val financeListDao = AppDatabase.getInstance(application).financeListDao()
    private val mapper = FinanceListMapper()

    private val balanceLD = MutableLiveData<Int>()
    private var balance: Int = 0


    private fun updateBalance(sum: Int, type: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            if (type == 1) {
                balance += sum
            } else {
                balance -= sum
            }
            balanceLD.value = balance
        }
    }

    override fun getFinanceBalance(): MutableLiveData<Int> {
        return balanceLD
    }

    override suspend fun addCategoryOperation(categoryOperation: CategoryOperation) {
        CoroutineScope(Dispatchers.IO).launch {
            financeListDao.addCategoryOperation(
                mapper.mapEntityToDbModelCategoryOperation(categoryOperation)
            )
        }
    }

    override suspend fun getCategoryOperation(categoryOperationId: Int): CategoryOperation {
        val dbModel = financeListDao.getCategoryOperation(categoryOperationId)
        return mapper.mapDbModelToEntityCategoryOperation(dbModel)
    }

//    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            for (i in 0 until 3) {
//                val item = FinanceItem(0, "Test$i", "test", i + 100, 1, "20-02-2020", 1)
//                addItem(item)
//            }
//        }
//    }

    override suspend fun addItem(financeItem: FinanceItem) {
        CoroutineScope(Dispatchers.IO).launch {
            financeListDao.addFinanceItem(mapper.mapEntityToDbModel(financeItem))
            updateBalance(financeItem.sum, financeItem.typeOperation)
            //TODO исправить баг с отображением баланса
        }
    }


    override suspend fun deleteItem(financeItem: FinanceItem) {
        financeListDao.deleteFinanceItem(financeItem.id)
        updateBalance(financeItem.sum, financeItem.typeOperation)
        //balanceLD.value = -financeItem.sum
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

    override fun getCategoryOperationsList(): LiveData<List<CategoryOperation>> =
        Transformations.map(financeListDao.getCategoryOperationList()) {
            mapper.mapListDbModelToListEntityCategoryOperation(it)
        }

    override fun getCategoryOperationByType(typeOperation: Int): LiveData<List<CategoryOperation>> =
        Transformations.map(financeListDao.getCategoryOperationByType(typeOperation)) {
            mapper.mapListDbModelToListEntityCategoryOperation(it)
        }
}