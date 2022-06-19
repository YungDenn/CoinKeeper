package com.example.coinkeeper.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.coinkeeper.domain.FinanceItem

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

    @Query("DELETE FROM finance_items")
    suspend fun deleteAll()

    //CategoryOperation

    @Query("SELECT * FROM category_operations")
    fun getCategoryOperationList(): LiveData<List<CategoryOperationDbModel>>

    @Insert (onConflict =  OnConflictStrategy.REPLACE)
    suspend fun addCategoryOperation(categoryOperationDbModel: CategoryOperationDbModel)

    @Query("SELECT * FROM category_operations WHERE id=:categoryOperationId LIMIT 1")
    suspend fun getCategoryOperation(categoryOperationId: Int): CategoryOperationDbModel

    @Query("SELECT * FROM category_operations WHERE typeOperation=:typeOperation")
    fun getCategoryOperationByType(typeOperation: Int): LiveData<List<CategoryOperationDbModel>>

    @Query("SELECT * FROM finance_items WHERE typeOperation=:typeOperation")
    fun getFinanceListByTypeOperation(typeOperation: Int): LiveData<List<FinanceItemDbModel>>

    @Query("SELECT * FROM finance_items WHERE category_operations_id=:typeOperation")
    fun getFinanceListByCategoryOperation(typeOperation: Int): LiveData<List<FinanceItemDbModel>>

    //Account

    @Insert (onConflict =  OnConflictStrategy.REPLACE)
    suspend fun addAccount(accountDbModel: AccountDbModel)

    @Query("UPDATE accounts set sum =:sumNew where id =:idAccount ")
    fun updateAccountBalance(idAccount: Int, sumNew: Int)


    @Query("SELECT sum FROM accounts WHERE id=:idAccount LIMIT 1")
    fun getAccountBalance(idAccount: Int): LiveData<Int>
}