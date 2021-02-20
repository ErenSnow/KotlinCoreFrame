package com.snow.framework.extraFunction.view

import android.app.Activity
import android.content.Context
import android.graphics.Typeface
import android.text.InputFilter
import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import com.snow.framework.BaseApplication
import com.snow.framework.extraFunction.view.ExtraTextViewFunctions.hideSoftKeyboard


/**
 * 全局用自定义方法 TextView部分

 */

/**
 * 以px做单位设置textSize
 * */
fun TextView?.setPxTextSize(size: Float) {
    if (this == null) return
    this.setTextSize(COMPLEX_UNIT_PX, size)
}

/**
 * 以res设置textSize
 * */
fun TextView?.textSize(@DimenRes size: Int) {
    if (this == null) return
    this.setTextSize(COMPLEX_UNIT_PX, context.resources.getDimension(size))
}

/**
 * 字体颜色
 * */
fun TextView?.textColor(@ColorRes color: Int) {
    if (this == null) return
    this.setTextColor(context.resources.getColor(color))
}

/**
 * 字体颜色，用来设置selector
 * */
fun TextView?.textColorSelector(@ColorRes color: Int) {
    if (this == null) return
    this.setTextColor(BaseApplication.instance.resources.getColorStateList(color))
}

fun TextView?.bold(isBold: Boolean) {
    if (this == null) return
    typeface = if (isBold) {
        Typeface.defaultFromStyle(Typeface.BOLD)
    } else {
        Typeface.defaultFromStyle(Typeface.NORMAL)
    }
}

fun EditText?.doInput() {
    if (this == null) return
    requestFocus()
    val inputManager =
        this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(this, 0)
}

/**
 * 添加EditText的InputFilter
 */
fun EditText?.addFilter(vararg filterList: InputFilter) {
    if (this == null) return
    filters = filters.plus(filterList)
}

/**
 * 去除EditText的InputFilter
 */
fun EditText?.removeFilter(vararg filterList: InputFilter) {
    if (this == null) return
    filters = arrayOf<InputFilter>().plus(filters.filter { !filterList.contains(it) })
}

/**
 * 添加回车时的处理
 * */
fun EditText?.onDone(listener: () -> Unit) {
    if (this == null) return
    setOnEditorActionListener { _, id, _ ->
        if (id == EditorInfo.IME_ACTION_SEARCH || id == EditorInfo.IME_ACTION_UNSPECIFIED || id == EditorInfo.IME_ACTION_DONE) {
            listener()
            hideKeyboard()
            return@setOnEditorActionListener true
        }
        return@setOnEditorActionListener false
    }
}

fun EditText?.hideKeyboard(){
    if (this == null) return
    hideSoftKeyboard(context, this)
    clearFocus()
}

object ExtraTextViewFunctions {
    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    fun hideSoftKeyboard(context: Context, view: View) {
        val inputMethodManager: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken,
            InputMethodManager.HIDE_NOT_ALWAYS)
    }
}
