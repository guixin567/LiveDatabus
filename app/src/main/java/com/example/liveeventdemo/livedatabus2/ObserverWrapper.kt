package com.example.liveeventdemo.livedatabus2

import android.arch.lifecycle.Observer

/**
 * Observer进行包装，防止类型转换异常
 */
class ObserverWrapper<T>(private val observer: Observer<T>):Observer<T> {
    override fun onChanged(t: T?){
        try {
            observer.onChanged(t)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}