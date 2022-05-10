package com.example.coinkeeper.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "finance_items")
data class FinanceItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name: String,
    val comment: String,
    val sum: Int,
    val typeOperation: Int
)