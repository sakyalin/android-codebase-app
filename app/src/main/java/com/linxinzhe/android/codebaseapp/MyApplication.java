package com.linxinzhe.android.codebaseapp;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;

/**
 * @author linxinzhe on 3/21/16.
 */
//public class MyApplication extends MultiDexApplication {
public class MyApplication extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

        if (BuildConfig.DEBUG) {
            Logger.init(getString(R.string.app_name));
        } else {
            Logger.init(getString(R.string.app_name)).logLevel(LogLevel.NONE);
        }

        //stetho
        Stetho.initializeWithDefaults(this);

        //umeng
        MobclickAgent.setDebugMode(BuildConfig.DEBUG);
        MobclickAgent.enableEncrypt(true);

        //bugly
        CrashReport.initCrashReport(getApplicationContext(), "900031572", false);
    }
}
