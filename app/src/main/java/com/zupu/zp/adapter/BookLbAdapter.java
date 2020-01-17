package com.zupu.zp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.zupu.zp.R;
import com.zupu.zp.bean.BookBean;
import com.zupu.zp.bean.Filterbean;
import com.zupu.zp.bean.PageBean;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.bean.ZpBean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.utliss.ImageUtli;
import com.zupu.zp.utliss.UrlCount;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import static com.zupu.zp.entity.ZegoApplication.index;
import static com.zupu.zp.entity.ZegoApplication.listindex;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/8/7 09:25
 * update: 族谱适配器
 */
public class BookLbAdapter extends RecyclerView.Adapter<BookLbAdapter.Holders> {
/*    List<ZpBean.FamilyListBean> list1=new ArrayList<>();
    List<ZpBean.FamilyListBean> list2=new ArrayList<>();
    List<ZpBean.FamilyListBean> list3=new ArrayList<>();
    List<ZpBean.FamilyListBean> list4=new ArrayList<>();
    List<ZpBean.FamilyListBean> list5=new ArrayList<>();
    List<ZpBean.FamilyListBean> list6=new ArrayList<>();
    List<ZpBean.FamilyListBean> list7=new ArrayList<>();
    List<ZpBean.FamilyListBean> list8=new ArrayList<>();
    List<ZpBean.FamilyListBean> list9=new ArrayList<>();
    List<ZpBean.FamilyListBean> list10=new ArrayList<>();
    List<ZpBean.FamilyListBean> list11=new ArrayList<>();
    List<ZpBean.FamilyListBean> list12=new ArrayList<>();*/
  //  ArrayList<List<ZpBean.FamilyListBean>> lists = new ArrayList<>();
    int a=0;
    List<BookBean> filterlist;
    List<ZpBean.FamilyListBean> list;
    Context context;
    private int flag=-1;

    public BookLbAdapter(List<BookBean> filterlist, List<ZpBean.FamilyListBean> list, Context context) {
        this.filterlist = filterlist;
        this.list = list;
        this.context = context;
     /*   for (int i = 0; i <list.size() ; i++) {
            switch (ZegoApplication.index-list.get(i).getRankingSeniority()+1){
                case  1:
                    list1.add(list.get(i));
                    break;
                case  2:
                    list2.add(list.get(i));
                    break;
                case  3:
                    list3.add(list.get(i));
                    break;
                case  4:
                    list4.add(list.get(i));
                    break;
                case  5:
                    list5.add(list.get(i));
                    break;
                case  6:
                    list6.add(list.get(i));
                    break;
                case  7:
                    list7.add(list.get(i));
                    break;
                case  8:
                    list8.add(list.get(i));
                    break;
                case  9:
                    list9.add(list.get(i));
                    break;
                case  10:
                    list10.add(list.get(i));
                    break;
                case  11:
                    list11.add(list.get(i));
                    break;
                case  12:
                    list12.add(list.get(i));
                    break;
            }
        }
        if (list1.size()!=0)
            lists.add(list1);
        if (list2.size()!=0)
            lists.add(list2);
        if (list3.size()!=0)
            lists.add(list3);
        if (list4.size()!=0)
            lists.add(list4);
        if (list5.size()!=0)
            lists.add(list5);
        if (list6.size()!=0)
            lists.add(list6);
        if (list7.size()!=0)
            lists.add(list7);
        if (list8.size()!=0)
            lists.add(list8);
        if (list9.size()!=0)
            lists.add(list9);
        if (list10.size()!=0)
            lists.add(list10);
        if (list11.size()!=0)
            lists.add(list11);
        if (list12.size()!=0)
            lists.add(list12);*/
    }

    @Override
    public BookLbAdapter.Holders onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.bookitm1, null);
        Holders holders = new Holders(view);
        return holders;
    }

    @Override
    public void onBindViewHolder(BookLbAdapter.Holders holders, final int i) {
              if (null!=filterlist.get(i).getListbean()){
                  //第几世去重
                  if (i-1>-1&&filterlist.get(i-1).getListbean()!=null&&filterlist.get(i).getListbean().getRankingSeniority()==filterlist.get(i-1).getListbean().getRankingSeniority())
                  {
                      holders.jishi.setText("");
                  }else {
                      holders.jishi.setText("第"+(ZegoApplication.index-filterlist.get(i).getListbean().getRankingSeniority()+1)+"世");
                  }
                  holders.name.setText(filterlist.get(i).getListbean().getSurname()+filterlist.get(i).getListbean().getName()+"");

                  for (int j = 0; j <listindex.size() ; j++) {
                      if (listindex.get(j).getData()!=null){
                          for (int k = 0; k <listindex.get(j).getData().size() ; k++) {
                              if ((listindex.get(j).getData().get(k).getSurname()+listindex.get(j).getData().get(k).getName()).equals(filterlist.get(i).getListbean().getSurname()+filterlist.get(i).getListbean().getName()))
                              {

                                  filterlist.get(i).setIndexs(listindex.get(j).getPag()+1);
                                  holders.pages.setText(listindex.get(j).getPag()+1+"");
                              }
                          }
                      }

                  }

              }else {
                  holders.jishi.setText("");
                  holders.name.setText(filterlist.get(i).getName());
              }


        for (int j = 0; j <listindex.size() ; j++) {
           if (listindex.get(j).getName().equals(filterlist.get(i).getName())){
               if (listindex.get(j).getName().equals(listindex.get(j-1).getName()))
               {
                   a=a+++a;
                 /*  holders.pages.setText(listindex.get(j).getPag()+"");
                   filterlist.get(i).setIndexs(listindex.get(j).getPag());*/
               }else {
                   if (listindex.get(j).getPag()+1>listindex.size())
                   {
                       holders.pages.setText(listindex.get(j).getPag()+"");
                       filterlist.get(i).setIndexs(listindex.get(j).getPag());
                   }else {
                       if (listindex.get(j).getName().equals("大事记"))
                       {
                           holders.pages.setText(listindex.get(j).getPag()+"");
                           filterlist.get(i).setIndexs(listindex.get(j).getPag());
                       }else {
                           if (listindex.get(j).getName().equals("后记")){
                               holders.pages.setText(listindex.get(j).getPag()+"");
                               filterlist.get(i).setIndexs(listindex.get(j).getPag());
                           }else {
                               holders.pages.setText(listindex.get(j).getPag()+1+"");
                               filterlist.get(i).setIndexs(listindex.get(j).getPag()+1);
                           }
                       }
                   }

                  /* if (a==0){
                       holders.pages.setText(listindex.get(j).getPag()+1+"");
                       filterlist.get(i).setIndexs(listindex.get(j).getPag()+1);
                   }else {
                       holders.pages.setText(listindex.get(j).getPag()-a+1+"");
                       filterlist.get(i).setIndexs(listindex.get(j).getPag()-a+1);
                       a=0;
                   }*/

               }

           }
        }

           /* if (filterlist.get(i).getName().equals("世系表")){
                //二级列表适配器
                holders.bookrecyce2.setLayoutManager(new LinearLayoutManager(context));
                MyCardadpter myCardadpter = new MyCardadpter(lists,context);
                holders.bookrecyce2.setAdapter(myCardadpter);
            }*/
          //  holders.pages.setText(filterlist.get(i).getFname());
   /*     if(flag == i){

            holders.ltext.setTextColor(Color.parseColor("#3087ea"));
        }else {

            holders.ltext.setTextColor(Color.parseColor("#ffffff"));

        }*/
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
        TextView name;
        TextView pages;
        TextView jishi;
        RecyclerView bookrecyce2;
        public Holders( View itemView) {
            super(itemView);
            jishi=itemView.findViewById(R.id.jishi);
            name=itemView.findViewById(R.id.text1);
            pages=itemView.findViewById(R.id.pages);
            bookrecyce2 = itemView.findViewById(R.id.bookrecyce2);
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
