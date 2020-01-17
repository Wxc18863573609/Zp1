package com.zupu.zp.utliss;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/16 13:28
 * update: $date$
 */
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.zupu.zp.R;

/**
 * 通用加载提示框MyProgressDialog类
 */
public class MyProgressDialog extends Dialog {

    private ImageView mLoadingImg;
    private TextView mMesssageTV;

    public MyProgressDialog(Context context) {
        super(context, R.style.transparency_theme_light);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_dialog);
        mLoadingImg = (ImageView) findViewById(R.id.loadingimg);
        mMesssageTV = (TextView) findViewById(R.id.messagetv);
    }

    /**
     * 当没有消息时只展示转圈
     *
     * @param message
     */
    public void showMessage(String message) {
        try {
            super.show();
            if (!TextUtils.isEmpty(message)) {
                mMesssageTV.setText(message);
                mMesssageTV.setVisibility(View.VISIBLE);
            } else {
                mMesssageTV.setText("");
                mMesssageTV.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        mLoadingImg.startAnimation(animation);
    }
}

