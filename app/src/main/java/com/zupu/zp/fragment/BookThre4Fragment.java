package com.zupu.zp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.bean.PhoneBean;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.bean.ZpBean;
import com.zupu.zp.utliss.UrlCount;

import java.util.HashMap;
import java.util.List;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/12 11:08
 * update: $date$
 */
public class BookThre4Fragment extends BaseFragment {
    private View view;
    private ImageView pic1;
    private ImageView pic2;
    private LinearLayout photoh1;
    private ImageView pic3;
    private ImageView pic4;
    private LinearLayout photoh2;
    private int flag=-1;
    String mess = null;

    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.bookthreelayout, container, false);
        return view;
    }

    @Override
    public void initview() {
        pic1=view.findViewById(R.id.pic1);
        pic2=view.findViewById(R.id.pic2);
        pic3=view.findViewById(R.id.pic3);
        pic4=view.findViewById(R.id.pic4);
        Bundle bundle =this.getArguments();//得到从Activity传来的数据
        if(bundle!=null){
            mess = bundle.getString("subjectId");
            List<ZpBean.PiclistBean> piclist = (List<ZpBean.PiclistBean>) bundle.getSerializable("list");
            Log.e("奥迪", piclist.size()+"" );
            for (int i = 0; i <piclist.size() ; i++) {
                if (piclist.get(i).getNo()==13){
                    Glide.with(getActivity()).load(piclist.get(i).getPicture()).error(R.drawable.zhanwei).into(pic1);
                }else if (piclist.get(i).getNo()==14){
                    Glide.with(getActivity()).load(piclist.get(i).getPicture()).error(R.drawable.zhanwei).into(pic2);
                }else if (piclist.get(i).getNo()==15){
                    Glide.with(getActivity()).load(piclist.get(i).getPicture()).error(R.drawable.zhanwei).into(pic3);
                }else if (piclist.get(i).getNo()==16){
                    Glide.with(getActivity()).load(piclist.get(i).getPicture()).error(R.drawable.zhanwei).into(pic4);
                }
            }

        }
    }

    @Override
    public void initdata() {
      //图一
      pic1.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              flag=1;
              oneUpPic();
          }
      });
        //图2
        pic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=2;
                oneUpPic();
            }
        });
        //图3
        pic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=3;
                oneUpPic();
            }
        });
        //图4
        pic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=4;
                oneUpPic();
            }
        });


    }

    @Override
    public void initlinsenter() {

    }

    @Override
    public void persenter() {

    }

    /*
    * page			int			页数

type			String			1家庭树，2族谱树

subject_id			int			对应家庭树的id

no			int			图片的排序

picture			String			图片路径


    * */
    @Override
    public void sucecess(Object o) {
        if (o instanceof Picbean){
            Picbean picbean= (Picbean)o;
            int code = picbean.getCode();
            if (code==0){
                if (flag==1){
                    Glide.with(getActivity()).load(picbean.getMediaUrl()).error(R.drawable.zhanwei).into(pic1);
                    getdata(13,picbean.getMediaUrl());
                }else if (flag==2){
                    Glide.with(getActivity()).load(picbean.getMediaUrl()).error(R.drawable.zhanwei).into(pic2);
                    getdata(14,picbean.getMediaUrl());
                }else if (flag==3){
                    Glide.with(getActivity()).load(picbean.getMediaUrl()).error(R.drawable.zhanwei).into(pic3);
                    getdata(15,picbean.getMediaUrl());
                }else if (flag==4){
                    Glide.with(getActivity()).load(picbean.getMediaUrl()).error(R.drawable.zhanwei).into(pic4);
                    getdata(16,picbean.getMediaUrl());
                }

            }
        }
        if (o instanceof PhoneBean){
            PhoneBean phoneBean=(PhoneBean)o;
            if (phoneBean.getCode()==0){
                Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(getActivity(), phoneBean.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

public void getdata(int no,String picture){
    HashMap<String, Object> map = new HashMap<>();
    map.put("page",4);
    map.put("type",1);
    map.put("subjectId",mess);
    map.put("no",no);
    map.put("picture",picture);
    persenterimpl.posthttp(UrlCount.URL_SevePic,map, PhoneBean.class);


}

}
