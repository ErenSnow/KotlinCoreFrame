package com.snow.core.base

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.kennyc.view.MultiStateView
import com.snow.core.R
import com.snow.core.utils.ActivityCollector
import com.snow.framework.utils.shortToast

abstract class BaseActivity : AppCompatActivity(), IBaseView {

    private lateinit var multiStateView: MultiStateView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        ActivityCollector.addActivity(this)
        initView()
        initData()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        setMultiStateView()
    }

    /**
     * 设置状态布局
     */
    private fun setMultiStateView() {
        val view = View.inflate(this, R.layout.layout_multi_state_view, null)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        addContentView(view, params)
        multiStateView = view.findViewById(R.id.msv)
    }

    override fun showLoading() {
        multiStateView.viewState = MultiStateView.ViewState.LOADING
    }

    override fun dismissLoading() {

    }

    override fun showError() {
        multiStateView.viewState = MultiStateView.ViewState.ERROR
    }

    override fun showEmpty() {
        multiStateView.viewState = MultiStateView.ViewState.EMPTY
    }

    override fun showContent() {
        multiStateView.viewState = MultiStateView.ViewState.CONTENT
    }

    override fun onErrorClick() {
        multiStateView.getView(MultiStateView.ViewState.ERROR)
            ?.setOnClickListener {
                multiStateView.viewState = MultiStateView.ViewState.LOADING
                "重新加载数据".shortToast()
                multiStateView.postDelayed({
                    multiStateView.viewState = MultiStateView.ViewState.CONTENT
                }, 3000L)
            }
    }
}