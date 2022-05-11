package com.example.coinkeeper.presentation


import android.app.Application
import androidx.lifecycle.*
import com.example.coinkeeper.data.FinanceItemListRepositoryImpl
import com.example.coinkeeper.domain.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class FinanceItemViewModel(application: Application): AndroidViewModel(application) {

    private val repository = FinanceItemListRepositoryImpl(application)

    private val getItemUseCase = GetFinanceItemUseCase(repository)
    private val addFinanceItemUseCase = AddFinanceItemUseCase(repository)
    private val editItemUseCase = EditFinanceItemUseCase(repository)

    private val scope = CoroutineScope(Dispatchers.Main)


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
        viewModelScope.launch {
            val item = getItemUseCase.getItem(FinanceItemId)
            _financeItem.value = item
        }
    }

    fun addFinanceItem(
        inputName: String?,
        inputCount: String?,
        inputComment: String?,
        inputTypeOperation: String?,
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
                finishWork()
            }

        }
        _financeBalance.value =+ sum
    }


    fun editFinanceItem(inputName: String?, inputCount: String?, inputComment: String?){
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val comment = parseComment(inputComment)
        val fieldsIsValid = validateInput(name, count)
        if (fieldsIsValid){
            _financeItem.value?.let {// Если объект не равен null
                viewModelScope.launch {
                    val item = it.copy(name = name, sum = count, comment = comment)
                    editItemUseCase.editItem(item)
                    finishWork()
                }
            }

        }
    }

    private fun updateBalance(){

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