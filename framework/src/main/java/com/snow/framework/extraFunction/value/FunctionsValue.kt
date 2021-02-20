package com.snow.framework.extraFunction.value

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import com.snow.framework.extraFunction.value.FunctionsValue.drawableToBitmap
import org.json.JSONObject
import java.io.File


/**
 * 全局用自定义方法 值部分
 */

fun Drawable.zoomDrawable(w: Int, h: Int = w): Drawable? {
    val width = intrinsicWidth
    val height = intrinsicHeight
    val oldbmp = drawableToBitmap(this)
    val matrix = Matrix()
    val scaleWidth = w.toFloat() / width
    val scaleHeight = h.toFloat() / height
    matrix.postScale(scaleWidth, scaleHeight)
    val newbmp = Bitmap.createBitmap(oldbmp, 0, 0, width, height,
        matrix, true)
    return BitmapDrawable(null, newbmp)
}

/**
 * 安全删除文件
 * */
fun File?.safeDelete() {
    if (this == null) return
    try {
        delete()
    } catch (ignore: Exception) {
    }
}

object FunctionsValue {
    fun drawableToBitmap(drawable: Drawable): Bitmap {
        val width = drawable.intrinsicWidth
        val height = drawable.intrinsicHeight
        val config =
            if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
        val bitmap = Bitmap.createBitmap(width, height, config)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, width, height)
        drawable.draw(canvas)
        return bitmap
    }
}

fun jsonOf(vararg pairs: Pair<String, Any?>?): JSONObject {
    val json = JSONObject()
    pairs.forEach {
        if (it?.first != null && it.second != null) {
            json.put(it.first, it.second)
        }
    }
    return json
}