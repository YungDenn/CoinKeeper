package com.example.coinkeeper.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.coinkeeper.R
import com.example.coinkeeper.domain.CategoryOperation
import com.example.coinkeeper.domain.FinanceItem
import com.example.coinkeeper.domain.FinanceItemRepository

object FinanceItemListRepositoryImpl: FinanceItemRepository {

    private val financeListLD = MutableLiveData<List<FinanceItem>>()
    private val financeList = sortedSetOf<FinanceItem>({ p1, p0 -> p0.sum.compareTo(p1.sum) })

    private val categoryListLD = MutableLiveData<List<CategoryOperation>>()
    private val categoryList = sortedSetOf<CategoryOperation>({ p1, p0 -> p0.id.compareTo(p1.id) })

    private val balanceLD = MutableLiveData<Int>()
    private var balance: Int = 0


    private var autoIncrementIdForFI = 0
    private var autoIncrementIdForCO = 0


    init {

        val item = FinanceItem(0, "Поступление зараплаты", "За первый квартал", 50000, 1, "20-02-2022", 1)
        val item1 = FinanceItem(0, "Поступление дивидендов", "Газпром", 14000, 1, "20-02-2022", 1)
        val item2 = FinanceItem(0, "Выплата по страховке", "", 4000, 1, "20-02-2022", 1)
        val item3 = FinanceItem(0, "Поступление стипендии", "", 2400, 1, "20-02-2022", 1)
        val item4 = FinanceItem(0, "Покупки в магазине", "Рис, мука, молоко", 3000, 0, "20-02-2022", 1)
        val item5 = FinanceItem(0, "Выплата долга", "90% выплачено", 30000, 0, "20-02-2022", 1)
        val item6 = FinanceItem(0, "ЖКХ", "", 3000, 0, "20-02-2022", 1)

        addItem(item)
        addItem(item1)
        addItem(item2)
        addItem(item3)
        addItem(item4)
        addItem(item5)
        addItem(item6)

    }

    override fun addItem(financeItem: FinanceItem) {
        if (financeItem.id == FinanceItem.ID) {
            financeItem.id = autoIncrementIdForFI++
        }

        updateBalance(financeItem.sum, financeItem.typeOperation)
        financeList.add(financeItem)
        updateFinanceList()
    }

    override fun getCategoryOperation(categoryOperationId: Int): CategoryOperation {
        return categoryList.find {
            it.id == categoryOperationId
        } ?: throw RuntimeException("Element with ID: $categoryOperationId not found")
    }

    override fun getCategoryOperationsList(): LiveData<List<CategoryOperation>> {
        return categoryListLD
    }

    override fun addTypeCategory(categoryOperation: CategoryOperation){
        if(categoryOperation.id == CategoryOperation.ID_Operation){
            categoryOperation.id = autoIncrementIdForCO++
        }
        categoryList.add(categoryOperation)
        updateCategoryList()

    }

    private fun updateBalance(sum: Int, type : Int){
        if (type == 1){
            balance += sum
        }
        else{
            balance -= sum
        }
        balanceLD.value = balance
    }

    override fun getFinanceBalance(): MutableLiveData<Int> {
        return balanceLD
    }


    override fun deleteItem(financeItem: FinanceItem) {
        if(financeItem.typeOperation == 0){
            updateBalance(financeItem.sum, 1)
        }
        else{
            updateBalance(financeItem.sum, 0)
        }
        financeList.remove(financeItem)
        updateFinanceList()
    }

    override fun editItem(financeItem: FinanceItem) {
        val oldElement = getFinanceItem(financeItem.id)
        balance -= oldElement.sum
        financeList.remove(oldElement)
        addItem(financeItem)
    }

    override fun getFinanceItem(financeItemId: Int): FinanceItem {
        return financeList.find {
            it.id == financeItemId
        } ?: throw RuntimeException("Element with ID: $financeItemId not found")
    }

    override fun getFinanceItemList(): LiveData<List<FinanceItem>> {
        return financeListLD
    }

    private fun updateFinanceList(){
        financeListLD.value = financeList.toList()
    }

    private fun updateCategoryList(){
        categoryListLD.value = categoryList.toList()
    }

}

//private val financeListDao = AppDatabase.getInstance(application).financeListDao()
//private val mapper = FinanceListMapper()
//    init {
//        CoroutineScope(Dispatchers.IO).launch {
//            for (i in 0 until 10) {
//                val item = FinanceItem(0, "Test$i", "test", i + 100, 1, "20-02-2020", 1)
//                addItem(item)
//            }
//        }
//
//    }

//    override fun addItem(financeItem: FinanceItem) {
//        CoroutineScope(Dispatchers.IO).launch {
//            financeListDao.addFinanceItem(mapper.mapEntityToDbModel(financeItem))
//            updateBalance(financeItem.sum, financeItem.typeOperation)
//            //TODO исправить баг с отображением баланса
//        }
//    }


//    override suspend fun deleteItem(financeItem: FinanceItem) {
//        financeListDao.deleteFinanceItem(financeItem.id)
//        //updateBalance(financeItem.sum, financeItem.typeOperation)
//        balanceLD.value = -financeItem.sum
//    }
//
//    override suspend fun editItem(financeItem: FinanceItem) {
//        financeListDao.addFinanceItem(mapper.mapEntityToDbModel(financeItem))
//        balance -= getFinanceItem(financeItem.id).sum
//    }
//
//    override suspend fun getFinanceItem(financeItemId: Int): FinanceItem {
//        val dbModel = financeListDao.getFinanceItem(financeItemId)
//        return mapper.mapDbModelToEntity(dbModel)
//    }
//
//    override fun getFinanceItemList(): LiveData<List<FinanceItem>> =
//        Transformations.map(financeListDao.getFinanceList()) {
//            mapper.mapListDbModelToListEntity(it)
//        }
//}