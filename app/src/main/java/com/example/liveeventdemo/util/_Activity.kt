package com.example.liveeventdemo.util

import android.app.Activity
import android.content.Intent
import android.widget.Toast

/**
 * Toast展示
 */
inline fun Activity.showToast(message:String?){
    message?.let {
        Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
    }
}

/**
 * Activity跳转
 */
inline fun <reified T:Activity> Activity.routeTo(){
    startActivity(Intent(this,T::class.java))
}