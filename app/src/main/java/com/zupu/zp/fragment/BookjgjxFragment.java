package com.zupu.zp.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.TextViewCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.myactivity.EdjieTextActivity;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/12 11:08
 * update: $date$
 */
public class BookjgjxFragment extends BaseFragment {
  private   View  view;
  private TextView titlename;
  private TextView bianji;
  private TextView textwb;
  String content;
  String type;
  String treeid;
    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.bookfaiwlayout, container, false);
        return view;
    }

    @Override
    public void initview() {
      titlename=view.findViewById(R.id.titlename);
      bianji=view.findViewById(R.id.bianji);
      textwb=view.findViewById(R.id.textwb);
    }

    @Override
    public void initdata() {
      titlename.setText(R.string.jgjxstr);
      Bundle bundle =this.getArguments();//得到从Activity传来的数据
      String mess = null;
      if(bundle!=null){
        mess = bundle.getString("data");
        Log.e("传值", mess+"" );
        treeid = bundle.getString("treeid");
        type = bundle.getString("type");
        content = bundle.getString("content");
        if (mess.length()<790){
          TextViewCompat.setAutoSizeTextTypeWithDefaults(textwb, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
          textwb.setTextSize(TypedValue.COMPLEX_UNIT_PX,38);
          textwb.setText(mess);
        }else {
          textwb.setText(mess);
          TextViewCompat.setAutoSizeTextTypeWithDefaults(textwb, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        }
      }
    }

    @Override
    public void initlinsenter() {

      //编辑
      bianji.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Intent intent = new Intent(getActivity(), EdjieTextActivity.class);
          intent.putExtra("textwb",textwb.getText());
          intent.putExtra("treeid",treeid);
          intent.putExtra("flag",5);
          intent.putExtra("type",type);
          intent.putExtra("content",content);
          startActivityForResult(intent,1001);
        }
      });
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
          textwb.setText(data.getStringExtra("jieshao"));
          //latePresentation


        }
        break;
    }
  }
}
