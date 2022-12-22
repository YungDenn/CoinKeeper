package com.example.coinkeeper.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.coinkeeper.domain.entity.FinanceItem

class CombinedLiveData<CategoryOperations, FinanceItems, Int>
    (source1: LiveData<CategoryOperations>, source2: LiveData<FinanceItems>,
     private val combine:
         (data1: LiveData<List<CategoryOperations>>?, data2: LiveData<List<FinanceItem>>?)
     -> Int) : MediatorLiveData<Int>() {

    private var data1:  LiveData<List<CategoryOperations>>? = null
    private var data2: LiveData<List<FinanceItem>>? = null

    init {
        super.addSource(source1) {
            //data1 = it
            value = combine(data1, data2)
        }
        super.addSource(source2) {
            //data2 = it
            value = combine(data1, data2)
        }
    }

    override fun <S : Any?> addSource(source: LiveData<S>, onChanged: Observer<in S>) {
        throw UnsupportedOperationException()
    }

    override fun <T : Any?> removeSource(toRemove: LiveData<T>) {
        throw UnsupportedOperationException()
    }
}