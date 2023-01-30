package com.example.coinkeeper.domain.entity

data class FinanceItem(
    var id:Int = ID,
    val name: String,
    val comment: String,
    val sum: Int,
    val typeOperation: Int,
    val date: String,
    val categoryOperationId: Int,
    val imageId: Int
) {
    companion object{
        const val ID = 0
    }
}