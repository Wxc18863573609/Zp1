package com.zupu.zp.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;

import static com.zupu.zp.entity.ZegoApplication.indexa;
import static com.zupu.zp.entity.ZegoApplication.listindex;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/12 11:08
 * update: $date$
 */
public class BookFousFragment extends BaseFragment {
  private   View  view;


    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.bookfouslayout, container, false);
        return view;
    }

    @Override
    public void initview() {

    }

    @Override
    public void initdata() {


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
}
