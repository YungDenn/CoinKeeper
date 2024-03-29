package com.example.coinkeeper.data.mapper

import com.example.coinkeeper.data.database.AccountDbModel
import com.example.coinkeeper.data.database.CategoryOperationDbModel
import com.example.coinkeeper.data.database.FinanceItemDbModel
import com.example.coinkeeper.domain.entity.Account
import com.example.coinkeeper.domain.entity.CategoryOperation
import com.example.coinkeeper.domain.entity.FinanceItem
import javax.inject.Inject

class FinanceListMapper @Inject constructor() {


    //Finance Item
    fun mapEntityToDbModel(financeItem: FinanceItem) = FinanceItemDbModel(
        id = financeItem.id,
        name = financeItem.name,
        sum = financeItem.sum,
        comment = financeItem.comment,
        typeOperation = financeItem.typeOperation,
        categoryOperationId = financeItem.categoryOperationId,
        date = financeItem.date,
        imageId = financeItem.imageId
    )

    fun mapDbModelToEntity(financeItemDbModel: FinanceItemDbModel) = FinanceItem(
        id = financeItemDbModel.id,
        name = financeItemDbModel.name,
        sum = financeItemDbModel.sum,
        comment = financeItemDbModel.comment,
        typeOperation = financeItemDbModel.typeOperation,
        categoryOperationId = financeItemDbModel.categoryOperationId,
        date = financeItemDbModel.date,
        imageId = financeItemDbModel.imageId
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