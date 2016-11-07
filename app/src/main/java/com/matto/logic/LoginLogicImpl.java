package com.matto.logic;

import android.util.Log;

/**
 * author meikoz on 2016/4/13.
 * email  meikoz@126.com
 */
public class LoginLogicImpl implements LoginLogic {

    LoginView mView;

    @Override
    public void login(String name, String passwrod) {
        if (name.equals("zhangsan") && passwrod.equals(123)) {
            Log.d("cs", "登录成功,通知Activity");
            mView.onLoginSuccess();
        } else {
            Log.d("cs", "登录失败,通知UI");
            mView.onLoginSuccess();
        }
    }

    @Override
    public void attachView(LoginView mvpView) {
        this.mView = mvpView;
    }

}
