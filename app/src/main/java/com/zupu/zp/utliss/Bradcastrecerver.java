package com.zupu.zp.utliss;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;


/**
 * description: dell
 * author: 王新昌
 * date: 2019/7/30 14:56
 * update: $date$
 */
public class Bradcastrecerver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            ConnectivityManager systemService = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = (NetworkInfo) intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (NetworkInfo.State.CONNECTED==info.getState()){
                EventBus.getDefault().postSticky(true);
            }else {
                EventBus.getDefault().postSticky(false);
            }

        }

    }
}
