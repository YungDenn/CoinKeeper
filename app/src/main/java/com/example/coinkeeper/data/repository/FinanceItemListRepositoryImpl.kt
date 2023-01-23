package com.example.coinkeeper.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.coinkeeper.data.database.FinanceListDao
import com.example.coinkeeper.data.mapper.FinanceListMapper
import com.example.coinkeeper.domain.entity.Account
import com.example.coinkeeper.domain.entity.CategoryOperation
import com.example.coinkeeper.domain.entity.FinanceItem
import com.example.coinkeeper.domain.repository.FinanceItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class FinanceItemListRepositoryImpl @Inject constructor(
    private val financeListDao: FinanceListDao,
    private val mapper: FinanceListMapper
) : FinanceItemRepository {

//    private val financeListDao = AppDatabase.getInstance(application).financeListDao()
//    private val mapper = FinanceListMapper()

    private val balanceLD = MutableLiveData<Int>()
    private var balance: Int = 0

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

    override suspend fun addItem(financeItem: FinanceItem) {
        CoroutineScope(Dispatchers.IO).launch {
            financeListDao.addFinanceItem(mapper.mapEntityToDbModel(financeItem))
            //updateBalance(financeItem.sum, financeItem.typeOperation)
            //TODO исправить баг с отображением баланса
        }
    }


    override suspend fun deleteItem(financeItem: FinanceItem) {
        financeListDao.deleteFinanceItem(financeItem.id)
        //updateBalance(financeItem.sum, financeItem.typeOperation)
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

    override fun getFinanceItemListByTypeOperation(typeOperation: Int): LiveData<List<FinanceItem>> =
        Transformations.map(financeListDao.getFinanceListByTypeOperation(typeOperation)) {
            mapper.mapListDbModelToListEntity(it)
        }

    override fun getFinanceListByCategoryOperation(typeCategory: Int): LiveData<List<FinanceItem>> =
        Transformations.map(financeListDao.getFinanceListByCategoryOperation(typeCategory)) {
            mapper.mapListDbModelToListEntity(it)
        }

    override suspend fun addAccount(account: Account) {
        CoroutineScope(Dispatchers.IO).launch {
            financeListDao.addAccount(mapper.mapEntityToDbModelAccount(account))
        }
    }

    override suspend fun updateAccountBalance(id: Int, sum: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            financeListDao.updateAccountBalance(id, sum)
        }
    }

    override fun getAccountBalance(id: Int): LiveData<Int> {
        return financeListDao.getAccountBalance(id)
    }
}