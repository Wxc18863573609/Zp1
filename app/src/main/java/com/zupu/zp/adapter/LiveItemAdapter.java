package com.zupu.zp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
/*import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;*/
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zupu.zp.R;
import com.zupu.zp.bean.LiveBean;

import java.util.List;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/7 09:25
 * update: live适配器
 */
public class LiveItemAdapter extends XRecyclerView.Adapter<LiveItemAdapter.Holders> {
    List<LiveBean.ListBean> filterlist;
    Context context;

    public LiveItemAdapter(List<LiveBean.ListBean> filterlist, Context context) {
        this.filterlist = filterlist;
        this.context = context;
    }

    @Override
    public LiveItemAdapter.Holders onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, com.zupu.zp.R.layout.live_itm, null);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(LiveItemAdapter.Holders holders, final int i) {
        holders.live_title.setText(filterlist.get(i).getTitle()+"");
        if (filterlist.get(i).getExtPara1()!=null){
            if (filterlist.get(i).getIsplayback().equals("0")&&filterlist.get(i).getExtPara1().equals("0")){
                Glide.with(context).load(com.zupu.zp.R.drawable.zbz)
                        .into(holders.live_zt);
            }else if(filterlist.get(i).getIsplayback().equals("0")&&filterlist.get(i).getExtPara1().equals("1")){
                Glide.with(context).load(com.zupu.zp.R.drawable.jypk)
                        .into(holders.live_zt);
            }else if (filterlist.get(i).getIsplayback().equals("1")){
                Glide.with(context).load(R.drawable.hb)
                        .into(holders.live_zt);
            }
        }else {
            if (filterlist.get(i).getIsplayback().equals("0")){
                Glide.with(context).load(com.zupu.zp.R.drawable.zbz)
                        .into(holders.live_zt);
            }else if(filterlist.get(i).getIsplayback().equals("0")){
                Glide.with(context).load(com.zupu.zp.R.drawable.jypk)
                        .into(holders.live_zt);
            }
        }


        Glide.with(context).load(filterlist.get(i).getCover())
                .error(com.zupu.zp.R.drawable.zhanwei)
                .placeholder(com.zupu.zp.R.drawable.zhanwei)
                .into(holders.live_fm);
        holders.live_address.setText(filterlist.get(i).getExtPara2()+"");
        holders.live_persennumber.setText(filterlist.get(i).getUserNum()+"人");
            holders.itemView.setOnClickListener(new View.OnClickListener() {
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

    public class Holders extends RecyclerView.ViewHolder {
        TextView live_title;
        ImageView live_zt;
        ImageView live_fm;
        TextView live_address;
        TextView live_persennumber;
        public Holders( View itemView) {
            super(itemView);
            live_title=itemView.findViewById(com.zupu.zp.R.id.live_title);
            live_zt=itemView.findViewById(com.zupu.zp.R.id.live_zt);
            live_fm=itemView.findViewById(com.zupu.zp.R.id.live_fm);
            live_address=itemView.findViewById(com.zupu.zp.R.id.live_adders);
            live_persennumber=itemView.findViewById(com.zupu.zp.R.id.live_persennumber);
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
