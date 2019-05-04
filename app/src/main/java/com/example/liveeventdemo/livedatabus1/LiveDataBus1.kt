package com.example.liveeventdemo.livedatabus1

import android.arch.lifecycle.MutableLiveData

class LiveDataBus1 {
    companion object {
        val instance by lazy {
            LiveDataBus1()
        }
    }

    /**
     * 事件总线
     */
    private val busMap =  mutableMapOf<String,MutableLiveData<Any>>()

    /**
     * 获取LiveData
     */
    fun <T> with(messageKey:String):MutableLiveData<T>{
        if(!busMap.containsKey(messageKey)){
            busMap[messageKey]  = MutableLiveData()
        }
        return busMap[messageKey] as MutableLiveData<T>
    }
}