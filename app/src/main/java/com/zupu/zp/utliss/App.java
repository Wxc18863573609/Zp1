package com.zupu.zp.utliss;

import android.app.Application;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zego.zegoliveroom.ZegoLiveRoom;
import com.zego.zegoliveroom.callback.IZegoInitSDKCompletionCallback;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/10/15 11:09
 * update: $date$
 */
public class App extends Application {
    ZegoLiveRoom  g_ZegoApi;
    public static  App mApplication;
    public static final long appId = 3030559298L;
    public static final byte[] appSign =new byte[]
            {       (byte)0x3e,(byte)0x27,(byte)0xe0,(byte)0x97,(byte)0xba,(byte)0x50,
                    (byte)0xc1,(byte)0xd0, (byte)0xde,(byte)0x46,(byte)0xe8, (byte)0x44,
                    (byte)0x73,(byte)0xd2,(byte)0x8f,(byte)0x9d,(byte)0x14,
                    (byte)0x35, (byte)0x14,(byte)0x0f,(byte)0xc9,(byte)0xe8,
                    (byte)0xcc,(byte)0xf8,(byte)0x4b,(byte)0xbc, (byte)0xb8,
                    (byte)0x5d,(byte)0x15,(byte)0xa2,(byte)0xec,(byte)0xc1 };
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        ZegoLiveRoom.setSDKContext(new ZegoLiveRoom.SDKContextEx() {

            @Override
            public long getLogFileSize() {
                return 0;  // 单个日志文件的大小，必须在 [5M, 100M] 之间；当返回 0 时，表示关闭写日志功能，不推荐关闭日志。
            }

            @Nullable
            @Override
            public String getSubLogFolder() {
                return null;
            }

            @Override
            public String getSoFullPath() {

                return null; // return null 表示使用默认方式加载 libzegoliveroom.so
                // 此处可以返回 so 的绝对路径，用来指定从这个位置加载 libzegoliveroom.so，确保应用具备存取此路径的权限
            }

            @Override
            public String getLogPath() {
                return null; //  return null 表示日志文件会存储到默认位置，如果返回非空，则将日志文件存储到该路径下，注意应用必须具备存取该目录的权限
            }

            @Override
            public Application getAppContext() {
                return mApplication; // android上下文. 不能为null
            }
        });

        // 当 App 集成完成后，再向 ZEGO 申请正式环境。
        ZegoLiveRoom.setTestEnv(true); // 为 true 则说明开启测试环境，为 false 则说明使用正式环境

          g_ZegoApi = new ZegoLiveRoom();
        // 初始化sdk, appID与appSign 开发者如果还没有申请, 可通过 <a>https://console.zego.im/acount/login</a> 申请 AppID
        // AppID 和 AppSign 由 ZEGO 分配给各 App。其中，为了安全考虑，建议将 AppSign 存储在 App 的业务后台，需要使用时从后台获取
        // 如果不需要再继续使用 SDK 可调用 g_ZegoApi.unInitSDK() 释放SDK
        g_ZegoApi.initSDK(appId, appSign, new IZegoInitSDKCompletionCallback() {
            @Override
            public void onInitSDK(int errorCode) {
                // errorCode 非0 代表初始化sdk失败
                // 具体错误码说明请查看<a> https://doc.zego.im/CN/308.html </a>
                Log.e("wang", "onInitSDK: "+errorCode );
            }
        });
        //g_ZegoApi.unInitSDK();
    }



    /**
     * 返回上下文
     *
     * @return
     */
   public static Context getContext() {
        return mApplication;
    }
}
