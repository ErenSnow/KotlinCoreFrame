package com.snow.frame

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.snow.core.base.BaseActivity
import com.snow.framework.extraFunction.value.dp

class MainActivity : BaseActivity() {

    override fun initView() {
    }

    override fun initData() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    fun View.setRoundRectBg(color: Int = Color.WHITE, cornerRadius: Float = 15.dp.toFloat()) {
        background = GradientDrawable().apply {
            setColor(color)
            setCornerRadius(cornerRadius)
        }
    }
}
