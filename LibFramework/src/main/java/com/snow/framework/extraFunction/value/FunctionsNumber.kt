package com.snow.framework.extraFunction.value

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import com.orhanobut.logger.Logger
import com.snow.framework.utils.ScreenUtils
import com.snow.framework.extraFunction.value.ExtraNumberFunctions.pt
import com.snow.framework.extraFunction.value.ExtraNumberFunctions.ptFloat
import com.snow.framework.BaseApplication
import java.math.BigDecimal


/**
 * 全局用自定义方法 字符串部分
 */
/**
 * 设计图尺寸转换为实际尺寸
 */
val Number?.pt: Int
    get() = pt()

/**
 * dp尺寸转换为实际尺寸
 */
val Number?.dp: Int
    get() {
        this ?: return 0
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            BaseApplication.instance.resources.displayMetrics
        ).toInt()
    }

/**
 * 设计图尺寸转换为实际尺寸
 */
val Number?.ptFloat: Float
    get() = ptFloat()

/**
 * 保留小数
 * */
fun Number?.toFixed(fixed: Int, mode: Int = BigDecimal.ROUND_UP): String {
    return BigDecimal((this ?: 0).toString()).setScale(fixed, mode).toString()
}

/**
 * 保留小数
 * */
fun Number?.toFixedDouble(fixed: Int, mode: Int = BigDecimal.ROUND_UP): Double {
    return BigDecimal((this ?: 0).toString()).setScale(fixed, mode).toDouble()
}

/**
 * 保留小数
 * */
fun BigDecimal?.toFixed(fixed: Int, mode: Int = BigDecimal.ROUND_UP): String {
    return (this ?: BigDecimal.ZERO).setScale(fixed, mode).toString()
}

/**
 * 处理null问题
 * */
val Int?.orZero: Int
    get() {
        return this ?: 0
    }

/**
 * 处理null问题
 * */
val Float?.orZero: Float
    get() {
        return this ?: 0f
    }

/**
 * 处理null问题
 * */
val Double?.orZero: Double
    get() {
        return this ?: 0.0
    }

/**
 * 处理null问题
 * */
val Long?.orZero: Long
    get() {
        return this ?: 0L
    }


/**
 * 处理null问题
 * */
val BigDecimal?.orZero: BigDecimal
    get() {
        return this ?: BigDecimal.ZERO
    }

/**
 * 保留小数
 * */
fun Number?.toFixedWithoutZero(fixed: Int, mode: Int = BigDecimal.ROUND_UP): String {
    return BigDecimal((this ?: 0).toString()).setScale(fixed, mode).toString().let {
        val result = it.toMutableList()
        while (result.contains('.') && result.last() == '0') {
            result.removeAt(result.lastIndex)
        }
        if (result.last() == '.') {
            result.removeAt(result.lastIndex)
        }
        val sb = StringBuilder()
        result.forEach { char ->
            sb.append(char)
        }
        sb.toString()
    }
}

/**
 * 防空转换Int
 */
fun CharSequence?.toSafeInt(default: Int = 0): Int {
    if (this.isNullOrEmpty()) return default
    return try {
        if (this is String) {
            this.toInt()
        } else {
            this.toString().toInt()
        }
    } catch (e: Exception) {
        Logger.e(e.printStackTrace().toString())
        default
    }
}

/**
 * 防空转换Int
 */
fun Number?.toSafeInt(default: Int = 0): Int {
    this ?: return default
    return this.toInt()
}


/**
 * 防空转换Long
 */
fun CharSequence?.toSafeLong(default: Long = 0L): Long {
    if (this.isNullOrEmpty()) return default
    return try {
        if (this is String) {
            this.toLong()
        } else {
            this.toString().toLong()
        }
    } catch (e: Exception) {
        Logger.e(e.printStackTrace().toString())
        default
    }
}

/**
 * 防空转换Long
 */
fun Number?.toSafeLong(default: Long = 0L): Long {
    this ?: return default
    return this.toLong()
}

/**
 * 防空转换Double
 */
fun CharSequence?.toSafeDouble(default: Double = 0.0): Double {
    if (this.isNullOrEmpty() || this == ".") return default
    return try {
        if (this is String) {
            this.toDouble()
        } else {
            this.toString().toDouble()
        }
    } catch (e: Exception) {
        Logger.e(e.printStackTrace().toString())
        default
    }
}

/**
 * 防空转换Double
 */
fun Number?.toSafeDouble(default: Double = 0.0): Double {
    this ?: return default
    return this.toDouble()
}

/**
 * 防空转换Float
 */
fun CharSequence?.toSafeFloat(default: Float = 0f): Float {
    if (this.isNullOrEmpty() || this == ".") return default
    return try {
        if (this is String) {
            this.toFloat()
        } else {
            this.toString().toFloat()
        }
    } catch (e: Exception) {
        Logger.e(e.printStackTrace().toString())
        default
    }
}

/**
 * 防空转换Float
 */
fun Number?.toSafeFloat(default: Float = 0f): Float {
    this ?: return default
    return this.toFloat()
}

/**
 * 防空转换BigDecimal
 */
fun CharSequence?.toSafeBigDecimal(default: Double = 0.0): BigDecimal {
    if (this.isNullOrEmpty() || this == ".") return BigDecimal.valueOf(default)
    return try {
        if (this is String) {
            this.toBigDecimal()
        } else {
            this.toString().toBigDecimal()
        }
    } catch (e: Exception) {
        Logger.e(e.printStackTrace().toString())
        BigDecimal.valueOf(default)
    }
}

/**
 * 防空转换BigDecimal
 */
fun Number?.toSafeBigDecimal(default: BigDecimal = BigDecimal.ZERO): BigDecimal {
    this ?: return default
    return this.toDouble().toBigDecimal()
}

object ExtraNumberFunctions {
    /**
     * 设计图尺寸转换为实际尺寸
     */
    fun Number?.pt(): Int {
        if (this == null) return 0
        return ScreenUtils.getRealSize(this.toDouble())
    }

    /**
     * 设计图尺寸转换为实际尺寸
     */
    fun Number?.ptFloat(context: Context = BaseApplication.instance): Float {
        if (this == null) return 0f
        return ScreenUtils.getRealSizeFloat(context, this.toFloat())
    }

    /**
     * 获取顶栏高度
     * */
    fun getInternalDimensionSize(context: Context, key: String): Int {
        val result = 0
        try {
            val resourceId = Resources.getSystem().getIdentifier(key, "dimen", "android")
            if (resourceId > 0) {
                val sizeOne = context.resources.getDimensionPixelSize(resourceId)
                val sizeTwo = Resources.getSystem().getDimensionPixelSize(resourceId)
                return if (sizeTwo >= sizeOne) {
                    sizeTwo
                } else {
                    val densityOne = context.resources.displayMetrics.density
                    val densityTwo = Resources.getSystem().displayMetrics.density
                    val f = sizeOne * densityTwo / densityOne
                    (if (f >= 0) f + 0.5f else f - 0.5f).toInt()
                }
            }
        } catch (ignored: Resources.NotFoundException) {
            return 0
        }
        return result
    }
}
