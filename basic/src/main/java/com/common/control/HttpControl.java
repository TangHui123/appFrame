package com.common.control;

import com.common.Constant;
import com.common.EasyApplication;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author miekoz on 2016/3/17.
 * email  meikoz@126.com
 */
public class HttpControl {

    private static HttpControl mInstance;
    private Retrofit retrofit;

    public static HttpControl getIns(){
        if (mInstance == null){
            synchronized (HttpControl.class){
                if (mInstance == null) mInstance = new HttpControl();
            }
        }
        return mInstance;
    }

    public HttpControl(){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(EasyApplication.getInstance().gson))
                .client(okHttpClient)
                .build();
        
        
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    public  <T> T createService(Class<T> clz){
        return retrofit.create(clz);
    }
}
