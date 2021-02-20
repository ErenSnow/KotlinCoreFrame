package com.snow.framework.extraFunction.view

import android.graphics.Paint
import android.view.Gravity
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.customview.widget.ViewDragHelper
import androidx.drawerlayout.widget.DrawerLayout
import com.snow.framework.BaseApplication
import com.snow.framework.extraFunction.value.min
import com.snow.framework.extraFunction.value.nowTimeStamp
import java.lang.reflect.Method


/**
 * 全局用自定义方法 View部分

 */

/**
 * 防止重复点击
 * 默认500ms
 */
fun View?.click(time: Long = 500L, click: (v: View) -> Unit) {
    if (this == null) return
    this.setOnClickListener(object : OnMultiClickListener(time, click) {})
}

/**
 * 防止重复点击
 */
fun View?.click(click: (v: View) -> Unit) {
    click(500L, click)
}

fun OnClickListener.clicks(vararg v: View, time: Long = 500L) {
    v.forEach {
        it.setOnClickListener(object : OnMultiClickListener(time) {
            override fun onMultiClick(v: View) {
                this@clicks.onClick(v)
            }
        })
    }
}

/**
 * 清空点击
 */
fun View?.clearClick() {
    if (this == null) return
    this.setOnClickListener(null)
    this.isClickable = false
}

/**
 * 判断是否可见
 */
fun View?.isVisible(): Boolean {
    if (this == null) return false
    return this.visibility == View.VISIBLE
}

/**
 * 显示view
 */
fun View?.visible() {
    if (this == null) return
    if (visibility == VISIBLE) return
    this.visibility = VISIBLE
}

/**
 * 不显示view
 */
fun View?.invisible() {
    if (this == null) return
    if (visibility == INVISIBLE) return
    this.visibility = INVISIBLE
}

/**
 * 隐藏view
 */
fun View?.gone() {
    if (this == null) return
    if (visibility == GONE) return
    this.visibility = GONE
}

/**
 * 有效化
 * */
fun View?.enable() {
    if (this == null) return
    if (isEnabled) return
    isEnabled = true
}

/**
 * 无效化
 * */
fun View?.disable() {
    if (this == null) return
    if (!isEnabled) return
    isEnabled = false
}

/**
 * 背景
 * */
fun View?.background(@DrawableRes bg: Int) {
    if (this == null) return
    this.setBackgroundResource(bg)
}

/**
 * 清除背景
 * */
fun View?.clearBackground() {
    if (this == null) return
    this.background = null
}

/**
 * 动画隐藏view
 */
fun View?.fade(time: Long = 500, cancelAnim: Boolean = true) {
    if (this == null) return
    if (!this.isVisible()) return
    if (time <= 0) {
        gone()
        return
    }
    if (cancelAnim) {
        animation?.setAnimationListener(null)
        animation?.cancel()
    } else if (animation != null) {
        if (animation.hasStarted() && !animation.hasEnded()) {
            return
        }
    }

    val anim = AlphaAnimation(1f, 0f)
    anim.fillAfter = false // 设置保持动画最后的状态
    anim.duration = time // 设置动画时间
    anim.interpolator = AccelerateInterpolator() // 设置插入器3
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) {
            gone()
        }

        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationRepeat(animation: Animation?) {}
    })
    startAnimation(anim)
}

/**
 * 动画显示view
 */
fun View?.appear(time: Long = 500, cancelAnim: Boolean = true) {
    if (this == null) return
    if (this.isVisible()) return
    if (time <= 0) {
        visible()
        return
    }
    if (cancelAnim) {
        animation?.setAnimationListener(null)
        animation?.cancel()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            animate()?.setUpdateListener(null)
        }
        animate()?.cancel()
    } else if (animation != null) {
        if (animation.hasStarted() && !animation.hasEnded()) {
            return
        }
    }

    visible()
    val anim = AlphaAnimation(0f, 1f)
    anim.fillAfter = false // 设置保持动画最后的状态
    anim.duration = time // 设置动画时间
    anim.interpolator = AccelerateInterpolator() // 设置插入器3
    anim.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationEnd(animation: Animation?) {
            visible()
        }

        override fun onAnimationStart(animation: Animation?) {}
        override fun onAnimationRepeat(animation: Animation?) {}
    })
    startAnimation(anim)
}

/**
 * 开启硬件加速
 */
fun View?.byHardwareAccelerate(paint: Paint? = Paint()) {
    if (this == null) return
    setLayerType(View.LAYER_TYPE_HARDWARE, paint)
}

/**
 * 关闭硬件加速
 */
fun View?.stopHardwareAccelerate() {
    if (this == null) return
    setLayerType(View.LAYER_TYPE_SOFTWARE, Paint())
}


/**
 * 设置margin，单位px
 */
fun View?.margin(start: Int? = null, top: Int? = null, end: Int? = null, bottom: Int? = null) {
    if (this == null) return
    val lp = layoutParams as? ViewGroup.MarginLayoutParams ?: return
    start?.let {
        lp.marginStart = it
        lp.leftMargin = it
    }
    top?.let { lp.topMargin = it }
    end?.let {
        lp.marginEnd = it
        lp.rightMargin = it
    }
    bottom?.let { lp.bottomMargin = it }
    layoutParams = lp
}

/**
 * 设置padding，单位px
 */
fun View?.padding(start: Int? = null, top: Int? = null, end: Int? = null, bottom: Int? = null) {
    if (this == null) return
    setPaddingRelative(
        start ?: paddingStart,
        top ?: paddingTop,
        end ?: paddingEnd,
        bottom ?: paddingBottom)
}

/**
 * 设置padding，单位px
 */
fun View?.paddingAll(padding: Int) {
    if (this == null) return
    setPaddingRelative(padding, padding, padding, padding)
}

/**
 * 调整view大小
 * @param width  可使用MATCH_PARENT和WRAP_CONTENT，传null或者不传为不变
 * @param height 可使用MATCH_PARENT和WRAP_CONTENT，传null或者不传为不变
 * */
fun View?.size(width: Int? = null, height: Int? = null) {
    if (this == null) return
    val lp = layoutParams
    height?.let {
        layoutParams?.height = it
    }
    width?.let {
        layoutParams?.width = it
    }
    layoutParams = lp ?: ViewGroup.LayoutParams(width ?: WRAP_CONTENT, height ?: WRAP_CONTENT)
}

/**
 * 在layout完毕之后进行计算处理
 * */
fun View?.doOnceAfterLayout(listener: () -> Unit) {
    if (this == null) return
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            listener()
        }
    })
}

/**
 * 设置layoutGravity，只对LinearLayout和FrameLayout有效，有需要则自行添加其他view
 * */
var View?.layoutGravity: Int
    get() {
        return when (this?.parent) {
            is LinearLayout -> {
                (this.layoutParams as LinearLayout.LayoutParams).gravity
            }
            is FrameLayout -> {
                (this.layoutParams as FrameLayout.LayoutParams).gravity
            }
            else -> {
                Gravity.NO_GRAVITY
            }
        }
    }
    set(value) {
        when (this?.parent) {
            is LinearLayout -> {
                val lp = this.layoutParams as LinearLayout.LayoutParams
                lp.gravity = value
                this.layoutParams = lp
            }
            is FrameLayout -> {
                val lp = this.layoutParams as FrameLayout.LayoutParams
                lp.gravity = value
                this.layoutParams = lp
            }
            else -> {
            }
        }
    }

fun ViewGroup?.foreachChild(loop: (View) -> Unit) {
    if (this == null) return
    for (i in 0 until this.childCount) {
        loop(this.getChildAt(i))
    }
}


/**
 *  获取当前类型的 某个方法
 */
private fun getDeclaredMethod(obj: Any, methodName: String, vararg parameterTypes: Class<*>): Method? {
    var method: Method? = null
    var clazz: Class<*> = obj.javaClass
    while (clazz != Any::class.java) {
        try {
            method = clazz.getDeclaredMethod(methodName, *parameterTypes)
            //                method?.isAccessible = true
            return method
        } catch (e: Exception) {
            //这里甚么都不要做！并且这里的异常必须这样写，不能抛出去。
            //如果这里的异常打印或者往外抛，则就不会执行clazz = clazz.getSuperclass(),最后就不会进入到父类中了
        }
        clazz = clazz.superclass as Class<*>
    }
    return null
}

private fun getFieldValue(obj: Any?, fieldName: String): Any? {
    if (obj == null || fieldName.isEmpty()) {
        return null
    }
    val clazz: Class<*> = obj.javaClass
    if (clazz != Any::class.java) {
        try {
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            return field.get(obj)
        } catch (e: Exception) {
        }
    }
    return null
}

private fun getListenerInfo(view: View): Any? {
    val method = getDeclaredMethod(view, "getListenerInfo")
    method?.isAccessible = true
    val info = method?.invoke(view)
    return info
}

fun View?.getOnClickListener(): View.OnClickListener? {
    this ?: return null
    val info = getListenerInfo(this)
    info?.let {
        try {
            val m = getFieldValue(it, "mOnClickListener") as View.OnClickListener?
            return m
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return null
}

/**
 * 获取拖拽范围
 * @param slideWidth  拖拽范围
 */
fun DrawerLayout?.getDrawerLeftEdgeSize(): Int {
    val density = (this?.context ?: BaseApplication.instance).resources.displayMetrics.density
    val default = (20 * density + 0.5f).toInt()
    this ?: return default
    return try {
        val leftDraggerField = this::class.java.getDeclaredField("mLeftDragger")
        leftDraggerField.isAccessible = true
        val leftDragger = leftDraggerField.get(this) as? ViewDragHelper ?: return default
        // find edgesize and set is accessible
        val edgeSizeField = leftDragger::class.java.getDeclaredField("mEdgeSize")
        edgeSizeField.isAccessible = true
        edgeSizeField.getInt(leftDragger).min(default)
    } catch (e: Exception) {
        default
    }
}

/**
 * 防止多次点击, 至少要500毫秒的间隔

 */
abstract class OnMultiClickListener(private val time: Long = 500, var click: (v: View) -> Unit = {}) : View.OnClickListener {
    private var lastClickTime: Long = 0

    open fun onMultiClick(v: View) {
        click(v)
    }

    @Deprecated("请勿覆写此方法")
    override fun onClick(v: View) {
        val curClickTime = nowTimeStamp
        if (curClickTime - lastClickTime >= time) {
            // 超过点击间隔后再将lastClickTime重置为当前点击时间
            lastClickTime = curClickTime
            onMultiClick(v)
        }
    }
}
