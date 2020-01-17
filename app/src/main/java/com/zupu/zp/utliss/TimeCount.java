package com.zupu.zp.utliss;

import android.widget.TextView;

import com.zupu.zp.R;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/14 21:02
 * update: $date$
 */
public class TimeCount extends CountDownTimer {
    private TextView tvCode;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public TimeCount(long millisInFuture, long countDownInterval, TextView tv) {
        super(millisInFuture, countDownInterval);
        this.tvCode = tv;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        tvCode.setBackgroundResource(R.drawable.selecterdjs);
        tvCode.setTextSize(13);
        tvCode.setText(millisUntilFinished / 1000 + "s");
        tvCode.setClickable(false);
    }

    @Override
    public void onFinish() {
        tvCode.setBackgroundResource(R.drawable.selecterdjs);
        tvCode.setTextSize(13);
        tvCode.setText("再次获取");
        tvCode.setClickable(true);
    }
}