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
import com.zupu.zp.bean.Giftbean;
import com.zupu.zp.bean.Giftbeans;

import java.util.ArrayList;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/7 09:25
 * update: 礼物适配器
 */
public class GiftAdapter extends RecyclerView.Adapter<GiftAdapter.Holders> {
    ArrayList<Giftbeans.ListBean> listBeans;
    Context context;
    private int flag=-1;

    public GiftAdapter(ArrayList<Giftbeans.ListBean> listBeans, Context context) {
        this.listBeans = listBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public GiftAdapter.Holders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.gift_item, null);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull GiftAdapter.Holders holders, final int i) {
            holders.gname.setText(listBeans.get(i).getGiftName());
            holders.gprice.setText(listBeans.get(i).getAmount()+"积分");
            Glide.with(context).load(listBeans.get(i).getPicture())
                  //  .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .placeholder(R.drawable.zhanwei)
                    .error(R.drawable.zhanwei)
                    .into(holders.gimg);
        if(flag == i){

            holders.gname.setTextColor(Color.parseColor("#3087ea"));
        }else {

            holders.gname.setTextColor(Color.parseColor("#ffffff"));

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
        return listBeans.size();
    }
    public void setColor(int position){
        this.flag=position;
        notifyDataSetChanged();
    }

    public class Holders extends RecyclerView.ViewHolder {
        TextView gname;
        TextView gprice;
        ImageView gimg;
        public Holders(@NonNull View itemView) {
            super(itemView);
            gname=itemView.findViewById(R.id.gname);
            gprice=itemView.findViewById(R.id.gprice);
            gimg=itemView.findViewById(R.id.gimg);
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
