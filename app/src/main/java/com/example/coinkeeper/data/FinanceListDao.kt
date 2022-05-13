package com.example.coinkeeper.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FinanceListDao {

    //FinanceItem

    @Query("SELECT * FROM finance_items")
    fun getFinanceList(): LiveData<List<FinanceItemDbModel>>

    @Insert (onConflict =  OnConflictStrategy.REPLACE)
    suspend fun addFinanceItem(financeItemDbModel: FinanceItemDbModel)

    @Query("DELETE FROM finance_items WHERE id =:financeItemId")
    suspend fun deleteFinanceItem(financeItemId: Int)

    @Query("SELECT * FROM finance_items WHERE id=:financeItemId LIMIT 1")
    suspend fun getFinanceItem(financeItemId: Int): FinanceItemDbModel

    //CategoryOperation

    @Query("SELECT * FROM category_operations")
    fun getCategoryOperation(): LiveData<List<CategoryOperationDbModel>>

    @Insert (onConflict =  OnConflictStrategy.REPLACE)
    suspend fun addCategoryOperation(categoryOperationDbModel: CategoryOperationDbModel)

    @Query("SELECT * FROM category_operations WHERE id=:categoryOperationId LIMIT 1")
    suspend fun getCategoryOperation(categoryOperationId: Int): CategoryOperationDbModel

    @Query("SELECT * FROM category_operations WHERE typeOperation=:typeOperation")
    suspend fun getCategoryOperationByType(typeOperation: Int): CategoryOperationDbModel

    @Query("DELETE FROM finance_items")
    suspend fun deleteAll()
}