package com.example.coinkeeper.data

import com.example.coinkeeper.domain.Account
import com.example.coinkeeper.domain.CategoryOperation
import com.example.coinkeeper.domain.FinanceItem

class FinanceListMapper {


    //Finance Item
    fun mapEntityToDbModel(financeItem: FinanceItem) = FinanceItemDbModel(
        id = financeItem.id,
        name = financeItem.name,
        sum = financeItem.sum,
        comment = financeItem.comment,
        typeOperation = financeItem.typeOperation,
        categoryOperationId = financeItem.categoryOperationId,
        date = financeItem.date
    )

    fun mapDbModelToEntity(financeItemDbModel: FinanceItemDbModel) = FinanceItem(
        id = financeItemDbModel.id,
        name = financeItemDbModel.name,
        sum = financeItemDbModel.sum,
        comment = financeItemDbModel.comment,
        typeOperation = financeItemDbModel.typeOperation,
        categoryOperationId = financeItemDbModel.categoryOperationId,
        date = financeItemDbModel.date
    )

    fun mapListDbModelToListEntity(list: List<FinanceItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }


    //CategoryOperations

    fun mapEntityToDbModelCategoryOperation(categoryOperation: CategoryOperation) = CategoryOperationDbModel(
        id = categoryOperation.id,
        name = categoryOperation.name,
        image_id = categoryOperation.image_id,
        typeOperation = categoryOperation.typeOperation
    )

    fun mapDbModelToEntityCategoryOperation(categoryOperationDbModel: CategoryOperationDbModel) = CategoryOperation(
        id = categoryOperationDbModel.id,
        name = categoryOperationDbModel.name,
        image_id = categoryOperationDbModel.image_id,
        typeOperation = categoryOperationDbModel.typeOperation

    )

    fun mapListDbModelToListEntityCategoryOperation(list: List<CategoryOperationDbModel>) = list.map {
        mapDbModelToEntityCategoryOperation(it)
    }

    fun mapEntityToDbModelAccount(account: Account) = AccountDbModel(
        id = account.id,
        name = account.name,
        sum = account.sum
    )

    fun mapDbModelToEntityAccount(accountDbModel: AccountDbModel) = Account(
        id = accountDbModel.id,
        name = accountDbModel.name,
        sum = accountDbModel.sum
    )

    fun mapListDbModelToListEntityAccount(list: List<AccountDbModel>) = list.map {
        mapDbModelToEntityAccount(it)
    }

}