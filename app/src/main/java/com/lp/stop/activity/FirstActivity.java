package com.lp.stop.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.lp.stop.R;
import com.lp.stop.adapter.ViewPagerAdapter;
import com.lp.stop.fragment.CeshiFragment;
import com.lp.stop.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private BottomNavigationView navigation;

    private List<Fragment> mFragments;

    private String[] mTitles = new String[]{"动态", "文章", "更多"};
    private List<String> mTitleList = Arrays.asList(mTitles);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        initView();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        //只是提供演示传值
        mFragments.add(CeshiFragment.newInstance("1"));
        mFragments.add(CeshiFragment.newInstance("1"));
        mFragments.add(CeshiFragment.newInstance("1"));
    }

    private void initView() {

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);

        initFragment();

        ViewPagerAdapter myAdapter = new ViewPagerAdapter(getFragmentManager(), mFragments, mTitleList);
        viewPager.setAdapter(myAdapter);

        //导航栏点击事件和ViewPager滑动事件,让两个控件相互关联
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //这里设置为：当点击到某子项，ViewPager就滑动到对应位置
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        viewPager.setCurrentItem(0);
                        return true;
                    case R.id.navigation_dashboard:
                        viewPager.setCurrentItem(1);
                        return true;
                    case R.id.navigation_notifications:
                        viewPager.setCurrentItem(2);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                navigation.getMenu().getItem(position).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

}
