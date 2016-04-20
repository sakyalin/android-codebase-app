package com.linxinzhe.android.codebaseapp.feature;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.linxinzhe.android.codebaseapp.R;
import com.linxinzhe.android.codebaseapp.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class GuideActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    //引导图片资源
    private static final int[] pics = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    private ViewPager vp;
    //底部小点图片
    private ImageView[] dots;

    //记录当前选中位置
    private int currentIndex;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        List<View> views = new ArrayList<>();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        //初始化引导图片列表
        for (int pic : pics) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pic);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            views.add(iv);
        }

        vp = (ViewPager) findViewById(R.id.viewpager_guide);
        //初始化Adapter
        ViewPagerAdapter vpAdapter = new ViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        //绑定回调
        vp.addOnPageChangeListener(this);

        //初始化底部小点
        initDots();

    }

    @OnClick(R.id.btn_skip_guide)
    public void skipGuide(View view) {
        startActivity(MainActivity.class);
        finish();
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll_points);

        dots = new ImageView[pics.length];

        if (ll != null) {
            //循环取得小点图片
            for (int i = 0; i < pics.length; i++) {
                dots[i] = (ImageView) ll.getChildAt(i);
                dots[i].setEnabled(true);//都设为灰色
                dots[i].setOnClickListener(this);
                dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应
            }
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置为白色，即选中状态
    }

    /**
     * 设置当前的引导页
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }

        vp.setCurrentItem(position);
    }

    /**
     * 这只当前引导小点的选中
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length - 1 || currentIndex == position) {
            return;
        }

        //绘制底部的小圆点
        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;

    }

    //当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    //当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    //当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        //设置底部小点选中状态
        setCurDot(arg0);

    }

    @Override
    public void onClick(View v) {
        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    private class ViewPagerAdapter extends PagerAdapter {

        //界面列表
        private List<View> views;

        public ViewPagerAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views.get(position));
        }

        //获得当前界面数
        @Override
        public int getCount() {
            if (views != null) {
                return views.size();
            }
            return 0;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(views.get(position), 0);
            return views.get(position);
        }

        //判断是否由对象生成界面

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}