package com.zupu.zp.utliss;

import android.app.Activity;
import android.widget.Toast;

import com.zego.zegoliveroom.callback.IZegoInitSDKCompletionCallback;
import com.zego.zegoliveroom.constants.ZegoConstants;
import com.zupu.zp.entity.ZGBaseHelper;
import com.zupu.zp.testpakeyge.AppLogger;
import com.zupu.zp.testpakeyge.CustomDialog;
import com.zupu.zp.testpakeyge.ZegoUtil;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/10/26 10:11
 * update: $date$
 */
public class InitSDKutil {
    private int roomRole = ZegoConstants.RoomRole.Anchor;
    private String flag;
    public void initsdks(final Activity context){
        // 防止用户点击，弹出加载对话框
        CustomDialog.createDialog("初始化SDK中...",  context).show();
       // flag=flags;
        // 调用sdk接口, 初始化sdk
        boolean results = ZGBaseHelper.sharedInstance().initZegoSDK(null,null,null,ZegoUtil.getAppID(), ZegoUtil.getAppSign(), ZegoUtil.getIsTestEnv(), new IZegoInitSDKCompletionCallback() {
            @Override
            public void onInitSDK(int errorCode) {
                // 关闭加载对话框
                CustomDialog.createDialog(context).cancel();
                // errorCode 非0 代表初始化sdk失败
                // 具体错误码说明请查看<a> https://doc.zego.im/CN/308.html </a>
                if (errorCode == 0) {
                    AppLogger.getInstance().i(context.getClass(), "初始化zegoSDK成功");
                    Toast.makeText(context, "初始化成功", Toast.LENGTH_SHORT).show();
                    // 初始化成功，跳转到登陆房间页面。
                    //jumpLoginRoom(roomID,context,starmID);
                } else {
                    AppLogger.getInstance().i(context.getClass(), "初始化sdk失败 错误码 : %d", errorCode);
                    Toast.makeText(context, "初始化失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 如果接口调用失败，也需要关闭加载对话框
        if (!results) {
            CustomDialog.createDialog(context).cancel();
        }
    }
}
