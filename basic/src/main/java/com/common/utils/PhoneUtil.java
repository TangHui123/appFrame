package com.common.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class PhoneUtil {

    //调起拨号键
    public static void startPanel(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    //直接打电话
    public static void call(Context context, String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }
}
