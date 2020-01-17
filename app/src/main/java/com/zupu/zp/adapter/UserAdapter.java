package com.zupu.zp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zupu.zp.R;
import com.zupu.zp.bean.GetUserCount;
import com.zupu.zp.bean.GetuserCountBean;

import java.util.ArrayList;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/7 09:25
 * update: 滤镜适配器
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.Holders> {
    ArrayList<GetuserCountBean> list;
    Context context;

    public UserAdapter(ArrayList<GetuserCountBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public UserAdapter.Holders onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.userheadlaout, null);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(UserAdapter.Holders holders, final int i) {

            Glide.with(context).load(list.get(i).getPhotoUrl())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(holders.limg);
            holders.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemclick.getposition(i);
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holders extends RecyclerView.ViewHolder {
        ImageView limg;
        public Holders( View itemView) {
            super(itemView);

            limg=itemView.findViewById(R.id.userimg);
        }
    }
    public interface OnItemclick{
        void getposition(int position);
    }
    OnItemclick onItemclick;
    public void setOnItemclick(OnItemclick onItemclick){
        this.onItemclick=onItemclick;
    }
}
