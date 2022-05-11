package com.example.coinkeeper.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_operations")
data class CategoryOperationDbModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name: String,
    val image_id: Int,
    val typeOperation: Int
)
