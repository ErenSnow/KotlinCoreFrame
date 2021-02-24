package com.snow.frame.utils

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.kennyc.view.MultiStateView
import com.snow.frame.R
import com.snow.framework.utils.shortToast

/**
 * MultiStateView布局状态工具类
 */
class MultiStateViewUtil {

    private val multiStateView: MultiStateView?
    private val context: Context?

    constructor(act: AppCompatActivity) {
        multiStateView = act.findViewById<View>(R.id.msv) as MultiStateView?
        context = act
    }

    constructor(view: View, act: Activity) {
        multiStateView = view.findViewById<View>(R.id.msv) as MultiStateView?
        context = act
    }

    constructor(fragment: Fragment) {
        multiStateView = fragment.view?.findViewById<View>(R.id.msv) as MultiStateView?
        context = fragment.activity
    }

    fun showLoading() {
        multiStateView?.viewState = MultiStateView.ViewState.LOADING
    }

    fun showError() {
        multiStateView?.viewState = MultiStateView.ViewState.ERROR
    }

    fun showEmpty() {
        multiStateView?.viewState = MultiStateView.ViewState.EMPTY
    }

    fun showContent() {
        multiStateView?.viewState = MultiStateView.ViewState.CONTENT
    }

    fun onErrorClick(click: () -> Unit = {}) {
        multiStateView?.getView(MultiStateView.ViewState.ERROR)
            ?.setOnClickListener {
                multiStateView.viewState = MultiStateView.ViewState.LOADING
                "重新加载数据".shortToast()
                click()
            }
    }
}