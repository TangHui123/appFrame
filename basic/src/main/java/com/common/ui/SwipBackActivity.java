package com.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.common.R;
import com.common.widget.SwipeBackLayout;

/**
 * author meikoz on 2016/4/1.
 * email  meikoz@126.com
 */
public abstract class SwipBackActivity extends BaseActivity {

    protected SwipeBackLayout mSwipBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSwipBack = (SwipeBackLayout) LayoutInflater.from(this).inflate(
                R.layout.base_swip_layout, null);
        mSwipBack.attachToActivity(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }

    // Press the back button in mobile phone
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, R.anim.base_slide_right_out);
    }
}
