package com.example.liveeventdemo.livedatabus2

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer

class LiveDataBus2 {
    companion object {
        val instance by lazy {
            LiveDataBus2()
        }
    }
    private val busMap = mutableMapOf<String, DistinctLiveData<Any>>()

    fun<T> with(messageKey: String): DistinctLiveData<T> {
        if(!busMap.containsKey(messageKey)){
            busMap[messageKey] = DistinctLiveData()
        }

        return busMap[messageKey] as DistinctLiveData<T>
    }

    /**
     * 发送默认的消息
     */
    private fun withDefaultMessage(messageKey: String):DistinctLiveData<Any>{
        return with(messageKey)
    }

    /**
     * 通过发送一个空串，伪装成不发送消息
     */
    fun withDefault(messageKey: String){
        withDefaultMessage(messageKey).setValue("")
    }

    /**
     * 默认消息，接收的方法
     */
    fun withDefault(messageKey: String,owner: LifecycleOwner,block:()->Unit){
        withDefaultMessage(messageKey).observe(owner, Observer {
            block.invoke()
        })
    }
}