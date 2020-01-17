package com.zupu.zp.entity;

import android.app.Application;


public class EnContext {

    private static final Application INSTANCE = ZegoApplication.zegoApplication;


    public static Application get() {
        return INSTANCE;
    }
}
