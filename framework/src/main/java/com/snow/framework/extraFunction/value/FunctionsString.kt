package com.snow.framework.extraFunction.value

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import com.google.gson.Gson
import com.snow.framework.BaseApplication

fun Any?.toJsonString(): String {
    if (this == null) return "null"
    return Gson().toJson(this)
}

/**
 * 复制字符串
 * */
fun String?.copyToClipBoard() {
    if (this == null) return
    val cm =
        BaseApplication.instance.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    // 创建普通字符型ClipData
    val mClipData = ClipData.newPlainText("Label", this)
    // 将ClipData内容放到系统剪贴板里
    cm.setPrimaryClip(mClipData)
}

/**
 * string list合并成string
 * */
fun List<String>?.join(separator: String): String {
    if (isNullOrEmpty()) return ""
    val sb = StringBuilder()
    forEachIndexed { index, s ->
        if (index > 0) sb.append(separator)
        sb.append(s)
    }
    return sb.toString()
}

/**
 * 从末尾开始切割字符串
 * */
fun String?.subStringFromLast(range: IntRange): String {
    if (isNullOrEmpty()) return ""
    if (range.first > lastIndex) return ""
    if (range.last > lastIndex) return substring(0..(lastIndex - range.first))
    return substring((lastIndex - range.last)..(lastIndex - range.first))
}

/**
 * 文字隐去部分，隐去部分如果不足2位则显示为2位
 * @param char 用来替代隐去部分的文字
 * @param range 所需的range
 * @param showOrHideRange range内的文字是隐藏还是显示
 * */
fun String?.hide(char: String, showOrHideRange: Boolean, range: String.() -> IntRange): String {
    if (this.isNullOrEmpty()) return "$char$char"
    val sb = StringBuilder()
    val start = range().first.fitRange(indices)
    val end = range().last.fitRange(start until length)
    if (showOrHideRange) {
        //显示range
        if (start > 0) {
            for (i in 0..(start - 1).min(2)) {
                sb.append(char)
            }
        }
        sb.append(substring(start..end))
        if (end < lastIndex) {
            for (i in (end + 1).max(lastIndex - 1)..lastIndex) {
                sb.append(char)
            }
        }
    } else {
        if (start > 0) {
            sb.append(substring(0 until start))
        }
        for (i in start..end.min(start + 1)) {
            sb.append(char)
        }
        if (end < lastIndex) {
            sb.append(substring((end + 1)..lastIndex))
        }
    }
    return sb.toString()
}
