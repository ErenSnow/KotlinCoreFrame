package com.snow.framework;

import android.app.Application;

import androidx.multidex.MultiDexApplication;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.BuildConfig;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class BaseApplication extends MultiDexApplication {

    public static BaseApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 初始化日志打印框架
        initLogger();
    }

    /**
     * 初始化日志打印框架
     */
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                //（可选）是否显示线程信息。 默认值为true
                .showThreadInfo(true)
                //（可选）要显示的方法行数。 默认2
                .methodCount(2)
                //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .methodOffset(7)
                //（可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .logStrategy(new LogcatLogStrategy())
                //（可选）每个日志的全局标记。 默认PRETTY_LOGGER
                .tag("AMD")
                .build();
        // 开启
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                //DEBUG模式下不打印LOG
                return BuildConfig.DEBUG;
            }
        });
    }
}
