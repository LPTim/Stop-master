package com.lp.stop.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lp.stop.R;
import com.lp.stop.adapter.ViewPagerAdapter;
import com.lp.stop.fragment.DynamicFragment;
import com.lp.stop.utils.DisplayUtil;
import com.lp.stop.utils.ScreenUtil;
import com.lp.stop.utils.StatusBarUtil;
import com.lp.stop.view.ColorFlipPagerTitleView;
import com.lp.stop.view.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements OnRefreshListener, AppBarLayout.OnOffsetChangedListener {
    @BindView(R.id.magic_indicator)
    MagicIndicator mMagicIndicator;
    @BindView(R.id.appbar_layout)
    AppBarLayout mAppbarLayout;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.iv_header)
    ImageView mIvHeader;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.buttonBarLayout)
    ButtonBarLayout mButtonBarLayout;
    @BindView(R.id.toolbar_username)
    TextView mToolbarUsername;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.iv_menu)
    ImageView mIvMenu;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.llt_1)
    LinearLayout mLlt1;
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.toolbar1)
    Toolbar mToolbar1;

    private List<Fragment> mFragments;
    private ViewPagerAdapter mViewPagerAdapter;
    private CommonNavigator mCommonNavigator;


    private String[] mTitles = new String[]{"动态", "文章", "更多"};
    private List<String> mTitleList = Arrays.asList(mTitles);
    /**
     * 屏幕宽度
     */
    private int mScreenWidth = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //将头部状态栏置为透明
        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //禁止上拉加载
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshListener(this);
        //上滑移动 设置监听
        mAppbarLayout.addOnOffsetChangedListener(this);

        //增加View的paddingTop,增加的值为状态栏高度 (智能判断，并设置高度)  titleBar
        StatusBarUtil.setPaddingSmart(this, mToolbar);
        StatusBarUtil.setPaddingSmart(this, mToolbar1);

        //获得屏幕宽度
        mScreenWidth = ScreenUtil.getScreenWidth(this);

        initFragment();
        initMagicIndicator();
        initView();

        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), mFragments, mTitleList);
        mViewPager.setAdapter(mViewPagerAdapter);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void initView() {
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                //设置图片向下移动
                mIvHeader.setTranslationY(offset / 2);
                //设置title渐变效果
                mToolbar1.setAlpha(1 - Math.min(percent, 1));
                //设置图片宽度变化   当达到指定设置的指定值后  宽度停止  只向下移动
                if (offset <= 100) {
                    ViewGroup.LayoutParams layoutParams = mIvHeader.getLayoutParams();
                    layoutParams.width = (mScreenWidth + offset);
                    ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - mScreenWidth) / 2, -DisplayUtil.dip2px(MainActivity.this,200), 0, 0);
                    mIvHeader.requestLayout();
                }
            }
        });

    }

    private void initFragment() {
        mFragments = new ArrayList<>();
        //只是提供演示传值
        mFragments.add(DynamicFragment.newInstance("1"));
        mFragments.add(DynamicFragment.newInstance("1"));
        mFragments.add(DynamicFragment.newInstance("1"));
    }

    private void initMagicIndicator() {
        mMagicIndicator.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        mCommonNavigator = new CommonNavigator(this);
        mCommonNavigator.setAdjustMode(true);
        mCommonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mTitleList == null ? 0 : mTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorFlipPagerTitleView(context);
                simplePagerTitleView.setText(mTitleList.get(index));
                simplePagerTitleView.setTextSize(14);
                simplePagerTitleView.setNormalColor(Color.BLACK);
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.colorFB3838));
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.colorGray));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setLineWidth(UIUtil.dip2px(context, 30));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(getResources().getColor(R.color.colorFB3838));
                return indicator;
            }
        });

        mMagicIndicator.setNavigator(mCommonNavigator);
        ViewPagerHelper.bind(mMagicIndicator, mViewPager);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishRefresh(1000);
    }

    /**
     * 上滑移动 监听
     *
     * @param appBarLayout
     * @param verticalOffset
     */
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int scrollRangle = appBarLayout.getTotalScrollRange();
        /**
         * 如果是verticalOffset改成负数   有不一样的效果，可以模拟试试
         */
        mIvHeader.setTranslationY(verticalOffset);

        /**
         * 这个数值可以自己定义
         */
        if (verticalOffset < -10) {
            mIvBack.setImageResource(R.drawable.back_black);
            mIvMenu.setImageResource(R.drawable.icon_menu_black);
        } else {
            mIvBack.setImageResource(R.drawable.back_white);
            mIvMenu.setImageResource(R.drawable.icon_menu_white);
        }

        int mAlpha = (int) Math.abs(255f / scrollRangle * verticalOffset);
        //顶部title渐变效果
        mToolbar1.setBackgroundColor(Color.argb(mAlpha, 255, 255, 255));
        mToolbarUsername.setTextColor(Color.argb(mAlpha, 0, 0, 0));
    }
}
