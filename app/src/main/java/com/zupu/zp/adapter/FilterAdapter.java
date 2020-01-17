package com.zupu.zp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zupu.zp.R;
import com.zupu.zp.bean.Filterbean;

import java.util.ArrayList;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/7 09:25
 * update: 滤镜适配器
 */
public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.Holders> {
    ArrayList<Filterbean> filterlist;
    Context context;
    private int flag=-1;
    public FilterAdapter(ArrayList<Filterbean> filterlist, Context context) {
        this.filterlist = filterlist;
        this.context = context;
    }

    @NonNull
    @Override
    public FilterAdapter.Holders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.filterlayout, null);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterAdapter.Holders holders, final int i) {
            holders.ltext.setText(filterlist.get(i).getFname());
            Glide.with(context).load(filterlist.get(i).getImage())
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(holders.limg);
        if(flag == i){

            holders.ltext.setTextColor(Color.parseColor("#3087ea"));
        }else {

            holders.ltext.setTextColor(Color.parseColor("#ffffff"));

        }
            holders.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag=i;
                    onItemclick.getposition(i);
                }
            });
    }

    @Override
    public int getItemCount() {
        return filterlist.size();
    }
    public void setColor(int position){
        this.flag=position;
        notifyDataSetChanged();
    }

    public class Holders extends RecyclerView.ViewHolder {
        TextView ltext;
        ImageView limg;
        public Holders(@NonNull View itemView) {
            super(itemView);
            ltext=itemView.findViewById(R.id.ltext);
            limg=itemView.findViewById(R.id.limg);
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