package com.snow.framework.utils

import android.annotation.SuppressLint
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import com.snow.framework.BaseApplication
import java.lang.ref.WeakReference

object ToastUtil {

    @SuppressLint("ShowToast")
    private var resToastBuilder: (message: Int, length: Int) -> Toast = { message, length ->
        val toast = Toast.makeText(BaseApplication.instance, null, length)
        toast?.setText(message)
        toast
    }
    @SuppressLint("ShowToast")
    private var toastBuilder: (message: String, length: Int) -> Toast = { message, length ->
        val toast = Toast.makeText(BaseApplication.instance, null, length)
        toast?.setText(message)
        toast
    }

    fun setResToastBuilder(builder: (message: Int, length: Int) -> Toast) {
        resToastBuilder = builder
    }

    fun setStringToastBuilder(builder: (message: String, length: Int) -> Toast) {
        toastBuilder = builder
    }

    private var toast: WeakReference<Toast>? = null
    fun short(message: Int, toastBuilder: ((message: Int, length: Int) -> Toast) = resToastBuilder) {
        if (message == -1) return
        toast?.get()?.cancel()
        toastBuilder(message, LENGTH_SHORT).apply {
            toast = WeakReference(this)
            show()
        }
    }

    fun short(message: String, toastBuilder: ((message: String, length: Int) -> Toast) = this.toastBuilder) {
        if (message.isEmpty()) return
        toast?.get()?.cancel()
        toastBuilder(message, LENGTH_SHORT).apply {
            toast = WeakReference(this)
            show()
        }
    }

    fun long(message: Int, toastBuilder: ((message: Int, length: Int) -> Toast) = resToastBuilder) {
        if (message == -1) return
        toast?.get()?.cancel()
        toastBuilder(message, LENGTH_LONG).apply {
            toast = WeakReference(this)
            show()
        }
    }

    fun long(message: String, toastBuilder: ((message: String, length: Int) -> Toast) = this.toastBuilder) {
        toast?.get()?.cancel()
        toastBuilder(message, LENGTH_LONG).apply {
            toast = WeakReference(this)
            show()
        }
    }
}

fun Int.shortToast() {
    ToastUtil.short(this)
}

fun String?.shortToast() {
    this ?: return
    ToastUtil.short(this)
}