package com.linxinzhe.android.codebaseapp.feature;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.linxinzhe.android.codebaseapp.R;
import com.linxinzhe.android.codebaseapp.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewPagerTabsActivity extends AppCompatActivity {

    public String[] tabTexts = {"tab1", "tab2", "tab3"};
    public int[] tabImgIdNormla = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    public int[] tabImgIdSelected = {R.mipmap.ic_launcher, R.mipmap.ic_launcher, R.mipmap.ic_launcher};
    @Bind(R.id.container)
    ViewPager mContainer;
    private List<View> mTabViewList = new ArrayList<>();
    private List<BaseFragment> mFragmentList = new ArrayList<>();
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_tabs);
        ButterKnife.bind(this);

        initView();

    }

    private void initView() {
        mFragmentList.add(new BaseFragment());
        mFragmentList.add(new BaseFragment());
        mFragmentList.add(new BaseFragment());

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mContainer.setOffscreenPageLimit(2);
        mContainer.setAdapter(mSectionsPagerAdapter);
        mContainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // reset all tab
                for (int i = 0; i < mTabViewList.size(); i++) {
                    View v = mTabViewList.get(i);
                    TextView tv = (TextView) v.findViewById(R.id.tv_txt);
                    tv.setTextColor(getResources().getColor(R.color.sub_txt));
                    ImageView img = (ImageView) v.findViewById(R.id.iv_icon);
                    img.setImageResource(tabImgIdNormla[i]);
                }

                //set tab selected
                View view = mTabViewList.get(position);
                TextView tv = (TextView) view.findViewById(R.id.tv_txt);
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));
                tv.setText(tabTexts[position]);
                ImageView img = (ImageView) view.findViewById(R.id.iv_icon);
                img.setImageResource(tabImgIdSelected[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(mContainer);
            //init tab
            for (int i = 0; i < tabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = tabLayout.getTabAt(i);
                if (tab != null) {
                    View tabView = getTabView(i);
                    mTabViewList.add(tabView);
                    tab.setCustomView(tabView);
                }
            }
        }
    }

    public View getTabView(int position) {
        View v = LayoutInflater.from(this).inflate(R.layout.item_tab_home_view, mContainer);
        ImageView img = (ImageView) v.findViewById(R.id.iv_icon);
        TextView tv = (TextView) v.findViewById(R.id.tv_txt);
        tv.setText(tabTexts[position]);

        if (position == 0) {
            // first selected tab
            tv.setTextColor(getResources().getColor(R.color.colorPrimary));
            img.setImageResource(tabImgIdSelected[position]);
        } else {
            tv.setTextColor(getResources().getColor(R.color.sub_txt));
            img.setImageResource(tabImgIdNormla[position]);
        }

        return v;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_view_pager_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTexts[position];
        }

    }
}
