package com.snow.framework.extraFunction.value

import android.annotation.SuppressLint
import com.snow.framework.extraFunction.value.FunctionsTime.formatDay
import com.snow.framework.extraFunction.value.FunctionsTime.formatDefault
import com.snow.framework.extraFunction.value.FunctionsTime.formatEmpty
import com.snow.framework.extraFunction.value.FunctionsTime.formatMin
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.pow


/**
 * 全局用自定义方法 时间部分

 */

/**
 * 现在的时间戳
 * */

val nowTimeStamp: Long
    get() {
        return System.currentTimeMillis()
    }

val nowDayOfWeek: Int
    get() {
        //这里是按照周日为1返回的 于是-1
        var result = Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1
        //如果周日则为0，于是赋值为7
        if (result == 0) result = 7
        return when {
            result > 7 -> 7
            result < 1 -> 1
            else -> result
        }
    }

/**
 * Date 转 DateString
 */
@SuppressLint("SimpleDateFormat")
fun Date?.timeString(format: String = formatDefault(), default: String = formatEmpty()): String {
    if (this == null) return default
    val sdf = SimpleDateFormat(format)
    return sdf.format(this)
}

/**
 * timestamp 转 DateString
 */
@SuppressLint("SimpleDateFormat")
fun Long?.timeString(format: String = formatDefault(), default: String = formatEmpty()): String {
    if (this == null) return default
    if (this <= 0) return default
    val length = this.toString().length
    val time = if (length < 13) this * (10.0.pow(13 - length)).toLong() else this
    val sdf = SimpleDateFormat(format)
    return sdf.format(time)
}

/**
 * timestamp 转 DateString
 */
@SuppressLint("SimpleDateFormat")
fun String?.timeString(format: String = formatDefault(), default: String = formatEmpty()): String {
    return this?.toLong().timeString(format, default)
}

/**
 * Long 时间戳转 yyyy-MM-dd HH:mm:ss（默认格式）
 */
@SuppressLint("SimpleDateFormat")
fun Long?.simpleDayString(default: String = formatEmpty()): String {
    if (this == null) return default
    if (this <= 0) return default
    val sdfDay = SimpleDateFormat(formatDay())
    return sdfDay.format(this)
}

/**
 * Long 时间戳转 yyyy-MM-dd HH:mm:ss（默认格式）
 */
@SuppressLint("SimpleDateFormat")
fun Long?.simpleMinString(default: String = formatEmpty()): String {
    if (this == null) return default
    if (this <= 0) return default
    val sdfMin = SimpleDateFormat(formatMin())
    return sdfMin.format(this)
}

/**
 * 计算日期差距
 * this - other，主体时间越靠后值越大
 * */

fun Long?.dayDiff(other: Long?): Int {
    this ?: return 0
    other ?: return 0

    val time1Day = floor((this - FunctionsTime.timeContrast) / (1000f * 60f * 60f * 24f)).toInt()
    val time2Day = floor((other - FunctionsTime.timeContrast) / (1000f * 60f * 60f * 24f)).toInt()

    return time1Day - time2Day
}

object FunctionsTime {
    val timeContrast by lazy {
        Calendar.getInstance().let {
            it.set(2000, 0, 1, 0, 0, 0)
            it.timeInMillis
        }
    }
    var formatDay = { "yy-MM-dd" }
    var formatMin = { "HH:mm" }
    var formatDefault = { "yyyy-MM-dd HH:mm:ss" }
    var formatEmpty = { "--" }
}