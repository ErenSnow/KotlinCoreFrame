package com.snow.framework.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import com.snow.framework.BaseApplication

/**
 * 屏幕数值相关类

 */
object ScreenUtils {

    private val designWidth by lazy {
        try {
            BaseApplication.instance.packageManager
                .getApplicationInfo(BaseApplication.instance.packageName, PackageManager.GET_META_DATA)
                .metaData.getInt("design_width_in_dp", 375)
        } catch (ignore: Exception) {
            375
        }
    }

    /**
     * 获取屏幕宽度（px）
     */
    private fun screenWidth(context: Context = BaseApplication.instance): Int {
        return if (context.resources.configuration.orientation == ORIENTATION_PORTRAIT) {
            context.resources.displayMetrics.widthPixels
        } else {
            context.resources.displayMetrics.heightPixels
        }
    }

    /**
     * 获取屏幕高度（px）
     */
    private fun screenHeight(context: Context = BaseApplication.instance): Int {
        return if (context.resources.configuration.orientation == ORIENTATION_PORTRAIT) {
            context.resources.displayMetrics.heightPixels
        } else {
            context.resources.displayMetrics.widthPixels
        }
    }

    /**
     * 获取屏幕高度（px）
     * 不会随着各种情况的变化而更新
     */
    val screenHeight: Int by lazy { screenHeight(BaseApplication.instance) }

    /**
     * 获取屏幕高度（px）
     * 不会随着各种情况的变化而更新
     */
    val screenWidth: Int by lazy { screenWidth(BaseApplication.instance) }

    /**
     * 设计图宽度转实际宽度
     */
    fun getRealSize(length: Int): Int {
        return (length.toDouble() * screenWidth.toDouble() / designWidth).toInt()
    }

    /**
     * 设计图宽度转实际宽度
     */
    fun getRealSize(length: Double): Int {
        return (length * screenWidth.toDouble() / designWidth).toInt()
    }

    /**
     * 设计图宽度转实际宽度
     */
    fun getRealSize(context: Context, length: Double): Int {
        return (length * screenWidth(context).toDouble() / designWidth).toInt()
    }

    /**
     * 设计图宽度转实际宽度
     */
    fun getRealSize(context: Context, length: Int): Int {
        return length * screenWidth(context) / designWidth
    }

    /**
     * 设计图宽度转实际宽度
     */
    fun getRealSizeFloat(context: Context, length: Int): Float {
        return getRealSizeFloat(context, length.toFloat())
    }

    /**
     * 设计图宽度转实际宽度
     */
    fun getRealSizeFloat(context: Context, length: Float): Float {
        return length * screenWidth(context).toFloat() / designWidth.toFloat()
    }
}