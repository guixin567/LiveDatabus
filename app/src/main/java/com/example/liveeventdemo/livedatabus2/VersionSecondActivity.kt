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
        /**
         * 默认消息的接收
         */
        LiveDataBus2.instance.withDefault(version2_default_message,this){
            showToast("有些场景不需要传值，就可以使用默认的消息")
        }
    }

    override fun initListener() {
        //功能点击
        sw_function.setOnCheckedChangeListener { _, isChecked ->
            group_exception.isShow(isChecked)
            group_function.isShow(!isChecked)
        }

        /**
         * 默认不是粘性事件
         */
        bt_function_stick.setOnClickListener {
            LiveDataBus2.instance.with<String>(version2_no_stick).setValue("去掉默认的粘性事件")
            routeTo<SecondActivity>()
        }

        /**
         * 类型转换异常
         */
        bt_function_class_cast.setOnClickListener {
            LiveDataBus2.instance.with<TestBean>(version2_class_cast).setValue(TestBean("测试"))
        }

        /**
         * LiveData相同事件的优化
         */
        bt_function_live_data.setOnClickListener {
            LiveDataBus2.instance.with<String>(version2_live_data_optimization).setValue("测试LiveData优化")
            LiveDataBus2.instance.with<String>(version2_live_data_optimization).setValue("测试LiveData优化")
            LiveDataBus2.instance.with<String>(version2_live_data_optimization).setValue("测试LiveData优化")
        }

        /**
         * 兼容粘性事件
         */
        bt_function_compatible_stick.setOnClickListener {
            LiveDataBus2.instance.with<String>(version2_stick_compatible).setValue("测试粘性事件")
            routeTo<SecondActivity>()
        }

        /**
         * 默认消息的发送
         */
        bt_function_default.setOnClickListener {
            LiveDataBus2.instance.withGetDefault(version2_default_message)
        }
    }
}
