package com.matto.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.common.ui.BaseActivity;
import com.matto.R;

import butterknife.Bind;


public class MainActivity extends BaseActivity {
    
    @Bind(R.id.image)
    ImageView imageView;
    
    private String url = "http://nuuneoi.com/uploads/source/playstore/cover.jpg";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitleBar("标题");
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInitView() {
        Glide.with(this).load(url).placeholder(R.drawable.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);

    }
}
