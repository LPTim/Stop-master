package com.lp.stop.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lp.stop.R;
import com.lp.stop.activity.MainActivity;
import com.lp.stop.adapter.DynamicAdapter;
import com.lp.stop.utils.L;
import com.lp.stop.view.RecycleViewDivider;
import com.lp.stop.view.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * File descripition:
 *
 * @author lp
 * @date 2019/1/30
 */

public class DynamicFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, OnLoadMoreListener {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private List<String> mBeans;
    private DynamicAdapter mAdapter;

    /**
     * 数据传递演示   （这里没用）
     *
     * @param id
     * @return
     */
    public static DynamicFragment newInstance(String id) {
        DynamicFragment mFragment = new DynamicFragment();

        Bundle bundle = new Bundle();
        bundle.putString("DATA", id);
        mFragment.setArguments(bundle);

        return mFragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_base;
    }

    @Override
    protected void initData() {
        //演示 取值
        Bundle bundle = getArguments();
        String mId = bundle.getString("DATA");

        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setOnLoadMoreListener(this);

        /**
         * 上滑  将底部加载状态置为默认状态
         */
        mRefreshLayout.setOnMultiPurposeListener(new SimpleMultiPurposeListener() {
            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                if (isDragging) {
                    //关闭正在打开状态的 Header 或者 Footer（1.1.0）
                    mRefreshLayout.closeHeaderOrFooter();
                }
            }
        });


        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mBeans = new ArrayList<>();
        mAdapter = new DynamicAdapter(R.layout.item_dynamic, mBeans);
        //开启动画（默认为渐显效果）
        mAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        //设置重复执行动画
        mAdapter.isFirstOnly(true);

        //设置分割线
        mRecyclerView.addItemDecoration(new RecycleViewDivider(
                mContext, LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.colorView)));

        mAdapter.setOnItemClickListener(this);

        mRecyclerView.setAdapter(mAdapter);

        getData();
    }

    /**
     * 模拟数据
     */
    private void getData() {
        for (int i = 0; i < 5; i++) {
            mBeans.add("01");
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(mContext, MainActivity.class));
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore(3000);
        L.e("a a a a a a a ");
    }
}
