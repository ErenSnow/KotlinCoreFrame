package com.snow.framework.extraFunction.view

import android.view.View
import android.view.View.NO_ID
import android.view.View.generateViewId
import androidx.constraintlayout.widget.ConstraintSet

/**
 * 自定义方法 关系布局

 *
 * usage
 *
 * ConstraintSet().applyConnect(
 *      a.BOTTOM to b.TOP margin 3.pt,
 *      b.BOTTOM to c.TOP,
 * ).applySize(
 *      a width 8.pt height ConstraintSet.WRAP_CONTENT
 *      b width 9.pt height ConstraintSet.MATCH_PARENT
 * )
 */
interface FunctionsConstraintLayout {

    val View.BOTTOM: Pair<View, Int>
        get() = this to ConstraintSet.BOTTOM
    val View.TOP: Pair<View, Int>
        get() = this to ConstraintSet.TOP
    val View.START: Pair<View, Int>
        get() = this to ConstraintSet.START
    val View.END: Pair<View, Int>
        get() = this to ConstraintSet.END

    infix fun Pair<View, Int>.to(pair: Pair<View, Int>) = Triple(this, pair, 0)
    infix fun Triple<Pair<View, Int>, Pair<View, Int>, Int>.margin(margin: Int) =
        Triple(this.first, this.second, margin)

    fun ConstraintSet.applyConnect(vararg rules: Triple<Pair<View, Int>, Pair<View, Int>, Int>): ConstraintSet {
        rules.forEach {
            if (it.first.first.id == NO_ID) it.first.first.id = generateViewId()
            if (it.second.first.id == NO_ID) it.second.first.id = generateViewId()
            connect(it.first.first.id, it.first.second, it.second.first.id, it.second.second, it.third)
        }
        return this
    }

    infix fun View.w(width: Int) = Triple(this, width, Int.MIN_VALUE)
    infix fun View.h(height: Int) = Triple(this, Int.MIN_VALUE, height)

    infix fun Triple<View, Int, Int>.w(width: Int) = Triple(first, width, third)
    infix fun Triple<View, Int, Int>.h(height: Int) = Triple(first, second, height)

    fun ConstraintSet.applySize(vararg rules: Triple<View, Int, Int>): ConstraintSet {
        rules.forEach {
            if (it.first.id == NO_ID) it.first.id = generateViewId()
            //设置宽
            if (it.second != Int.MIN_VALUE) constrainWidth(it.first.id, it.second)
            //设置高
            if (it.third != Int.MIN_VALUE) constrainHeight(it.first.id, it.third)
        }
        return this
    }


    infix fun Pair<View, Int>.goneMargin(margin: Int) =
        Triple(this.first, this.second, margin)

    fun ConstraintSet.applyGoneMargin(vararg rules: Triple<View, Int, Int>): ConstraintSet {
        rules.forEach {
            if (it.first.id == NO_ID) it.first.id = generateViewId()
            setGoneMargin(it.first.id, it.second, it.third)
        }
        return this
    }
}