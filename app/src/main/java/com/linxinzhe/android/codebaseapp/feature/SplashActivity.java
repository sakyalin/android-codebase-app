package com.linxinzhe.android.codebaseapp.feature;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.linxinzhe.android.codebaseapp.AppConfig;
import com.linxinzhe.android.codebaseapp.R;
import com.linxinzhe.android.codebaseapp.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

/**
 * @author linxinzhe on 2015/8/5.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        //友盟统计上传信息
        /** 设置是否对日志信息进行加密, 默认false(不加密). */

        // 记录是否第一次启动
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean isFirstIn = preferences.getBoolean(AppConfig.SP_FIRST_RUN, true);

        // 判断是否初次运行，是则跳到引导界面，否则跳转到主界面
        if (isFirstIn) {
            Observable.timer(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    startActivity(GuideActivity.class);
                }
            });
            preferences.edit().putBoolean(AppConfig.SP_FIRST_RUN, false).apply();
        } else {
            Observable.timer(1, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
                @Override
                public void call(Long aLong) {
                    startActivity(ViewPagerTabsActivity.class);
                }
            });
        }

    }

}
