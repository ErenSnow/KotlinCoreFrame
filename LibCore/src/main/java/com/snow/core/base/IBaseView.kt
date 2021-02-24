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
}