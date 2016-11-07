package com.matto.net;

import com.common.control.HttpControl;
import com.matto.pojo.Gank;

import retrofit2.Call;


/**
 * author miekoz on 2016/3/21.
 * email  meikoz@126.com
 */
public class ServiceFactory {

    private static MainService mService;

    public static MainService getMainIns(){
        if (mService == null){
            mService = HttpControl.getIns().createService(MainService.class);
        }
        return mService;
    }

    public Call<Gank> getBenefitsGoods(int size, int page){
       return mService.getMainAndroid(size,page);
    }

}
