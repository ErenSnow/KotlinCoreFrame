package com.snow.core.utils

import android.app.Activity
import java.util.*

/**
 * 活动管理类
 */
object ActivityCollector {
    /**
     * 通过一个List来缓存活动
     */
    private var activities: MutableList<Activity> = ArrayList()

    /**
     * 用于向List中添加一个活动
     */
    fun addActivity(activity: Activity) {
        activities.add(activity)
    }

    /**
     * 用于从List中移除活动
     */
    fun removeActivity(activity: Activity) {
        activities.remove(activity)
    }

    /**
     * 将List中存储的活动全部销毁掉
     */
    fun finishAll() {
        for (activity in activities) {
            if (!activity.isFinishing) {
                activity.finish()
            }
        }
    }
}