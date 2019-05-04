package com.example.liveeventdemo.activity


import android.arch.lifecycle.Observer
import com.example.liveeventdemo.R
import com.example.liveeventdemo.livedatabus1.LiveDataBus1
import com.example.liveeventdemo.livedatabus1.VersionFirstActivity
import com.example.liveeventdemo.util.other_page_back_message
import com.example.liveeventdemo.util.other_page_message
import com.example.liveeventdemo.util.routeTo
import com.example.liveeventdemo.util.showToast
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : BaseActivity() {

    override fun initLayoutResID()=R.layout.activity_second

    override fun initObserve() {
        LiveDataBus1.instance.with<String>(other_page_message).observe(this, Observer {
            showToast(it)
        })

    }

    override fun initListener() {
        super.initListener()
        bt_back_first.setOnClickListener {
            LiveDataBus1.instance.with<String>(other_page_back_message).value = "其他页面返回到首页发送消息"
            routeTo<VersionFirstActivity>()
        }
    }
}
