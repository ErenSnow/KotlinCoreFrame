package com.snow.framework.extraFunction

import android.content.pm.PackageManager
import android.os.Bundle
import com.snow.framework.BaseApplication
import com.snow.framework.utils.MD5SHA256Util

/**
 * 全局用自定义方法 其他部分

 */

/**获取Manifest中的参数*/
fun getManifestString(name: String): String? {
    return BaseApplication.instance.packageManager
        .getApplicationInfo(BaseApplication.instance.packageName, PackageManager.GET_META_DATA)
        .metaData.get(name)?.toString()
}

/**
 * 清空fragment缓存
 * */
fun Bundle?.clearFragmentSavedState() {
    this ?: return
    remove("android:support:fragments")
    remove("android:fragments")
}

/**
 * 计算md5值
 */
val String?.md5 get() = this?.let { MD5SHA256Util.md5(it) }.orEmpty()

/**
 * 计算sha1值
 */
val String?.sha1 get() = this?.let { MD5SHA256Util.sha1(it) }.orEmpty()

/**
 * 计算sha256值
 */
val String?.sha256 get() = this?.let { MD5SHA256Util.sha256(it) }.orEmpty()