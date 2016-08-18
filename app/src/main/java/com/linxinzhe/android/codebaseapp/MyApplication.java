package com.linxinzhe.android.codebaseapp;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;

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

//        umeng share
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("1369000913", "11af454c8091426fcde8ee12aac57250");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("100424468", "ty7m23ajd1Nr0lm9");

        //bugly
        CrashReport.initCrashReport(getApplicationContext(), "900031572", false);
    }
}
