package com.zupu.zp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.zupu.zp.R;
import com.zupu.zp.bean.ZpBean;
import com.zupu.zp.entity.ZegoApplication;

import java.util.ArrayList;
import java.util.List;

import static com.zupu.zp.entity.ZegoApplication.listindex;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/7/22 21:00
 * update: $date$
 */
public class MyCardadpter extends RecyclerView.Adapter<MyCardadpter.GroupHolder> {
   // List<ZpBean.FamilyListBean> list;
    ArrayList<List<ZpBean.FamilyListBean>> lists = new ArrayList<>();
    Context context;

    public MyCardadpter(ArrayList<List<ZpBean.FamilyListBean>> lists, Context context) {
        this.lists = lists;
        this.context = context;
    }
/* public MyCardadpter(List<ZpBean.FamilyListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }*/

    @NonNull
    @Override
    public GroupHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.groupview, null);
        GroupHolder groupHolder = new GroupHolder(view);
        return groupHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupHolder groupHolder,final int i) {
        for (int j = 0; j <lists.get(i).size() ; j++) {
            groupHolder.groupname.setText("第"+(ZegoApplication.index-lists.get(i).get(j).getRankingSeniority()+1)+"世");
            groupHolder.childrecyl.setLayoutManager(new LinearLayoutManager(context));
            MyChildadapter myChildadapter = new MyChildadapter(lists.get(i),context,i);
            groupHolder.childrecyl.setAdapter(myChildadapter);
        }
  /*      groupHolder.groupck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemclick.getgroupck(i);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class GroupHolder extends RecyclerView.ViewHolder {
        TextView groupname;
        RecyclerView childrecyl;
        public GroupHolder(@NonNull View itemView) {
            super(itemView);
            groupname=itemView.findViewById(R.id.shi);
            childrecyl=itemView.findViewById(R.id.childrecyl);
        }
    }
    class MyChildadapter extends RecyclerView.Adapter<MyChildadapter.ChildHolder>{
      //  List<ZpBean.FamilyListBean> childlist;
       // ArrayList<List<ZpBean.FamilyListBean>> childlist;
        List<ZpBean.FamilyListBean> childlist;
        Context context;
        int groupposition;

        public MyChildadapter(List<ZpBean.FamilyListBean> childlist, Context context, int groupposition) {
            this.childlist = childlist;
            this.context = context;
            this.groupposition = groupposition;
        }

        @NonNull
        @Override
        public ChildHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(context, R.layout.bookitm, null);
            ChildHolder childHolder = new ChildHolder(view);
            return childHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ChildHolder childHolder, final int i) {


            for (int j = 0; j <listindex.size() ; j++) {

                if (listindex.get(j).getData()!=null){
                    for (int k = 0; k <listindex.get(j).getData().size() ; k++) {
                        if ((listindex.get(j).getData().get(k).getSurname()+listindex.get(j).getData().get(k).getName()).equals(childlist.get(i).getSurname()+childlist.get(i).getName()))
                        {
                            childHolder.pages.setText(listindex.get(j).getPag()+1+"");
                        }
                    }
                }

            }
                childHolder.childname.setText(childlist.get(i).getSurname()+childlist.get(i).getName()+"");
        }

        @Override
        public int getItemCount() {
            return childlist.size();
        }

        public class ChildHolder extends RecyclerView.ViewHolder {
            TextView childname;
            TextView pages;
            public ChildHolder(@NonNull View itemView) {
                super(itemView);
                childname=itemView.findViewById(R.id.text1);
                pages=itemView.findViewById(R.id.pages);
            }
        }
    }
    public interface OnItemclick{
        void getgroupck(int groupposition);
        void getchildck(int groupposition, int childposition);
    }
    OnItemclick onItemclick;
    public void setOnItemclick(OnItemclick onItemclick){
        this.onItemclick=onItemclick;
    }

}
