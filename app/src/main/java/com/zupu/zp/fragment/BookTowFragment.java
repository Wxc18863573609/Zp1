package com.zupu.zp.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.zupu.zp.R;
import com.zupu.zp.adapter.BookLbAdapter;
import com.zupu.zp.adapter.MyCardadpter;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.bean.BookBean;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.bean.ZpBean;
import com.zupu.zp.utliss.ImageUtli;
import com.zupu.zp.utliss.UrlCount;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.zupu.zp.myactivity.CreateZpActivity.vp;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/12 11:08
 * update: $date$
 */
public class BookTowFragment extends BaseFragment {
    private View view;
    Bundle bundle;
    int lastItemPosition;
    private RecyclerView bookrecycel, bookrecyce2;

    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.booktowlayout, container, false);
        return view;
    }

    @Override
    public void initview() {
        bookrecycel = view.findViewById(R.id.bookrecycel);
        //  bundle = this.getArguments();//得到从Activity传来的数据
    }

    @Override
    public void initdata() {
        bundle = this.getArguments();//得到从Activity传来的数据
        //适配器设置
        if (bundle != null) {
            ArrayList<BookBean> bookBeans = (ArrayList<BookBean>) bundle.getSerializable("date");

            bookrecycel.setLayoutManager(new LinearLayoutManager(getActivity()));
            BookLbAdapter bookLbAdapter = new BookLbAdapter(bookBeans, null, getActivity());
            bookLbAdapter.setOnItemclick(new BookLbAdapter.OnItemclick() {
                @Override
                public void getposition(int position) {
                    Log.e("数据",bookBeans.get(position).getIndexs()+"");
                    EventBus.getDefault().post(bookBeans.get(position).getIndexs());

                }
            });
            bookrecycel.setAdapter(bookLbAdapter);

        } else {
            Toast.makeText(getActivity(), "null", Toast.LENGTH_SHORT).show();
        }

        bookrecycel.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                //判断是当前layoutManager是否为LinearLayoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //获取最后一个可见view的位置
                     lastItemPosition = linearManager.findLastVisibleItemPosition();
                     Log.e("我的梦", ""+lastItemPosition );
                }
            }
        });


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


/*
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reEventBus(Object o) {
        if (o instanceof String){
             String name= (String)o;
            Log.e("euntebus", name+"?????" );
        }
    *//*    bookrecycel.setLayoutManager(new LinearLayoutManager(getActivity()));
        BookLbAdapter bookLbAdapter = new BookLbAdapter(data, null, getActivity());
        bookLbAdapter.setOnItemclick(new BookLbAdapter.OnItemclick() {
            @Override
            public void getposition(int position) {
                if (position == 0) {
                    vp.setCurrentItem(0);
                } else {
                    vp.setCurrentItem(2);
                }
            }
        });
        bookrecycel.setAdapter(bookLbAdapter);
*//*
    }*/
}
