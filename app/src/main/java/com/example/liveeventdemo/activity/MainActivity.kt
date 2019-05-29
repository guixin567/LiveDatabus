package com.example.liveeventdemo.activity

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import com.example.liveeventdemo.R
import com.example.liveeventdemo.livedatabus1.VersionFirstActivity
import com.example.liveeventdemo.livedatabus2.VersionSecondActivity
import com.example.liveeventdemo.util.routeTo
import com.example.liveeventdemo.util.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private val mTestLiveData = MutableLiveData<String>()

    override fun initLayoutResID() = R.layout.activity_main

    override fun initListener() {
        MutableLiveData<String>()
        //源码
        bt_source.setOnClickListener {
            mTestLiveData.value = "发布消息"
            mTestLiveData.postValue("post")
        }

        //版本一
        bt_version1.setOnClickListener {
            routeTo<VersionFirstActivity>()
        }

        //版本二
        bt_version2.setOnClickListener {
            routeTo<VersionSecondActivity>()
        }

        //版本三
        bt_version3.setOnClickListener {
            routeTo<VersionFirstActivity>()
        }
    }

    override fun initObserve() {
        super.initObserve()
        mTestLiveData.observe(this, Observer {
            showToast("LiveData事件订阅---$it")
        })
    }
}
