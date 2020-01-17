package com.zupu.zp.bean;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import com.zupu.zp.R;

import java.util.ArrayList;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/9 19:47
 * update: $date$
 */
public class DaocterAdapters extends RecyclerView.Adapter<DaocterAdapters.Holders> {
    ArrayList<String> drocterlist;
    Context context;
    private int flag=-1;

    public DaocterAdapters(ArrayList<String> drocterlist, Context context) {
        this.drocterlist = drocterlist;
        this.context = context;
    }

    @NonNull
    @Override
    public Holders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.tlayout, null);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull Holders holders, final int i) {
        holders.dorctername.setText(drocterlist.get(i));
      /*  Glide.with(context).load(drocterlist.get(i).getImagePic()).into(holders.dorcterimg);
        if(flag == i){
            holders.dorctername.setBackgroundColor(Color.parseColor("#3087ea"));
        }else {

            holders.dorctername.setBackgroundColor(Color.parseColor("#D0F17676"));

        }
        holders.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=i;
                onItemclick.getposition(i);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return drocterlist.size();
    }
    public void setColor(int position){
        this.flag=position;
        notifyDataSetChanged();
    }

    public class Holders extends RecyclerView.ViewHolder {
        TextView dorctername;
       // ImageView dorcterimg;
        public Holders(@NonNull View itemView) {
            super(itemView);
            dorctername=itemView.findViewById(R.id.testa);
          // dorcterimg=itemView.findViewById(R.id.dorcterimg);
        }
    }
   /* public interface OnItemclick{
        void getposition(int position);
    }
    OnItemclick onItemclick;
    public void setOnItemclick(OnItemclick onItemclick){
        this.onItemclick=onItemclick;
    }*/
}
