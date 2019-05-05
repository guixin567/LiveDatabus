package com.example.liveeventdemo.livedatabus2

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
}