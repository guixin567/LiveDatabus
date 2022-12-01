package com.example.liveeventdemo.livedatabus3

import android.arch.lifecycle.*
import java.util.concurrent.ConcurrentHashMap

class LiveDataBus3 private constructor() {
    private val busMap by lazy { ConcurrentHashMap<String, BusLiveData<*>>() }

    private fun  bus(channel: String) =
        busMap.getOrPut(channel) { BusLiveData<Any>() }


    /**
     * 发送事件 类型任意 但是得保证跟channel一一对应
     */
    fun  with(channel: String) = bus(channel) as BusLiveData


    class BusLiveData<T> : MutableLiveData<T>() {

        var mVersion = START_VERSION

        var mStickyEvent: T? = null

        /**
         * stick ：是否接收粘性事件，即事件先产生，后监听
         */
        fun observe(owner: LifecycleOwner, observer: Observer<T>, sticky: Boolean) {
            super.observe(owner, ObserverWrapper(observer, this, sticky))
        }

        override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
            observe(owner, observer, false)
        }

        /**
         * 发送普通事件
         */
        override fun setValue(value: T) {
            mVersion++
            super.setValue(value)
        }

        override fun postValue(value: T) {
            mVersion++
            super.postValue(value)
        }

        /**
         * 发送粘性事件
         */
        fun setValueSticky(value: T) {
            mStickyEvent = value
            setValue(value)
        }

        fun postValueSticky(value: T) {
            mStickyEvent = value
            postValue(value)
        }

        fun removeSticky() {
            mStickyEvent = null
        }
    }

    private class ObserverWrapper<T>(
        val observer: Observer<in T>,
        val liveData: BusLiveData<T>,
        val sticky: Boolean
    ) : Observer<T> {

        private var mLastVersion = liveData.mVersion

        override fun onChanged(t: T?) {

            if (mLastVersion >= liveData.mVersion) {
                if (sticky && liveData.mStickyEvent != null) {
                    observer.onChanged(liveData.mStickyEvent)
                }
                return
            }
            mLastVersion = liveData.mVersion

            observer.onChanged(t)
        }
    }

    companion object {

        private const val START_VERSION = -1

        @Volatile
        private var instance: LiveDataBus3? = null

        @JvmStatic
        fun getInstance() = instance ?: synchronized(this) {
            instance
                ?: LiveDataBus3().also { instance = it }
        }


        /**
         * 发送事件 类型任意 但是得保证跟channel一一对应
         */
        fun with(channel: String) = getInstance().with(channel)


    }

}