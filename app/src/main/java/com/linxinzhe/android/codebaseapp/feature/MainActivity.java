package com.linxinzhe.android.codebaseapp.feature;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.linxinzhe.android.codebaseapp.R;
import com.linxinzhe.android.codebaseapp.base.BaseActivity;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_go_splash)
    Button mBtnGoSplash;
    @BindView(R.id.btn_go_guide)
    Button mBtnGoGuide;
    @BindView(R.id.btn_go_viewpager_tabs)
    Button mBtnGoViewpagerTabs;
    @BindView(R.id.btn_go_refresh_and_load_more)
    Button mBtnGoRefreshAndLoadMore;
    @BindView(R.id.btn_go_qr_scan)
    Button mBtnGoQrScan;
    @BindView(R.id.btn_go_upload_img)
    Button mBtnGoUploadImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_go_splash)
    public void goSplash(View view) {
        startActivity(SplashActivity.class);
    }

    @OnClick(R.id.btn_go_guide)
    public void goGuide(View view) {
        startActivity(GuideActivity.class);
    }

    @OnClick(R.id.btn_go_viewpager_tabs)
    public void goViewpagerTabs(View view) {
        startActivity(ViewPagerTabsActivity.class);
    }

    @OnClick(R.id.btn_go_refresh_and_load_more)
    public void goRefreshAndLoadMore(View view) {
        startActivity(RefreshAndLoadMoreActivity.class);
    }

    @OnClick(R.id.btn_go_qr_scan)
    public void goQrScan(View view) {
        startActivity(QrScanActivity.class);
    }

    @OnClick(R.id.btn_go_upload_img)
    public void goUploadImg(View view) {
        startActivity(UploadImgActivity.class);
    }

    private boolean isOnKeyBacking;
    private Toast mBackToast;
    private Runnable onBackTimeRunnable = new Runnable() {

        @Override
        public void run() {
            isOnKeyBacking = false;
            if (mBackToast != null) {
                mBackToast.cancel();
            }
        }
    };

    private static final int MSG_DO1 = 1001;
    private final MyHandler mHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MainActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case MSG_DO1:
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if (isOnKeyBacking) {
            mHandler.removeCallbacks(onBackTimeRunnable);
            if (mBackToast != null) {
                mBackToast.cancel();
            }
            // double click log out
            setResult(Activity.RESULT_OK);
            finish();
            return true;
        } else {
            isOnKeyBacking = true;
            if (mBackToast == null) {
                mBackToast = Toast.makeText(this, getString(R.string.tip_double_click_exit), Toast.LENGTH_LONG);
            }
            mBackToast.show();
            mHandler.postDelayed(onBackTimeRunnable, 2000);
            return true;
        }
    }
}
