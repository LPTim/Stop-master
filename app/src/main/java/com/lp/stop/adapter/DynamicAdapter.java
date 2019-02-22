package com.lp.stop.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * File descripition: 动态
 *
 * @author lp
 * @date 2019/1/30
 */

public class DynamicAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public DynamicAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
