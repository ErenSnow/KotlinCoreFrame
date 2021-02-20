package com.snow.framework.extraFunction.value


/**
 * 全局用自定义方法 数字部分

 */
/**
 * 设定最小值
 * */
fun Int?.min(min: Int): Int {
    return when {
        this == null -> min
        this <= min -> min
        else -> this
    }
}

/**
 * 设定最小值
 * */
fun Float?.min(min: Float): Float {
    return when {
        this == null -> min
        this <= min -> min
        else -> this
    }
}

/**
 * 设定最小值
 * */
fun Double?.min(min: Double): Double {
    return when {
        this == null -> min
        this <= min -> min
        else -> this
    }
}

/**
 * 设定最小值
 * */
fun Long?.min(min: Long): Long {
    return when {
        this == null -> min
        this <= min -> min
        else -> this
    }
}

/**
 * 设定最大值
 * */
fun Int?.max(max: Int): Int {
    return when {
        this == null -> max
        this >= max -> max
        else -> this
    }
}

/**
 * 设定最大值
 * */
fun Long?.max(max: Long): Long {
    return when {
        this == null -> max
        this >= max -> max
        else -> this
    }
}

/**
 * 设定最大值
 * */
fun Float?.max(max: Float): Float {
    return when {
        this == null -> max
        this >= max -> max
        else -> this
    }
}


/**
 * 设定最大值
 * */
fun Double?.max(max: Double): Double {
    return when {
        this == null -> max
        this >= max -> max
        else -> this
    }
}

/**
 * 设定范围
 * */
fun Int?.fitRange(range: IntRange): Int {
    return when {
        this == null -> range.first
        this <= range.first -> range.first
        this >= range.last -> range.last
        else -> this
    }
}

/**
 * 设定范围
 * */
fun Long?.fitRange(range: LongRange): Long {
    return when {
        this == null -> range.first
        this <= range.first -> range.first
        this >= range.last -> range.last
        else -> this
    }
}

/**
 * 设定范围
 * */
fun Double?.fitRange(range: IntRange): Double {
    return when {
        this == null -> range.first.toDouble()
        this <= range.first -> range.first.toDouble()
        this >= range.last -> range.last.toDouble()
        else -> this
    }
}

/**
 * 设定范围
 * */
fun Float?.fitRange(range: IntRange): Float {
    return when {
        this == null -> range.first.toFloat()
        this <= range.first -> range.first.toFloat()
        this >= range.last -> range.last.toFloat()
        else -> this
    }
}
