package com.matto.ui.adapter;

import android.content.Context;

import com.common.apdater.BasicAdapter;
import com.common.apdater.BasicViewHolder;
import com.matto.R;
import com.matto.pojo.GankDetails;

import java.util.List;

/**
 * author miekoz on 2016/3/18.
 * email  meikoz@126.com
 */
public class MainAdapter extends BasicAdapter<GankDetails> {

    public MainAdapter(Context context, List<GankDetails> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(BasicViewHolder helper, GankDetails item) {
        helper.setText(R.id.tv_test, item.desc);
    }

}
