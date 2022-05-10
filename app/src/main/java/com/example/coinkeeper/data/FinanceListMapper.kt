package com.example.coinkeeper.data

import com.example.coinkeeper.domain.FinanceItem

class FinanceListMapper {

    fun mapEntityToDbModel(financeItem: FinanceItem) = FinanceItemDbModel(
        id = financeItem.id,
        name = financeItem.name,
        sum = financeItem.sum,
        comment = financeItem.comment,
        typeOperation = financeItem.typeOperation
    )

    fun mapDbModelToEntity(financeItemDbModel: FinanceItemDbModel) = FinanceItem(
        id = financeItemDbModel.id,
        name = financeItemDbModel.name,
        sum = financeItemDbModel.sum,
        comment = financeItemDbModel.comment,
        typeOperation = financeItemDbModel.typeOperation
    )

    fun mapListDbModelToListEntity(list: List<FinanceItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

}