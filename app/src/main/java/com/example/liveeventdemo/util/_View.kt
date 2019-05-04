package com.example.liveeventdemo.util

import android.view.View

inline fun View.isShow(isShow: Boolean){
    this.visibility = if(isShow) View.VISIBLE else View.GONE
}