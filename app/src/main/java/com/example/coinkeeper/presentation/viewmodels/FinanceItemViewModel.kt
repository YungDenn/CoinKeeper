package com.example.coinkeeper.presentation.viewmodels


import android.app.Application
import androidx.lifecycle.*
import com.example.coinkeeper.data.repository.FinanceItemListRepositoryImpl
import com.example.coinkeeper.domain.entity.CategoryOperation
import com.example.coinkeeper.domain.entity.FinanceItem
import com.example.coinkeeper.domain.usecases.*
import kotlinx.coroutines.launch
import java.lang.Exception

class FinanceItemViewModel(application: Application): AndroidViewModel(application) {

    private val repository = FinanceItemListRepositoryImpl(application)

    private val getItemUseCase = GetFinanceItemUseCase(repository)
    private val addFinanceItemUseCase = AddFinanceItemUseCase(repository)
    private val editItemUseCase = EditFinanceItemUseCase(repository)
    private val operationListUseCase = GetCategoryOperationListUseCase(repository)
    private val getCategoryOperationByTypeUseCase = GetCategoryOperationByTypeUseCase(repository)
    private val updateAccountBalanceUseCase = UpdateAccountBalanceUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _financeItem = MutableLiveData<FinanceItem>()
    val financeItem: LiveData<FinanceItem>
        get() = _financeItem

    private val _balance = MutableLiveData<Int>()
    val balance: LiveData<Int>
        get() = _balance


    private val _closeScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = _closeScreen


    fun getFinanceItem(FinanceItemId: Int){
        viewModelScope.launch {
            val item = getItemUseCase.getItem(FinanceItemId)
            _financeItem.value = item
        }
    }
    fun getCategoryOperationByType(typeOperation: Int): LiveData<List<CategoryOperation>> {
        return getCategoryOperationByTypeUseCase.getCategoryOperationByType(typeOperation)
    }


    fun addFinanceItem(
        inputName: String?,
        inputCount: String?,
        inputComment: String?,
        inputTypeOperation: String,
        inputDate: String?,
        inputCategoryId: String?,
    ) {

        val name = parseName(inputName)
        val sum = parseCount(inputCount)
        val comment = parseComment(inputComment)
        val typeOperation = parseTypeOperation(inputTypeOperation)
        val date = parseDate(inputDate)
        val categoryId = parseIdCategory(inputCategoryId)
        val fieldsIsValid = validateInput(name, sum)
        if (fieldsIsValid){
            viewModelScope.launch {
                val financeItem = FinanceItem(0, name,  comment, sum,  typeOperation, date, categoryId )
                addFinanceItemUseCase.addItem(financeItem)
                if (typeOperation == 1) {
                    updateAccountBalanceUseCase.updateBalance(1, sum)
                }
                else{
                    updateAccountBalanceUseCase.updateBalance(1, -sum)
                }
                finishWork()
            }
        }

    }


    fun editFinanceItem(inputName: String?, inputCount: String?, inputComment: String?, inputCategoryId: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val comment = parseComment(inputComment)
        val categoryOperationId = parseIdCategory(inputCategoryId)
        val fieldsIsValid = validateInput(name, count)
        if (fieldsIsValid){
            _financeItem.value?.let {// Если объект не равен null
                viewModelScope.launch {
                    val item = it.copy(name = name, sum = count, comment = comment, categoryOperationId = categoryOperationId)
                    editItemUseCase.editItem(item)
                    finishWork()
                }
            }

        }
    }

    private fun parseName(inputName: String?): String{
        return inputName?.trim() ?: ""
        // Если inputName не равен null, тогда обрезать пробелы (.trim())
        // иначе вернуть пустую строку ""
    }
    private fun parseComment(inputComment: String?): String{
        return inputComment?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?) : Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }
    private fun parseTypeOperation(inputType: String?) : Int {
        return try {
            inputType?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }
    private fun parseDate(inputDate: String?) : String {
        return inputDate?.trim() ?: ""
    }
    private fun parseIdCategory(inputCategoryId: String?) : Int {
        return try {
            inputCategoryId?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }
    private fun validateInput(name: String, count: Int): Boolean{
        var result = true
        if (name.isBlank()){
            _errorInputName.value = true
            result = false
        }
        if (count <= 0){
            _errorInputCount.value = true
            result = false
        }
        return  result
    }

    fun resetErrorInputName(){
        _errorInputName.value = false
    }

    fun resetErrorInputCount(){
        _errorInputCount.value = false
    }

    private fun finishWork(){
        _closeScreen.value = Unit
    }
}