package com.zupu.zp.utliss;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/9/4 21:13
 * update: $date$
 */
public class NetworkUtils {


    //网络判断
    public static boolean isNet(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo!=null&&networkInfo.isConnected()){
            return true;
        }
        return false;
    }
    //若没有网络时调用该方法
    public void setNet(final Context context){
       /* AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(“设置”);
        builder.setMessage(“是否打开网络”);
        builder.setPositiveButton(“确定”, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                context.startActivity(intent);
            }
        });
        builder.show();*/

    }



}
