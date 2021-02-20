@file:Suppress("UNCHECKED_CAST")

package com.snow.framework.extraFunction

import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.*
import androidx.fragment.app.Fragment
import com.snow.framework.BaseApplication
import com.snow.framework.extraFunction.value.ExtraNumberFunctions
import java.io.Serializable


/**
 * 全局用自定义方法 Context部分

 */

fun Context.getIntent(cls: Class<out Context>, vararg pairs: Pair<String, Any?>): Intent {
    val intent = Intent(this, cls)
    pairs.forEach {
        val key = it.first
        val value = it.second
        when (value) {
            is Int -> intent.putExtra(key, value)
            is Byte -> intent.putExtra(key, value)
            is Char -> intent.putExtra(key, value)
            is Long -> intent.putExtra(key, value)
            is Float -> intent.putExtra(key, value)
            is Short -> intent.putExtra(key, value)
            is Double -> intent.putExtra(key, value)
            is Boolean -> intent.putExtra(key, value)
            is String? -> intent.putExtra(key, value)
            is Bundle? -> intent.putExtra(key, value)
            is IntArray? -> intent.putExtra(key, value)
            is ByteArray? -> intent.putExtra(key, value)
            is CharArray? -> intent.putExtra(key, value)
            is LongArray? -> intent.putExtra(key, value)
            is FloatArray? -> intent.putExtra(key, value)
            is Parcelable? -> intent.putExtra(key, value)
            is ShortArray? -> intent.putExtra(key, value)
            is DoubleArray? -> intent.putExtra(key, value)
            is BooleanArray? -> intent.putExtra(key, value)
            is CharSequence? -> intent.putExtra(key, value)
            is Serializable? -> intent.putExtra(key, value)
        }
    }
    return intent
}

fun Context.startActivity(cls: Class<out Activity>, vararg pairs: Pair<String, Any?>) {
    startActivity(getIntent(cls, *pairs))
}

fun Context.startForegroundService(cls: Class<out Service>, vararg pairs: Pair<String, Any?>) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        startForegroundService(getIntent(cls, *pairs))
    } else {
        startService(getIntent(cls, *pairs))
    }
}

fun Context.startService(cls: Class<out Service>, vararg pairs: Pair<String, Any?>) {
    startService(getIntent(cls, *pairs))
}

/**
 * 获取顶栏高度
 * */
fun getStatusBarHeight(): Int {
    return ExtraNumberFunctions.getInternalDimensionSize(BaseApplication.instance, "status_bar_height")
}

/**
 * 获取resources中的color
 */
@ColorInt
fun color(@ColorRes res: Int): Int {
    return BaseApplication.instance.resources.getColor(res)
}

/**
 * 获取resources中的color
 */
@ColorInt
fun Context.color(@ColorRes res: Int): Int {
    return resources.getColor(res)
}

/**
 * 获取resources中的drawable
 */
fun drawable(@DrawableRes res: Int): Drawable {
    return BaseApplication.instance.resources.getDrawable(res)
}

/**
 * 获取resources中的drawable
 */
fun dimen(@DimenRes res: Int): Float {
    return BaseApplication.instance.resources.getDimension(res)
}

/**
 * 获取resources中的drawable
 */
fun View?.dimen(@DimenRes res: Int): Float {
    this ?: return 0f
    return context.resources.getDimension(res)
}

/**
 * 获取resources中的drawable
 */
fun Context.dimen(@DimenRes res: Int): Float {
    return resources.getDimension(res)
}

/**
 * 获取Color String中的color
 * eg: "#ffffff"
 */
@ColorInt
fun color(color: String): Int {
    return Color.parseColor(color)
}

/**
 * 获取Resources中的String
 * */
fun resString(@StringRes res: Int): String {
    return try {
        BaseApplication.instance.getString(res)
    } catch (ignore: Exception) {
        ""
    }
}

/**
 * 获取Resources中的String，带拼接字符串功能
 * */
fun resString(@StringRes res: Int, vararg strings: String): String {
    return BaseApplication.instance.getString(res, *strings)
}

/**
 * 生成View
 * */
fun Context.inflate(@LayoutRes res: Int, root: ViewGroup? = null): View {
    return LayoutInflater.from(this).inflate(res, root)
}

/**
 * 生成View
 * */
fun Context.inflate(@LayoutRes res: Int, root: ViewGroup?, attachToRoot: Boolean): View {
    return LayoutInflater.from(this).inflate(res, root, attachToRoot)
}

/**
 * 生成View
 * */
fun ViewGroup.inflate(@LayoutRes res: Int, attachToRoot: Boolean): View {
    return LayoutInflater.from(this.context).inflate(res, this, attachToRoot)
}

/**
 * 使Fragment获取到的Context必定有值
 * 为空时返回Application的Context
 * */
fun Fragment.getSafeContext(): Context {
    return activity ?: BaseApplication.instance
}

fun Activity.intentString(key: String, default: String = ""): String {
    return intent.getStringExtra(key) ?: default
}

fun Activity.intentInt(key: String, default: Int = 0): Int {
    return intent.getIntExtra(key, default)
}

fun Activity.intentDouble(key: String, default: Double = 0.0): Double {
    return intent.getDoubleExtra(key, default)
}

fun Activity.intentFloat(key: String, default: Float = 0f): Float {
    return intent.getFloatExtra(key, default)
}

fun <T> Activity.intentSerializable(key: String): T? {
    return intent.getSerializableExtra(key) as? T
}

fun <T> Activity.intentSerializable(key: String, default: T): T {
    return intent.getSerializableExtra(key) as? T ?: default
}

fun Activity.intentBoolean(key: String, default: Boolean = false): Boolean {
    return intent.getBooleanExtra(key, default)
}

fun Activity.intentParcelable(key: String): Parcelable? {
    return intent.getParcelableExtra(key)
}