package com.snow.framework.extraFunction.value

import android.os.Bundle
import android.os.Parcelable
import android.util.SparseArray
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.set


/**
 * 全局用自定义方法 List部分
 */

/**
 * 安全的List.size
 * */
val <T : Collection<*>> T?.safeSize: Int
    get() {
        return this?.size.orZero
    }

fun <T : List<K>, K> T?.safeGet(position: Int): K? {
    return when {
        isNullOrEmpty() -> null
        position in this.indices -> get(position)
        else -> null
    }
}

/**
 * 安全的Map.size
 * */
val <T : Map<*, *>> T?.safeSize: Int
    get() {
        return this?.size.orZero
    }

/**
 * 将旧list转换为新list
 * */
fun <T, K> List<T>?.toNewList(func: (T) -> K?): ArrayList<K> {
    if (this == null) return arrayListOf()
    val list = arrayListOf<K>()
    forEach {
        func(it)?.let { result ->
            list.add(result)
        }
    }
    return list
}

/**
 * 将旧list转换为新list
 * */
fun <T, K> ArrayList<T>?.toNewList(func: (T) -> K?): ArrayList<K> {
    return (this as? List<T>).toNewList(func)
}

/**
 * 将旧list转换为新list
 * */
fun <T, K> Array<T>?.toNewList(func: (T) -> K): ArrayList<K> {
    if (this == null) return arrayListOf()
    val list = arrayListOf<K>()
    forEach {
        list.add(func(it))
    }
    return list
}

/**
 * 将旧list转换为新list
 * */
fun <K> IntArray?.toNewList(func: (Int) -> K): ArrayList<K> {
    if (this == null) return arrayListOf()
    val list = arrayListOf<K>()
    forEach {
        list.add(func(it))
    }
    return list
}

fun <T, K> List<T>?.toMap(func: (T) -> Pair<String, K>?): HashMap<String, K> {
    if (this == null) return hashMapOf()
    val map = hashMapOf<String, K>()
    forEach {
        func(it)?.apply {
            map[first] = second
        }
    }
    return map
}

fun <T, K, P> Map<P, T>?.toList(func: (Map.Entry<P, T>) -> K?): ArrayList<K> {
    if (this == null) return arrayListOf()
    val list = arrayListOf<K>()
    forEach {
        func(it)?.apply {
            list.add(this)
        }
    }
    return list
}

inline fun <T, reified K, P> Map<P, T>?.toArray(func: (Map.Entry<P, T>) -> K?): Array<K> {
    if (this == null) return arrayOf()
    val list = arrayListOf<K>()
    forEach {
        func(it)?.apply {
            list.add(this)
        }
    }
    return list.toTypedArray()
}

fun <T> List<T>.toArrayList(): ArrayList<T> {
    return ArrayList(this)
}

fun Bundle?.toMap(): Map<String, String> {
    this ?: return mapOf()
    val map = HashMap<String, String>()
    val ks = keySet()
    val iterator: Iterator<String> = ks.iterator()
    while (iterator.hasNext()) {
        val key = iterator.next()
        getString(key)?.let {
            map[key] = it
        }
    }
    return map
}

@Suppress("UNCHECKED_CAST")
fun <T> Collection<T>.toBundle(func: (T.() -> Pair<String, Any?>)): Bundle {
    val bundle = Bundle()
    forEach {
        val pair = it.func()
        val key = pair.first
        val value = pair.second ?: return@forEach
        when (value) {
            is Char -> bundle.putChar(key, value)
            is Byte -> bundle.putByte(key, value)
            is Bundle -> bundle.putBundle(key, value)
            is ByteArray -> bundle.putByteArray(key, value)
            is CharArray -> bundle.putCharArray(key, value)
            is CharSequence -> bundle.putCharSequence(key, value)
            is Float -> bundle.putFloat(key, value)
            is FloatArray -> bundle.putFloatArray(key, value)
            is Int -> bundle.putInt(key, value)
            is Parcelable -> bundle.putParcelable(key, value)
            is Serializable -> bundle.putSerializable(key, value)
            is Short -> bundle.putShort(key, value)
            is ShortArray -> bundle.putShortArray(key, value)
            is String -> bundle.putString(key, value)
            is Boolean -> bundle.putBoolean(key, value)
            is BooleanArray -> bundle.putBooleanArray(key, value)
            is Double -> bundle.putDouble(key, value)
            is DoubleArray -> bundle.putDoubleArray(key, value)
            is IntArray -> bundle.putIntArray(key, value)
            is Long -> bundle.putLong(key, value)
            is LongArray -> bundle.putLongArray(key, value)
            is SparseArray<*> -> if (value.size() != 0) when (value[0]) {
                is Parcelable -> bundle.putSparseParcelableArray(
                    key,
                    value as SparseArray<out Parcelable>
                )
            }
            is Array<*> -> if (value.isNotEmpty()) when (value[0]) {
                is CharSequence -> bundle.putCharSequenceArray(
                    key,
                    value as Array<out CharSequence>
                )
                is Parcelable -> bundle.putParcelableArray(key, value as Array<out Parcelable>)
                is String -> bundle.putStringArray(key, value as Array<out String>)
            }
            is List<*> -> if (value.isNotEmpty()) when (value[0]) {
                is CharSequence -> bundle.putCharSequenceArrayList(
                    key,
                    value as ArrayList<CharSequence>
                )
                is Int -> bundle.putIntegerArrayList(key, value as ArrayList<Int>)
                is Parcelable -> bundle.putParcelableArrayList(
                    key,
                    value as ArrayList<out Parcelable>
                )
                is String -> bundle.putStringArrayList(key, value as ArrayList<String>)
            }
        }
    }
    return bundle
}

fun <T> Array<T>.toBundle(func: (T.() -> Pair<String, Any?>)): Bundle {
    return this.toList().toBundle(func)
}