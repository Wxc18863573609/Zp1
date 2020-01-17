package com.zupu.zp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zupu.zp.R;
import com.zupu.zp.bean.Filterbean;
import com.zupu.zp.bean.SearchBean;

import java.util.ArrayList;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/7 09:25
 * update: 直播封面搜索适配器
 */
public class LiveSearchAdapter extends RecyclerView.Adapter<LiveSearchAdapter.Holders> {
    ArrayList<SearchBean.ListBean> filterlist;
    Context context;
    private int flag=-1;

    public LiveSearchAdapter(ArrayList<SearchBean.ListBean> filterlist, Context context) {
        this.filterlist = filterlist;
        this.context = context;
    }

    @Override
    public LiveSearchAdapter.Holders onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.shaoppinglist_itm, null);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(LiveSearchAdapter.Holders holders, final int i) {

        String realName = filterlist.get(i).getUser().getRealName();
        //用星号替换最后一位
        realName=realName.replace(realName.charAt(realName.length()-1)+"","*");
            holders.productname.setText(realName);
            holders.content.setText(filterlist.get(i).getProductName());
            holders.chick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
        TextView productname;
        TextView content;
        RadioButton chick;
        public Holders( View itemView) {
            super(itemView);
            productname=itemView.findViewById(R.id.productname);
            content=itemView.findViewById(R.id.content);
            chick=itemView.findViewById(R.id.chick);
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
