package com.common.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.common.R;

import butterknife.ButterKnife;

/**
 * author meikoz on 2016/3/30.
 * email  meikoz@126.com
 */
public abstract class BaseActivity extends FragmentActivity {

    protected EasyToolBar mTitleBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        onInitView();
    }
    protected abstract int getLayoutResource();

    protected abstract void onInitView();

    protected void initTitleBar(String title) {
        findTitleBar();
        mTitleBar.setTitle(title);
    }

    private void findTitleBar() {
        mTitleBar = (EasyToolBar) findViewById(R.id.toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (intent != null && intent.getComponent() != null) {
            overridePendingTransition(R.anim.move_right_in_activity, R.anim.hold_long);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.hold_long, R.anim.move_right_out_activity);
    }
}
