package com.example.coinkeeper.data

import com.example.coinkeeper.domain.FinanceItem

class FinanceListMapper {

    fun mapEntityToDbModel(financeItem: FinanceItem) = FinanceItemDbModel(
        id = financeItem.id,
        name = financeItem.name,
        sum = financeItem.sum,
        comment = financeItem.comment,
        category = financeItem.category
    )

    fun mapDbModelToEntity(financeItemDbModel: FinanceItemDbModel) = FinanceItem(
        id = financeItemDbModel.id,
        name = financeItemDbModel.name,
        sum = financeItemDbModel.sum,
        comment = financeItemDbModel.comment,
        category = financeItemDbModel.category
    )

    fun mapListDbModelToListEntity(list: List<FinanceItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }

}