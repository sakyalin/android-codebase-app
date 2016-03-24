package com.linxinzhe.android.codebaseapp;

import android.app.Application;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

/**
 * @author linxinzhe on 3/21/16.
 */
//public class MyApplication extends MultiDexApplication {
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Logger.init(getString(R.string.app_name));
        } else {
            Logger.init(getString(R.string.app_name)).logLevel(LogLevel.NONE);
        }

        //umeng
        AnalyticsConfig.setAppkey(getApplicationContext(), "APPID");
        MobclickAgent.setDebugMode(true);
        AnalyticsConfig.enableEncrypt(true);

        //bugly
        CrashReport.initCrashReport(getApplicationContext(), "注册时申请的APPID", false);
    }
}
