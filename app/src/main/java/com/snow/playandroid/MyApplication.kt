package com.snow.playandroid

import com.snow.framework.BaseApplication

class MyApplication : BaseApplication() {

    companion object {
        val instance: MyApplication
            get() = BaseApplication.instance as MyApplication
    }

}