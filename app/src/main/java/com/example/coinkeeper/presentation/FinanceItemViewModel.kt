package com.example.coinkeeper.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.coinkeeper.data.FinanceItemListRepositoryImpl
import com.example.coinkeeper.domain.*
import java.lang.Exception

class FinanceItemViewModel: ViewModel() {
    private val repository = FinanceItemListRepositoryImpl

    private val getItemUseCase = GetFinanceItemUseCase(repository)
    private val addFinanceItemUseCase = AddFinanceItemUseCase(repository)
    private val editItemUseCase = EditFinanceItemUseCase(repository)


    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _financeItem = MutableLiveData<FinanceItem>()
    val financeItem: LiveData<FinanceItem>
        get() = _financeItem

    private val _closeScreen = MutableLiveData<Unit>()
    val closeScreen: LiveData<Unit>
        get() = _closeScreen

    private val _financeBalance = MutableLiveData<Int>()
    val financeBalance: LiveData<Int>
        get() = _financeBalance

    fun getFinanceItem(FinanceItemId: Int){
        val item = getItemUseCase.getItem(FinanceItemId)
        _financeItem.value = item
    }

    fun addFinanceItem(
        inputName: String?,
        inputCount: String?,
        inputComment: String?,
        inputCategory: String?) {

        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val comment = parseComment(inputComment)
        val category = parseCategory(inputCategory)
        val fieldsIsValid = validateInput(name, count)
        if (fieldsIsValid){
            val financeItem = FinanceItem(name, comment, count, category)
            addFinanceItemUseCase.addItem(financeItem)
            finishWork()
        }
        _financeBalance.value = count
    }


    fun editFinanceItem(inputName: String?, inputCount: String?, inputComment: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val comment = parseComment(inputComment)
        val fieldsIsValid = validateInput(name, count)
        if (fieldsIsValid){
            _financeItem.value?.let {// Если объект не равен null
                val item =it.copy(name = name, sum = count, comment = comment)
                editItemUseCase.editItem(item)
                finishWork()
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
    private fun parseCategory(inputCategory: String?) : Int {
        return try {
            inputCategory?.trim()?.toInt() ?: 0
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