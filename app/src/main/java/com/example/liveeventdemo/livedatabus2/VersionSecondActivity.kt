package com.example.liveeventdemo.livedatabus2


import android.arch.lifecycle.Observer
import com.example.liveeventdemo.R
import com.example.liveeventdemo.activity.BaseActivity
import com.example.liveeventdemo.activity.SecondActivity
import com.example.liveeventdemo.bean.TestBean
import com.example.liveeventdemo.util.*
import kotlinx.android.synthetic.main.activity_version_second.*


class VersionSecondActivity : BaseActivity() {
    override fun initLayoutResID() = R.layout.activity_version_second

    override fun initObserve() {
        LiveDataBus2.instance.with<String>(version2_class_cast).observe(this, Observer {
            showToast(it)
        })
        /**
         * 优化后只会执行一次，而且就算是重复点击也不会再执行，除非发送的值改变了
         */
        LiveDataBus2.instance.with<String>(version2_live_data_optimization).observe(this, Observer {
            showToast("$it")
        })
    }

    override fun initListener() {
        //功能点击
        sw_function.setOnCheckedChangeListener { _, isChecked ->
            group_exception.isShow(isChecked)
            group_function.isShow(!isChecked)
        }

        bt_function_stick.setOnClickListener {
            LiveDataBus2.instance.with<String>(version2_no_stick).setValue("去掉默认的粘性事件")
            routeTo<SecondActivity>()
        }

        bt_function_class_cast.setOnClickListener {
            LiveDataBus2.instance.with<TestBean>(version2_class_cast).setValue(TestBean("测试"))
        }

        bt_function_live_data.setOnClickListener {
            LiveDataBus2.instance.with<String>(version2_live_data_optimization).setValue("测试LiveData优化")
            LiveDataBus2.instance.with<String>(version2_live_data_optimization).setValue("测试LiveData优化")
            LiveDataBus2.instance.with<String>(version2_live_data_optimization).setValue("测试LiveData优化")
        }

    }
}
