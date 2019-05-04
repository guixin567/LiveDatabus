package com.example.liveeventdemo.util

import android.arch.lifecycle.MutableLiveData

open class DistinctLiveData<T>:MutableLiveData<T>() {
    private var currentValue:T? = null

    override fun setValue(value: T) {
        if(currentValue==value) return
        currentValue = value
        super.setValue(value)
    }

    override fun postValue(value: T) {
        if(currentValue==value) return
        currentValue = value
        super.postValue(value)
    }
}