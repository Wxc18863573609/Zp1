package com.zupu.zp.utliss;

import android.content.Context;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/9/2 15:46
 * update: $date$
 */
public class WXUtils {
    /**
     * APP_ID 替换为你的应用从官方网站申请到的合法appID
     */
    public static String APP_ID = "wx3c8adc40cdcd29e6";

    public static IWXAPI reg(Context context) {
        if (context != null) {
            //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
            IWXAPI wxapi = WXAPIFactory.createWXAPI(context, APP_ID, true);
            //注册到微信
            wxapi.registerApp(APP_ID);
            return wxapi;
        } else {
            return null;
        }
    }

    //判断是否安装过微信
    public static boolean success(Context context) {
        if (WXUtils.reg(context).isWXAppInstalled()) {
            return true;
        } else {
            return false;
        }
    }
}
