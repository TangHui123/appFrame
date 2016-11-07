package com.matto.app;

import com.common.EasyApplication;
import com.common.control.LogicProxy;
import com.matto.logic.LoginLogic;

/**
 * author miekoz on 2016/3/17.
 * email  meikoz@126.com
 */
public class App extends EasyApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        LogicProxy.getInstance().init(LoginLogic.class);
    }
}
