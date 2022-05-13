package com.example.coinkeeper.domain

data class FinanceItem(
    var id:Int = ID,
    val name: String,
    val comment: String,
    val sum: Int,
    val typeOperation: Int,
    val date: String,
    val categoryOperationId: Int
) {
    companion object{
        const val ID = -1
    }
}