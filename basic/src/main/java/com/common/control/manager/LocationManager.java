package com.common.control.manager;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * author meikoz on 2016/4/13.
 * email  meikoz@126.com
 */
public class LocationManager {

    private double latitude, longitude;
    private Context mContext;
    private LocationCallback callback;

    private BDLocationListener locationListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location != null) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                if (callback != null) callback.onLocationFetched(latitude, longitude);
            } else {
                if (callback != null)
                    callback.onError("无法获取到您当前的位置");
            }
        }
    };

    public LocationManager(Context context) {
        this.mContext = context;
    }

    public void doStartLocation() {
        LocationClient mLocClient = new LocationClient(mContext);
        mLocClient.registerLocationListener(locationListener);
        LocationClientOption option = new LocationClientOption();
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setCoorType("bd09ll");

        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setScanSpan(1000);

        //可选，设置是否需要地址信息，默认不需要
        option.setIsNeedAddress(true);

        //可选，默认false,设置是否使用gps
        option.setOpenGps(true);

        //可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setLocationNotify(true);

        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);

        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIsNeedLocationPoiList(true);

        //可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.setIgnoreKillProcess(false);

        //可选，默认false，设置是否收集CRASH信息，默认收集
        option.SetIgnoreCacheException(false);

        //可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setEnableSimulateGps(false);
        mLocClient.setLocOption(option);

        //开始定位
        mLocClient.start();
    }

    public void startLocation(LocationCallback callback) {
        this.callback = callback;
        doStartLocation();
    }

    public interface LocationCallback {

        void onLocationFetched(double latitude, double longitude);

        void onError(String error);
    }
}
