package com.example.liveeventdemo.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.liveeventdemo.R
import com.example.liveeventdemo.livedatabus1.VersionFirstActivity
import com.example.liveeventdemo.livedatabus2.VersionSecondActivity
import com.example.liveeventdemo.util.routeTo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    override fun initLayoutResID() = R.layout.activity_main

    override fun initListener() {
        bt_version1.setOnClickListener {
            routeTo<VersionFirstActivity>()
        }

        bt_version2.setOnClickListener {
            routeTo<VersionSecondActivity>()
        }

        bt_version3.setOnClickListener {
            routeTo<VersionFirstActivity>()
        }
    }
}
