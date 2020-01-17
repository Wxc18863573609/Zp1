package com.zupu.zp.lianmai.ui;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.zupu.zp.R;
import com.zupu.zp.databinding.ActivityJoinLiveLoginPublishBinding;
import com.zupu.zp.lianmai.ZGJoinLiveHelper;
import com.zupu.zp.testpakeyge.AppLogger;
import com.zupu.zp.testpakeyge.BaseActivitys;


public class JoinLiveLoginPublishUI extends BaseActivitys {

    private ActivityJoinLiveLoginPublishBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_live_login_publish);

        // 设置当前 UI 界面左上角的点击事件，点击之后结束当前 Activity，返回到"创建直播"页面
        binding.goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    // 登录房间
    public void onClickLoginRoomAndPublish(View view){

        String roomID = ZGJoinLiveHelper.PREFIX + binding.edRoomId.getText().toString();
        if (!"".equals(roomID)) {
            jumpPublish(roomID);
        } else {
            Toast.makeText(JoinLiveLoginPublishUI.this, getString(R.string.tx_room_id_is_no_null), Toast.LENGTH_SHORT).show();
            AppLogger.getInstance().i(JoinLiveLoginPublishUI.class, getString(R.string.tx_room_id_is_no_null));
        }
    }


    // 跳转到主播推流页面
    public void jumpPublish(String roomID){
        JoinLiveAnchorUI.actionStart(JoinLiveLoginPublishUI.this, roomID);
    }

    /**
     * 供其他Activity调用，进入本专题的方法
     *
     * @param activity
     */
    public static void actionStart(Activity activity) {
        Intent intent = new Intent(activity, JoinLiveLoginPublishUI.class);
        activity.startActivity(intent);
    }
}
