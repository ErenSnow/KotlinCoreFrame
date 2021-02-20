package com.snow.framework.extraFunction.view

import android.os.SystemClock
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.snow.framework.extraFunction.value.orZero
import com.snow.framework.extraFunction.view.ExtraRecyclerViewFunctions.smoothScroll


/**
 * 全局用自定义方法 RecyclerView部分

 */

/**
 * 刷新RecyclerView，无动画
 */
fun RecyclerView?.refresh() {
    if (this == null) return
    this.adapter?.notifyDataSetChanged()
}

fun RecyclerView?.cancelItemAnimator() {
    this ?: return
    (itemAnimator as? SimpleItemAnimator)?.apply {
        addDuration = 0
        changeDuration = 0
        moveDuration = 0
        removeDuration = 0
        supportsChangeAnimations = false
    }
}

/**
 * 判断是否滑到顶端
 * */
fun RecyclerView?.isTop(): Boolean {
    if (this == null) return true
    val layoutManager = layoutManager as LinearLayoutManager? ?: return true
    val position = layoutManager.findFirstVisibleItemPosition()
    return if (position <= 0) {
        val firstChild = layoutManager.findViewByPosition(position)
        firstChild?.let { return it.top >= 0 } ?: true
    } else {
        false
    }
}

/**
 * 判断是否滑到底端
 * */
fun RecyclerView?.isBottom(): Boolean {
    if (this == null) return true
    val layoutManager = layoutManager as LinearLayoutManager? ?: return true
    val position = layoutManager.findLastVisibleItemPosition()
    return if (position >= adapter?.itemCount.orZero - 1) {
        val lastChild = layoutManager.findViewByPosition(position)
        lastChild?.let { it.bottom <= height } ?: true
    } else {
        false
    }
}

/**
 * 不触发scrollListener的情况下执行操作
 * */
fun RecyclerView?.doWithoutScrollListener(
    onScroll: RecyclerView.OnScrollListener,
    listener: () -> Unit,
) {
    if (this == null) return
    removeOnScrollListener(onScroll)
    //这里处理相应的事件
    listener()
    //通过addOnGlobalLayoutListener在滚动完毕后再添加listener
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            addOnScrollListener(onScroll)
        }
    })
}

/**
 * 滚动RecyclerView，目标位置置顶
 */
fun RecyclerView?.toPositionSmooth(pos: Int, scale: Float = 1f) {
    smoothScroll(pos, LinearSmoothScroller.SNAP_TO_START, scale)
}

/**
 * 滚动RecyclerView，目标位置置底
 */
fun RecyclerView?.toBottomPositionSmooth(pos: Int, scale: Float = 1f) {
    smoothScroll(pos, LinearSmoothScroller.SNAP_TO_END, scale)
}


/**
 * 滚动RecyclerView，可带有offset
 */
fun RecyclerView?.toPosition(pos: Int, offset: Int = 0) {
    if (this == null) return

    try {
        (parent as ViewGroup).dispatchTouchEvent(
            MotionEvent.obtain(
                SystemClock.uptimeMillis(),
            SystemClock.uptimeMillis(),
            MotionEvent.ACTION_CANCEL, 0f, 0f, 0))
    } catch (ignore: java.lang.Exception) {
    }

    scrollToPosition(pos)
    when (val mLayoutManager = layoutManager) {
        is LinearLayoutManager -> {
            mLayoutManager.scrollToPositionWithOffset(pos, offset)
        }
        is GridLayoutManager -> {
            mLayoutManager.scrollToPositionWithOffset(pos, offset)
        }
        is StaggeredGridLayoutManager -> {
            mLayoutManager.scrollToPositionWithOffset(pos, offset)
        }
    }
}

/**
 * RecyclerView添加不遮挡item的padding
 */
fun RecyclerView?.paddingClipless(
    start: Int? = null, top: Int? = null, end: Int? = null,
    bottom: Int? = null,
) {
    if (this == null) return
    clipToPadding = false
    padding(start, top, end, bottom)
}

/**
 * 设置水平的LayoutManager和adapter
 * */
fun RecyclerView?.initLinearHorizontal(adapter: RecyclerView.Adapter<*>) {
    this ?: return
    layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
    this.adapter = adapter
}

/**
 * 设置垂直的LayoutManager和adapter
 * */
fun RecyclerView?.initLinearVertical(adapter: RecyclerView.Adapter<*>? = null) {
    this ?: return
    layoutManager = LinearLayoutManager(context, VERTICAL, false)
    if (adapter != null) this.adapter = adapter
}

/**
 * 设置水平的Grid的LayoutManager和adapter
 * */
fun RecyclerView?.initGridHorizontal(adapter: RecyclerView.Adapter<*>, columns: Int) {
    this ?: return
    layoutManager = GridLayoutManager(context, columns, VERTICAL, false)
    this.adapter = adapter
}

/**
 * 获取holder
 * */
fun <K : RecyclerView.ViewHolder> RecyclerView?.getHolder(position: Int): K? {
    if (this == null) return null
    adapter?.let { adapter ->
        if (position !in 0 until adapter.itemCount) {
            return null
        }
    } ?: return null
    return findViewHolderForAdapterPosition(position) as? K
}

object ExtraRecyclerViewFunctions {
    fun RecyclerView?.smoothScroll(pos: Int, type: Int, scale: Float) {
        if (this == null) return
        val manager = layoutManager
        if (manager !is LinearLayoutManager) return

        try {
            (parent as ViewGroup).dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(),
                MotionEvent.ACTION_CANCEL, 0f, 0f, 0))
        } catch (ignore: java.lang.Exception) {
        }

        val first = manager.findFirstVisibleItemPosition()
        val last = manager.findLastVisibleItemPosition()
        if (first > pos || last < pos) {
            val smoothScroller = object : LinearSmoothScroller(this.context) {
                override fun getVerticalSnapPreference(): Int = type
                override fun getHorizontalSnapPreference(): Int = type
            }
            smoothScroller.targetPosition = pos
            layoutManager?.startSmoothScroll(smoothScroller)
        } else {
            val top = manager.findViewByPosition(pos)?.top.orZero
            val height = manager.findViewByPosition(pos)?.height.orZero
            val listHeight = measuredHeight

            when (type) {
                LinearSmoothScroller.SNAP_TO_START -> {
                    smoothScrollBy(0, (top * scale).toInt())
                }
                LinearSmoothScroller.SNAP_TO_END -> {
                    smoothScrollBy(0, ((top + height - listHeight) * scale).toInt())
                }
            }
        }
    }

}