package com.linxinzhe.android.codebaseapp.feature;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.linxinzhe.android.codebaseapp.R;
import com.linxinzhe.android.codebaseapp.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.btn_go_splash)
    Button mBtnGoSplash;
    @Bind(R.id.btn_go_guide)
    Button mBtnGoGuide;
    @Bind(R.id.btn_go_viewpager_tabs)
    Button mBtnGoViewpagerTabs;
    @Bind(R.id.btn_go_refresh_and_load_more)
    Button mBtnGoRefreshAndLoadMore;
    @Bind(R.id.btn_go_qr_scan)
    Button mBtnGoQrScan;
    @Bind(R.id.btn_go_upload_img)
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
}
