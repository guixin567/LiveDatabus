package com.example.liveeventdemo.livedatabus1

import android.arch.lifecycle.Observer
import com.example.liveeventdemo.R
import com.example.liveeventdemo.activity.BaseActivity
import com.example.liveeventdemo.activity.SecondActivity
import com.example.liveeventdemo.bean.TestBean
import com.example.liveeventdemo.util.*
import kotlinx.android.synthetic.main.activity_version_first.*

class VersionFirstActivity : BaseActivity() {

    override fun initLayoutResID() = R.layout.activity_version_first

    /**
     * 点击初始化
     */
     override fun initListener() {
        //功能点击
        sw_function.setOnCheckedChangeListener { _, isChecked ->
            group_exception.isShow(isChecked)
            group_function.isShow(!isChecked)
        }

        bt_function_current.setOnClickListener {
            LiveDataBus1.instance.with<String>(current_page_message).value = "在当前页面发送消息"
        }

        bt_function_other.setOnClickListener {
            LiveDataBus1.instance.with<String>(other_page_message).value = "给其他页面发送消息"
            routeTo<SecondActivity>()
        }

        //类型转换异常
        bt_exception_class_cast.setOnClickListener {
            LiveDataBus1.instance.with<TestBean>(current_page_message).value = TestBean("测试")
        }

        //LiveData优化
        bt_exception_live_data.setOnClickListener {
            LiveDataBus1.instance.with<String>(live_data_optimization).value = "livedata优化测试"
            LiveDataBus1.instance.with<String>(live_data_optimization).value = "livedata优化测试"
            LiveDataBus1.instance.with<String>(live_data_optimization).value = "livedata优化测试"
        }


    }

    /**
     * 订阅初始化
     */
     override fun initObserve() {
        LiveDataBus1.instance.with<String>(current_page_message).observe(this, Observer {
            showToast(it)
        })
        LiveDataBus1.instance.with<String>(other_page_back_message).observe(this, Observer {
            showToast(it)
        })

        var time = 0
        LiveDataBus1.instance.with<String>(live_data_optimization).observe(this, Observer {
            time++
            showToast("$it $time")
        })

    }
}
