package com.snow.frame.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.snow.frame.R
import com.snow.framework.extraFunction.dimen
import com.snow.framework.extraFunction.value.max
import com.snow.framework.extraFunction.value.min
import com.snow.framework.extraFunction.view.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

/**
 * Toolbar工具类
 */

class ToolBarUtil {
    private val toolbar: Toolbar?
    private val context: Context?

    constructor(act: AppCompatActivity) {
        toolbar = act.findViewById<View>(R.id.toolbar) as Toolbar?
        context = act
        act.setSupportActionBar(toolbar)
        act.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeButtonEnabled(false)
        }
    }

    constructor(view: View, act: Activity) {
        toolbar = view.findViewById<View>(R.id.toolbar) as Toolbar?
        context = act

    }

    constructor(fragment: Fragment) {
        toolbar = fragment.view?.findViewById<View>(R.id.toolbar) as Toolbar?
        context = fragment.activity
    }

    fun getTitleView(): TextView? {
        return toolbar?.toolbarTitle
    }

    fun setTitle(title: String): ToolBarUtil {
        toolbar?.toolbarTitle?.text = title
        return this
    }

    fun setMenuBtn(menu: String, click: () -> Unit = {}): ToolBarUtil {
        toolbar?.btnMenu?.apply {
            visible()
            text = menu
            click {
                click()
            }
        }
        return this
    }

    fun setMenuBtnColor(@ColorRes color: Int) {
        toolbar?.btnMenu?.textColor(color)
    }

    fun setMenuImgBtn(@DrawableRes res: Int, size: Int = 0, click: () -> Unit = {}): ToolBarUtil {
        toolbar?.imgMenu?.apply {
            visible()
            setImageResource(res)
            click {
                click()
            }
            val imgSize = dimen(R.dimen.toolbarHeight)
            val padding = ((imgSize - size) / 2f).min(0f).max(imgSize)
            size(imgSize.toInt(), imgSize.toInt())
            paddingAll(padding.toInt())
        }
        return this
    }

    fun hideMenuBtn(): ToolBarUtil {
        toolbar?.btnMenu?.gone()
        toolbar?.imgMenu?.gone()
        return this
    }


    fun showMenuBtn(): ToolBarUtil {
        toolbar?.btnMenu?.visible()
        return this
    }


    fun setBack(click: () -> Unit = {}): ToolBarUtil {
        toolbar?.setNavigationOnClickListener {
            click()
        }
        return this
    }

    fun getToolBar(): Toolbar? {
        return toolbar
    }

    fun setBottomLine(showLine: Boolean) {
        if (showLine) {
            toolbar?.background(R.drawable.bg_toolbar)
        } else {
            toolbar?.background(R.color.white)
        }
    }

}