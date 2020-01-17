package com.zupu.zp.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.myactivity.BianJActivity;
import com.zupu.zp.myactivity.CreateZpActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/12 11:08
 * update: $date$
 */
public class BookOneFragment extends BaseFragment {
    private View view;
    private TextView bookname;
    private TextView wb1;
    private TextView wb2;
    private RelativeLayout rewb,fmd;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String treeid;

    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.bookoonelayout, container, false);
        return view;
    }

    @Override
    public void initview() {
        bookname=view.findViewById(R.id.bookname);
        wb1=view.findViewById(R.id.wb1);
        wb2=view.findViewById(R.id.wb2);
        rewb=view.findViewById(R.id.rewb);
        fmd=view.findViewById(R.id.fmd);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences=loging;
        editor = sharedPreferences.edit();
    }

    @Override
    public void initdata() {
        int fm = sharedPreferences.getInt("fm", 0);
        switch (fm){
            case 1:
                fmd.setBackgroundResource(R.drawable.jiapuone);
                break;
            case 2:
                fmd.setBackgroundResource(R.drawable.jiaputwo);
                break;
            case 3:
                fmd.setBackgroundResource(R.drawable.jiaputhire);
                break;
            case 4:
                fmd.setBackgroundResource(R.drawable.jiapufour);
                break;

        }


        Bundle bundle =this.getArguments();//得到从Activity传来的数据
        String mess = null;
        if(bundle!=null){
            mess = bundle.getString("xname");
            String creatname= bundle.getString("createname");
            String edit_time= bundle.getString("time");
            treeid = bundle.getString("treeid");
            Log.e("传值", mess+"创"+creatname+"time:"+edit_time );
            if (!mess.equals("")&&!mess.equals("null"))
            //bookname.setText(mess+"");
            reSizeTextView(bookname,mess,400);
            if (!creatname.equals("")&&!creatname.equals("null"))
            {
                wb2.setText(creatname);
            }else {
                wb2.setText(creatname);
            }

            if (!edit_time.equals("null")&&!edit_time.equals(""))
            wb1.setText(edit_time);

        }
        //点击跳转编辑
         rewb.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getActivity(), BianJActivity.class);
                 intent.putExtra("flag",1);
                 intent.putExtra("bookname",bookname.getText().toString());
                 intent.putExtra("wb1",wb1.getText().toString());
                 intent.putExtra("wb2",wb2.getText().toString());
                 intent.putExtra("treeid",treeid);
                 startActivityForResult(intent,1001);
             }
         });
        // 参数： TextView textView, int autoSizeTextType


    }

    @Override
    public void initlinsenter() {

    }

    @Override
    public void persenter() {

    }

    @Override
    public void sucecess(Object o) {


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1001:
                if (resultCode == getActivity().RESULT_OK) {
                    bookname.setText(data.getStringExtra("bookname"));
                    if (data.getStringExtra("wb1")!=null)
                    wb1.setText(data.getStringExtra("wb1"));
                    wb2.setText(data.getStringExtra("wb2"));
                    //latePresentation
                }
                break;
        }
    }

    private void reSizeTextView(TextView textView, String text, float maxWidth) {
        Paint paint = textView.getPaint();
        float textWidth = paint.measureText(text);
        int textSizeInDp = 30;

        if (textWidth > maxWidth) {
            for (; textSizeInDp > 0; textSizeInDp--) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeInDp);
                paint = textView.getPaint();
                textWidth = paint.measureText(text);
                if (textWidth <= maxWidth) {
                    break;
                }
            }
        }
        textView.setText(text);
        textView.invalidate();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reEventBus(Object o) {

        if (o instanceof Integer) {
            Integer a = (Integer) o;
            if (a==3001){
                fmd.setBackgroundResource(R.drawable.jiapuone);
                editor.putInt("fm",1);
                editor.commit();
            }else if (a==3002){
                fmd.setBackgroundResource(R.drawable.jiaputwo);
                editor.putInt("fm",2);
                editor.commit();
            }else if (a==3003){
                fmd.setBackgroundResource(R.drawable.jiaputhire);
                editor.putInt("fm",3);
                editor.commit();
            }else if (a==3004){
                fmd.setBackgroundResource(R.drawable.jiapufour);
                editor.putInt("fm",4);
                editor.commit();
            }

        }

    }
}
