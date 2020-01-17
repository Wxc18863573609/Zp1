package com.zupu.zp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/12 11:08
 * update: $date$
 */
public class BookSixFragment extends BaseFragment {
    private View view;
    private TextView bookname;


    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.bookfouslayout, container, false);
        return view;
    }

    @Override
    public void initview() {
        bookname=view.findViewById(R.id.bookname);
        bookname.setText(R.string.shixibiaostr);
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
