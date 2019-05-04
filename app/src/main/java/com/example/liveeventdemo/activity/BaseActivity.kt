package com.example.liveeventdemo.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(initLayoutResID())
        initObserve()
        initListener()
    }

    /**
     * liveData订阅的事件
     */
    protected open fun initObserve() {}

    /**
     * 事件监听
     */
    protected open fun initListener() {}

    /**
     * 视图布局
     */
    abstract fun initLayoutResID(): Int


}