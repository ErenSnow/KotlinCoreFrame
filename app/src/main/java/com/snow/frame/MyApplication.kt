package com.snow.frame

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.snow.framework.BaseApplication
import com.snow.framework.extraFunction.value.pt
import com.snow.framework.extraFunction.view.padding
import com.snow.framework.extraFunction.view.textColor
import com.snow.framework.extraFunction.view.textSize
import com.snow.framework.utils.ToastUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.jessyan.autosize.AutoSizeConfig
import me.jessyan.autosize.unit.Subunits

class MyApplication : BaseApplication() {

    companion object {
        val instance: MyApplication
            get() = BaseApplication.instance as MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        initAutoSize()

            initToast()
        GlobalScope.launch {
            delay(500)
        }
    }

    private fun initAutoSize() {
        AutoSizeConfig.getInstance()
            .setBaseOnWidth(true)
            .unitsManager
            .setSupportDP(false)
            .setSupportSP(false)
            .supportSubunits = Subunits.PT
    }

    @SuppressLint("ShowToast")
    private fun initToast() {
        ToastUtil.setResToastBuilder { message, length ->
            val toast = Toast(instance)
            // 设置Toast要显示的位置，居中，X轴偏移0个单位，Y轴偏移0个单位，
            toast.setGravity(Gravity.CENTER, 0, 0)
            // 设置显示时间
            toast.duration = length
            val view = TextView(instance)
            view.text = message.toString()
            view.setBackgroundResource(R.drawable.bg_toast)
            view.minHeight = 40.pt
            view.minWidth = 190.pt
            view.padding(start = 20.pt, end = 20.pt)
            view.gravity = Gravity.CENTER
            view.textSize(R.dimen.toastSize)
            view.textColor(R.color.white)
            toast.view = view
            return@setResToastBuilder toast
        }
        ToastUtil.setStringToastBuilder { message, length ->
            val toast = Toast(instance)
            // 设置Toast要显示的位置，居中，X轴偏移0个单位，Y轴偏移0个单位，
            toast.setGravity(Gravity.CENTER, 0, 0)
            // 设置显示时间
            toast.duration = length
            val view = TextView(instance)
            view.text = message
            view.setBackgroundResource(R.drawable.bg_toast)
            view.minHeight = 40.pt
            view.minWidth = 190.pt
            view.padding(start = 20.pt, end = 20.pt)
            view.gravity = Gravity.CENTER
            view.textSize(R.dimen.toastSize)
            view.textColor(R.color.white)
            toast.view = view
            return@setStringToastBuilder toast
        }
    }

}