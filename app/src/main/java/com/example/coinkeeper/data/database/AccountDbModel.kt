package com.example.coinkeeper.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class AccountDbModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name: String,
    val sum: Int,
)