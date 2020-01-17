package com.zupu.zp.base.mvp_no_dagger;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp.IcontClass;
import com.zupu.zp.base.mvp.Persenterimpl;
import com.zupu.zp.bean.ParmesBena;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.AndroidBug5497Workaround;
import com.zupu.zp.utliss.Bradcastrecerver;
import com.zupu.zp.utliss.LanguageUtil;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import taobe.tec.jcc.JChineseConvertor;


/**
 * description: dell
 * author: 王新昌
 * date: 2019/7/30 14:18
 * update: $date$
 */
public abstract class BaseActivity extends AppCompatActivity implements IcontClass.Iview {

    private Context mContext = this;
    // 中文 英文 日语 德语
    private static final String[] language = {"zh_CN","TW","ja", "de"};
    public static final String CHINESE = language[0];
    public static final String ENGLISH = language[1];
    private String type;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String TAG="abcde";
    public static ParmesBena parmesBena;
    public abstract int initlayout();
    public abstract void initview();
    public abstract void initdata();
    public abstract void initlisenter();
    public abstract void persenter();
    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;
    // 用来计算返回键的点击间隔时间
    private long exitTime = 0;
    private Bradcastrecerver receiver;
    public Persenterimpl persenterimpl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
       persenterimpl=new Persenterimpl();
        persenterimpl.onAtach(this);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        editor = sharedPreferences.edit();
        setContentView(initlayout());
        AndroidBug5497Workaround.assistActivity(this);
     //   KeyBoardListener.getInstance(this).init();
        parmesBena = new ParmesBena();

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        ImmersionBar.with(this).init();
        initview();
        initdata();
        initlisenter();
        setnetwokr();
        persenter();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void setNet(Object o){
        if (o instanceof Boolean)
        {
           boolean boo=(boolean)o;
           if (boo){
              // Toast.makeText(this, "有网", Toast.LENGTH_SHORT).show();
           }else {
              // Toast.makeText(this, "暂无网络", Toast.LENGTH_SHORT).show();
           }

        }
    }
     //判断网络
       public void setnetwokr(){
         receiver = new Bradcastrecerver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,filter);


    }
    //事件分发   用来拦截0.3秒内重复点击
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //防误触
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (isFastDoubleClick()) {
                return true;
            }
        }

        /**
         * 只要点击非Editext  都会收起软键盘
         *
         * @param
         * @return
         */
        if (!baseDispatch) {
            return super.dispatchTouchEvent(ev);
        }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev) && hideView(ev)) {
                InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        try {
            // 必不可少，否则所有的组件都不会有TouchEvent了
            if (getWindow().superDispatchTouchEvent(ev)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.dispatchTouchEvent(ev);
    }

    //判断是不是在0.3秒内重复点击两次
    public boolean isFastDoubleClick() {
        if ((System.currentTimeMillis() - exitTime) > 300) {
            exitTime = System.currentTimeMillis();
            return false;
        } else {
            return true;
        }
    }
    public  boolean isFastClick() {
        boolean flag = false;
        long curClickTime = System.currentTimeMillis();
        if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
            flag = true;
        }
        lastClickTime = curClickTime;
        return flag;
    }


    /**
     * 弹出软键盘
     */
    public void showSoftInput(final View v) {
        if (v != null) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    InputMethodManager inputManager = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.showSoftInputFromInputMethod(getWindow().getDecorView().getWindowToken(), InputMethodManager.RESULT_SHOWN);
                    inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }, 200);
        }

    }

    private boolean baseDispatch = true;

    public void setBaseDispatch(boolean bool) {
        baseDispatch = bool;
    }

    /**
     * 收起软键盘
     */
    public void colseSoftInputMethod(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }


//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        if (!baseDispatch) {
//            return super.dispatchTouchEvent(ev);
//        }
//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//            View v = getCurrentFocus();
//            if (isShouldHideInput(v, ev) && hideView(ev)) {
//                InputMethodManager imm = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
//                }
//            }
//            return super.dispatchTouchEvent(ev);
//        }
//        try {
//            // 必不可少，否则所有的组件都不会有TouchEvent了
//            if (getWindow().superDispatchTouchEvent(ev)) {
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return onTouchEvent(ev);
//    }

    protected boolean hideView(MotionEvent event) {
        return true;
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        receiver = null;
        persenterimpl.onDtach();
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
    }


    /**
     * 设置语言
     *
     * @param lauType
     */
    private void set(String lauType) {
// 本地语言设置
        Locale myLocale = new Locale(lauType);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }




    /**
     * 初始化国际化语言，繁体字和简体字
     */
    public void initI18() {
        Resources resources = getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        if (LanguageUtil.getCountry(getApplicationContext()).equals("TW")) {
            config.locale = Locale.TAIWAN;
            Toast.makeText(mContext, "繁体", Toast.LENGTH_SHORT).show();
        } else {
            config.locale = Locale.CHINESE;
            Toast.makeText(mContext, "简体", Toast.LENGTH_SHORT).show();
        }
        resources.updateConfiguration(config, dm);
    }




}
