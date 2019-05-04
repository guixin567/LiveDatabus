package com.example.liveeventdemo.livedatabus2

import android.arch.lifecycle.Observer

class ObserverWraper<T>:Observer<T> {
    override fun onChanged(t: T?){
    }
}