package com.common.utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * author meikoz on 2016/4/14.
 * email  meikoz@126.com
 */
public class ActivityUtils {

    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {

        if (fragment == null || fragment == fragmentManager.findFragmentById(frameId))
            return;

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.commit();
    }
}
