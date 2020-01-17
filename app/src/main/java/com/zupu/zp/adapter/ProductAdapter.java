package com.zupu.zp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zupu.zp.R;
import com.zupu.zp.bean.Giftnumberbean;
import com.zupu.zp.bean.ListBean;

import java.util.ArrayList;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/7 09:25
 * update: 数量适配器
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Holders> {
    ArrayList<ListBean> listBeans;
    Context context;
    private int flag=-1;
    private int flag1=-1;

    public ProductAdapter(ArrayList<ListBean> listBeans, Context context) {
        this.listBeans = listBeans;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.Holders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.productnamelaout, null);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapter.Holders holders, final int i) {
            holders.giftnumber.setText(listBeans.get(i).getProductName());
        //    holders.gyy.setText(listBeans.get(i).getNumbername());

        if(flag == i){
           holders.itemView.setBackgroundColor(Color.parseColor("#504A4A"));
          //  holders.giftnumber.setTextColor(Color.parseColor("#3087ea"));
        }else {
            holders.itemView.setBackgroundColor(Color.parseColor("#3F2727"));
           // holders.giftnumber.setTextColor(Color.parseColor("#ffffff"));
        }
        holders.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
              if(action== MotionEvent.ACTION_DOWN){
                  flag=i;
                  onItemclick.getposition(i);
              }
                if(action== MotionEvent.ACTION_UP){
                    holders.itemView.setBackgroundColor(Color.parseColor("#3F2727"));
                    notifyDataSetChanged();
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBeans.size();
    }
    public void setColor(int position){
        this.flag=position;
        this.flag1=position;
        notifyDataSetChanged();
    }

    public class Holders extends RecyclerView.ViewHolder {
        TextView giftnumber;

        public Holders(@NonNull View itemView) {
            super(itemView);
            giftnumber=itemView.findViewById(R.id.productname);

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
