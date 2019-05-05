package com.example.liveeventdemo.livedatabus2

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer

/**
 * LiveData的优化
 */
open class DistinctLiveData<T>:MutableLiveData<T>() {
    private var currentValue:T? = null

    override fun setValue(value: T) {
        if(currentValue==value) return
        currentValue = value
        super.setValue(value)
    }

    override fun postValue(value: T) {
        if(currentValue==value) return
        currentValue = value
        super.postValue(value)
    }

    fun observeStick(owner: LifecycleOwner, observer: Observer<T>){
        super.observe(owner,  ObserverWrapper(observer))
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        //防止类型转换异常
        val observerWrapper = ObserverWrapper(observer)
        super.observe(owner, observerWrapper)
        try {
            hook(observerWrapper)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * mLastVersion->observer:ObserverWrapper->iterator.next().getValue():Entry.Value->mObservers:SafeIterableMap
     * mVersion
     * 目标是将mVersion的值赋值给mLastVersion，这样就可以在调用onChange之前return，防止粘性事件
     */
    private fun hook(observer: Observer<T>) {
        //mObservers和mVersion都是LiveData的变量
        val liveDataClass = LiveData::class.java

        val mObserversField = liveDataClass.getDeclaredField("mObservers")
        mObserversField.isAccessible = true
        val mObservers = mObserversField.get(this)
        val mObserversClass = mObservers.javaClass

        //通过Map的get方法拿到Entry
        val getMethod = mObserversClass.getDeclaredMethod("get",Any::class.java)
        getMethod.isAccessible = true
        val mObserversEntry = getMethod.invoke(mObservers, observer)

        //通过Entry的value获取ObserverWrapper，再获取mLastVersion
        val mObserver = (mObserversEntry as Map.Entry<*, *>).value
        val wrapperObserverClass = mObserver!!.javaClass.superclass
        val mLastVersionField = wrapperObserverClass.getDeclaredField("mLastVersion")
        mLastVersionField.isAccessible = true

        //将mVersion的值赋值给mLastVersion
        val mVersion = liveDataClass.getDeclaredField("mVersion")
        mVersion.isAccessible = true

        mLastVersionField.set(mObserver,mVersion.get(this))



    }
}