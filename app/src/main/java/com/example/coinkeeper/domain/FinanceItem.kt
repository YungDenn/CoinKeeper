package com.example.coinkeeper.domain

data class FinanceItem(
    val name: String,
    val comment: String,
    val sum: Int,
    val category: Int,
    var id:Int = UNDEFINED_ID
) {
    companion object{
        const val UNDEFINED_ID = -1
    }
}