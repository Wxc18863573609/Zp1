package com.zupu.zp.myactivity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.adapter.MyViewPagerAdapter;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.utliss.GallyPageTransformer;
import com.zupu.zp.utliss.ImageUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class FmActivity extends BaseActivity {
    private ViewPager mViewPager;
    private RelativeLayout ll_main;
    private List<ImageView> imageViews;
    private int pagerWidth,page=4001;
    private Button btn;
    @Override
    public int initlayout() {
        return R.layout.activity_fm;
    }

    @Override
    public void initview() {
        btn=findViewById(R.id.btn);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        ll_main = (RelativeLayout) findViewById(R.id.activity_main);
    }

    @Override
    public void initdata() {
        initData();

        mViewPager.setOffscreenPageLimit(3);
        pagerWidth = (int) (getResources().getDisplayMetrics().widthPixels * 3.0f / 5.0f);
        ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        } else {
            lp.width = pagerWidth;
        }
        mViewPager.setLayoutParams(lp);
        mViewPager.setPageMargin(-50);
        ll_main.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return mViewPager.dispatchTouchEvent(motionEvent);
            }
        });
        mViewPager.setPageTransformer(true, new GallyPageTransformer());
        mViewPager.setAdapter(new MyViewPagerAdapter(imageViews));
    }

    @Override
    public void initlisenter() {
        //监听滑动的封面
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        page=4001;
                        break;
                    case 1:
                        page=4002;
                        break;
                    case 2:
                        page=4003;
                        break;
                    case 3:
                        page=4004;
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        //选定封面
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(page);
                finish();
            }
        });
    }

    @Override
    public void persenter() {

    }



    @Override
    public void sucecess(Object o) {

    }
    private void initData() {
        imageViews = new ArrayList<>();
        ImageView first = new ImageView(FmActivity.this);
       // first.setImageBitmap(ImageUtil.getReverseBitmapById(R.drawable.zb11, FmActivity.this));
        first.setImageResource(R.drawable.jiapuone);
        ImageView second = new ImageView(FmActivity.this);
       // second.setImageBitmap(ImageUtil.getReverseBitmapById(R.mipmap.ic_bg, FmActivity.this));
       second.setImageResource(R.drawable.jiaputwo);
        ImageView third = new ImageView(FmActivity.this);
      //  third.setImageBitmap(ImageUtil.getReverseBitmapById(R.drawable.shanping, FmActivity.this));
        third.setImageResource(R.drawable.jiaputhire);
        ImageView fourth = new ImageView(FmActivity.this);
      //  fourth.setImageBitmap(ImageUtil.getReverseBitmapById(R.mipmap.fourth,MainActivity.this));
        fourth.setImageResource(R.drawable.jiapufour);
        ImageView fifth = new ImageView(FmActivity.this);
      //  fifth.setImageBitmap(ImageUtil.getReverseBitmapById(R.mipmap.ic_launcher, FmActivity.this));
      //  fifth.setImageResource(R.mipmap.ic_launcher);
        imageViews.add(first);
        imageViews.add(second);
        imageViews.add(third);
        imageViews.add(fourth);
      //  imageViews.add(fifth);
    }
}
