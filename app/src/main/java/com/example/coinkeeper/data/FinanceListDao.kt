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
    fun addFinanceList(financeItemDbModel: FinanceItemDbModel)

    @Query("DELETE FROM finance_items WHERE id =:financeItemId")
    fun deleteFinanceItem(financeItemId: Int)

    @Query("SELECT * FROM finance_items WHERE id=:financeItemId LIMIT 1")
    fun getFinanceItem(financeItemId: Int): FinanceItemDbModel
}