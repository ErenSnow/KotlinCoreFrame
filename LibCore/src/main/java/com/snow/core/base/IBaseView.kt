package com.snow.core.base

/**
 * Activity和Fragment初始化函数
 */
interface IBaseView {

    /**
     * 初始化布局
     */
    fun initView()

    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 具体的布局
     */
    fun getLayoutId(): Int

    /**
     * 显示加载框
     */
    fun showLoading()

    /**
     * 隐藏加载框
     */
    fun dismissLoading()

    /**
     * 加载错误界面
     */
    fun showError()

    /**
     * 空界面
     */
    fun showEmpty()

    /**
     * 显示界面
     */
    fun showContent()

    /**
     * 空白或错误后点击刷新
     */
    fun onErrorClick()
}