package com.example.coinkeeper.domain

data class CategoryOperation(
    var id: Int = ID_Operation,
    val name: String,
    val image_id: Int,
    val typeOperation: Int
){
    companion object{
        const val ID_Operation = -1
    }
}