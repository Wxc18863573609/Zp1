package com.zupu.zp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/*import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;*/
import com.zupu.zp.R;
import com.zupu.zp.bean.Filterbean;

import java.util.ArrayList;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/7 09:25
 * update: 滤镜适配器
 */
public class UserChartAdapter extends RecyclerView.Adapter<UserChartAdapter.Holders> {
    ArrayList<String> list;
    Context context;

    public UserChartAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public UserChartAdapter.Holders onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.chart_itm, null);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(UserChartAdapter.Holders holders, final int i) {
           // holders.ltext.setText(list.get(i).getuname());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holders extends RecyclerView.ViewHolder {
        TextView ltext;
        ImageView limg;
        public Holders( View itemView) {
            super(itemView);
            ltext=itemView.findViewById(R.id.chart_username);
            limg=itemView.findViewById(R.id.chart_content);
        }
    }

}
