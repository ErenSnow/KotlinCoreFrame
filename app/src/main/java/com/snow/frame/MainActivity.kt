package com.snow.frame

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.snow.framework.extraFunction.value.dp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun View.setRoundRectBg(color: Int = Color.WHITE, cornerRadius: Float = 15.dp.toFloat()) {
        background = GradientDrawable().apply {
            setColor(color)
            setCornerRadius(cornerRadius)
        }
    }
}
