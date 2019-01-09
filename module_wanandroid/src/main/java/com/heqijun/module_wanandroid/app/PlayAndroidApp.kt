package com.heqijun.module_wanandroid.app

import com.heqijun.module_wanandroid.BuildConfig
import com.wxq.commonlibrary.base.BaseApp

/**
 * Created by heqijun on 2019/1/8.
 */
class PlayAndroidApp : BaseApp() {

    override fun dealWithException(thread: Thread?, throwable: Throwable?, error: String?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setIsDebug(): Boolean {
        return BuildConfig.DEBUG
    }
}