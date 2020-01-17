package com.zupu.zp.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zupu.zp.R;
import com.zupu.zp.adapter.LiveItemAdapter;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.bean.LiveBean;

import java.util.ArrayList;
import java.util.List;

import static com.zupu.zp.entity.ZegoApplication.dismissProgress;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/12 11:08
 * update: $date$
 */
public class LiveChildfindFragment extends BaseFragment {
  private   View  view;
  private XRecyclerView live_recyview;
    LiveItemAdapter liveItemAdapter;
    RelativeLayout reque;
    int page = 1;
    ArrayList<LiveBean.ListBean> listBeans = new ArrayList<>();
    String livetype="3";
    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.livechildlaout, container, false);
        return view;
    }

    @Override
    public void initview() {
        live_recyview= view.findViewById(R.id.live_recyview);
        reque=view.findViewById(R.id.reque);
    }

    @Override
    public void initdata() {
        //查询接口得到直播列表
        getdata(livetype,1);

        //查询接口得到直播列表
        getdata(livetype,1);
        live_recyview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        live_recyview.addItemDecoration(new SpaceItemDecoration(10));

        //适配器设置
        liveItemAdapter = new LiveItemAdapter(listBeans,getActivity());

        live_recyview.setLoadingMoreEnabled(true);//加载更多
        live_recyview.setPullRefreshEnabled(true);//下拉刷新
        live_recyview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page = 1;
                getdata(livetype,page);
                listBeans.clear();
                liveItemAdapter.notifyDataSetChanged();
                live_recyview.refreshComplete();//停止刷新
            }

            @Override
            public void onLoadMore() {
                page++;
                getdata(livetype,page);
                live_recyview.loadMoreComplete();//设置停止加载
            }
        });
        live_recyview.setAdapter(liveItemAdapter);

    }

    @Override
    public void initlinsenter() {

    }

    @Override
    public void persenter() {

    }

    @Override
    public void sucecess(Object o) {
        if (o instanceof LiveBean) {
            dismissProgress(getActivity());
            LiveBean liveBean = (LiveBean) o;
            if (liveBean.getCode()==0)
            {
                List<LiveBean.ListBean> liveBeanList = liveBean.getList();
                listBeans.addAll(liveBeanList);
                liveItemAdapter.notifyDataSetChanged();
                //点击事件
                setdata(listBeans ,liveItemAdapter);
                if (liveBeanList.size()==0&&listBeans.size()==0){
                    reque.setVisibility(View.VISIBLE);
                }else {
                    reque.setVisibility(View.GONE);
                }
            }else {
                Toast.makeText(getActivity(), liveBean.getMsg(), Toast.LENGTH_SHORT).show();
                if (liveBean.getMsg().equals("登陆失效"))
                outLogings();
            }


        }
    }
}
