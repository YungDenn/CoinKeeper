package com.example.coinkeeper.domain

data class FinanceItem(
    val name: String,
    val comment: String,
    val sum: Int,
    val typeOperation: Int,
    var id:Int = ID
) {
    companion object{
        const val ID = 0
    }
}