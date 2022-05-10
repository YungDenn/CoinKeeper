package com.example.coinkeeper.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FinanceListDao {

    @Query("SELECT * FROM finance_items")
    fun getFinanceList(): LiveData<List<FinanceItemDbModel>>

    @Insert (onConflict =  OnConflictStrategy.REPLACE)
    suspend fun addFinanceItem(financeItemDbModel: FinanceItemDbModel)

    @Query("DELETE FROM finance_items WHERE id =:financeItemId")
    suspend fun deleteFinanceItem(financeItemId: Int)

    @Query("SELECT * FROM finance_items WHERE id=:financeItemId LIMIT 1")
    suspend fun getFinanceItem(financeItemId: Int): FinanceItemDbModel
}