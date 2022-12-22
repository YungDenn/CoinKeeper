package com.example.coinkeeper.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "finance_items")
data class FinanceItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name: String,
    val comment: String,
    val sum: Int,
    val typeOperation: Int,
    val date: String,
    @ColumnInfo(name = "category_operations_id")
    val categoryOperationId: Int
)