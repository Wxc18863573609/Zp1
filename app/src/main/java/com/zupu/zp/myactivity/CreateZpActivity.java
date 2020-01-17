package com.zupu.zp.myactivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseActivity;
import com.zupu.zp.bean.BookBean;
import com.zupu.zp.bean.PageBean;
import com.zupu.zp.bean.Picbean;
import com.zupu.zp.bean.ZpBean;
import com.zupu.zp.bean.ZpDaoBean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.fragment.BookFaiwFragment;
import com.zupu.zp.fragment.BookFousFragment;
import com.zupu.zp.fragment.BookOneFragment;
import com.zupu.zp.fragment.BookSeavenFragment;
import com.zupu.zp.fragment.BookSixFragment;
import com.zupu.zp.fragment.BookThre2Fragment;
import com.zupu.zp.fragment.BookThre3Fragment;
import com.zupu.zp.fragment.BookThre4Fragment;
import com.zupu.zp.fragment.BookThreFragment;
import com.zupu.zp.fragment.BookTowFragment;
import com.zupu.zp.fragment.BookdsjFragment;
import com.zupu.zp.fragment.BookhjFragment;
import com.zupu.zp.fragment.BookjgjxFragment;
import com.zupu.zp.fragment.BookrwzFragment;
import com.zupu.zp.fragment.BookxslyFragment;
import com.zupu.zp.fragment.BookzpxyFragment;
import com.zupu.zp.utliss.ImageUtli;
import com.zupu.zp.utliss.MyProgressDialog;
import com.zupu.zp.utliss.PhotoView;
import com.zupu.zp.utliss.UrlCount;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.http.Url;

import static com.zupu.zp.entity.ZegoApplication.dismissProgress;
import static com.zupu.zp.entity.ZegoApplication.index;
import static com.zupu.zp.entity.ZegoApplication.listindex;
import static com.zupu.zp.entity.ZegoApplication.showProgress;

public class CreateZpActivity extends BaseActivity {
    String path;
    int page = 1;
    int height;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    dismissProgress(CreateZpActivity.this);
                    Intent intent = new Intent(CreateZpActivity.this, PdfActivity.class);
                    Gson gson = new Gson();
                    ZpDaoBean zpDaoBean = gson.fromJson(path, ZpDaoBean.class);
                    if (zpDaoBean.getPath() == null && zpDaoBean.getPath().equals("")) {
                        Toast.makeText(CreateZpActivity.this, R.string.donlaodnos, Toast.LENGTH_SHORT).show();
                    } else {
                        if (zpDaoBean.getPath() != null && !zpDaoBean.getPath().equals("")) {
                            intent.putExtra("url", zpDaoBean.getPath());
                            startActivity(intent);
                            numberpopWindow.dismiss();
                        } else {
                            Toast.makeText(CreateZpActivity.this, R.string.donlaodnos, Toast.LENGTH_SHORT).show();
                        }

                    }
                    break;
                case 2:
                    dismissProgress(CreateZpActivity.this);
                    Toast.makeText(CreateZpActivity.this, R.string.outsb, Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    };
    public static ViewPager vp;
    boolean boo;
    String json;
    View numberview;
    PopupWindow numberpopWindow;
    ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private PhotoView img;
    MyProgressDialog dialog;
    SharedPreferences sharedPreferences;
    ArrayList<Fragment> list = new ArrayList<>();
    FragmentPagerAdapter fragmentPagerAdapter;
    LinearLayout addf;
    private String str = "";
    ImageView back;
    List<BookBean> bookBeans1;
    ImageView imm, cd;
    Integer indexa = 0;
    int flag;
    //  int a= length/696;
    ArrayList<ArrayList<ZpBean.FamilyListBean>> arrayLists = new ArrayList<>();


    BookFousFragment bookFousFragment = new BookFousFragment();
    BookSixFragment bookSixFragment = new BookSixFragment();

    @Override
    public int initlayout() {
        return R.layout.activity_create_zp;
    }

    @Override
    public void initview() {
        numberview = LayoutInflater.from(this).inflate(R.layout.caidan_popwind, null);
        numberpopWindow = new PopupWindow(numberview, 360, 200);
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        vp = findViewById(R.id.vp);
        back = (ImageView) findViewById(R.id.backss);
        img = findViewById(R.id.img);
        addf = findViewById(R.id.addf);
        imm = findViewById(R.id.imm);
        cd = findViewById(R.id.cd);
    }

    @Override
    public void initdata() {

  /*      DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;     // 屏幕宽度（像素）
        height = metric.heightPixels;   // 屏幕高度（像素）
        Log.e("宽", width+"");
        Log.e("高", height+"");*/

        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        //点击vp
        vp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CreateZpActivity.this, "111", Toast.LENGTH_SHORT).show();
            }
        });

        cd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberpopWindow.setContentView(numberview);
                numberpopWindow.setFocusable(true);
                numberpopWindow.showAsDropDown(cd, 0, 0);
                RelativeLayout re1 = (RelativeLayout) numberview.findViewById(R.id.re1);
                RelativeLayout re2 = (RelativeLayout) numberview.findViewById(R.id.re2);
                //导出族谱
                re1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showProgress(CreateZpActivity.this, "生成中...");
                        getsave();
                      /* HashMap<String, Object> map = new HashMap<>();
                       map.put("uuid",sharedPreferences.getString("appLoginIdentity", null));
                       map.put("treeType",2);
                       map.put("info",json);*/
                        // persenterimpl.posthttp(UrlCount.URL_outZp,map,);
                    }
                });
                //封面
                re2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(CreateZpActivity.this, FmActivity.class));
                        numberpopWindow.dismiss();
                    }
                });
                // 把一个View转换成图片
              /*  Bitmap cachebmp = loadBitmapFromView(vp);
                cd.setImageBitmap(cachebmp);
                //startActivity(new Intent(CreateZpActivity.this,FmActivity.class));*/
            }
        });
        imm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBitmap();
            }
        });


        //关闭
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page == 1) {
                    finish();
                } else {
                    vp.setCurrentItem(0);
                    page = 1;
                }

            }
        });

        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", sharedPreferences.getString("appLoginIdentity", null));
        persenterimpl.posthttp(UrlCount.URL_ZpData, map, ZpBean.class);
        showProgress(CreateZpActivity.this,"加载中...");

    }

    @Override
    public void initlisenter() {

      /*  addf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             myBitmap();
            }
        });*/
    }

    @Override
    public void persenter() {

    }

    @Override
    public void sucecess(Object o) {
        list.clear();
        if (o instanceof ZpBean) {
            ZpBean bookBean = (ZpBean) o;
            dismissProgress(CreateZpActivity.this);
            if (bookBean.getCode() == 0) {
                imm.setVisibility(View.VISIBLE);
                BookOneFragment bookOneFragment = new BookOneFragment();
                Bundle bundle0 = new Bundle();
                bundle0.putString("xname", bookBean.getEntity().getFamilyName());
                if (bookBean.getEntity().getCreaterName() != null) {
                    bundle0.putString("createname", bookBean.getEntity().getCreaterName() + "");
                } else {
                    bundle0.putString("createname", "");
                }
                bundle0.putString("treeid", bookBean.getEntity().getId() + "");
                bundle0.putString("time", stampToDate(bookBean.getEntity().getEditTime()));
                bookOneFragment.setArguments(bundle0);//数据传递到fragment中
                list.add(bookOneFragment);
                PageBean pageBean = new PageBean();
                pageBean.setName("首页");
                pageBean.setPag(indexa++);
                listindex.add(pageBean);
                //  listindex.add(indexa++);
                //列表分页
                ArrayList<BookBean> bookBeans = new ArrayList<>();
                BookBean bookBean0 = new BookBean();
                bookBean0.setName("编委会");
                BookBean bookBean1 = new BookBean();
                bookBean1.setName("家谱序言");
                BookBean bookBean2 = new BookBean();
                bookBean2.setName("姓氏来源");
                BookBean bookBean3 = new BookBean();
                bookBean3.setName("家规家训");
                BookBean bookBean4 = new BookBean();
                bookBean4.setName("家族照");
                BookBean bookBean5 = new BookBean();
                bookBean5.setName("世系表");
                BookBean bookBean6 = new BookBean();
                bookBean6.setName("人物传");
                BookBean bookBean7 = new BookBean();
                bookBean7.setName("大事记");
                BookBean bookBean8 = new BookBean();
                bookBean8.setName("后记");
                bookBeans.add(bookBean0);
                bookBeans.add(bookBean1);
                bookBeans.add(bookBean2);
                bookBeans.add(bookBean3);
                bookBeans.add(bookBean4);
                bookBeans.add(bookBean5);
                for (int i = 0; i < bookBean.getFamilyList().size(); i++) {
                    BookBean bookBean9 = new BookBean();
                    bookBean9.setListbean(bookBean.getFamilyList().get(i));
                    bookBeans.add(bookBean9);
                }
                bookBeans.add(bookBean6);
                bookBeans.add(bookBean7);
                bookBeans.add(bookBean8);
                if (bookBeans.size() <= 12) {
                    BookTowFragment bookTowFragment = new BookTowFragment();
                    Bundle bundles = new Bundle();
                    ZegoApplication.index = bookBean.getFamilyList().get(0).getRankingSeniority();
                    bundles.putSerializable("date", (Serializable) bookBeans);
                    //   bundles.putSerializable("datas",(Serializable)bookBean.getFamilyList());
                    bookTowFragment.setArguments(bundles);//数据传递到fragment中
                    this.list.add(bookTowFragment);
                    PageBean pageBean1 = new PageBean();
                    pageBean1.setName("首页1");
                    pageBean1.setPag(indexa++);
                    listindex.add(pageBean1);
                } else {
                    int b = 0;
                    int dd = 0;
                    int length = bookBeans.size();
                    while (b <= length) {
                        if (length <= 12) {
                            BookTowFragment bookTowFragment = new BookTowFragment();
                            Bundle bundles = new Bundle();
                            ZegoApplication.index = bookBean.getFamilyList().get(0).getRankingSeniority();
                            bundles.putSerializable("date", (Serializable) bookBeans);
                            // bundles.putSerializable("datas",(Serializable)bookBean.getFamilyList());
                            bookTowFragment.setArguments(bundles);//数据传递到fragment中
                            this.list.add(bookTowFragment);
                            PageBean pageBean1 = new PageBean();
                            pageBean1.setName("首页1");
                            pageBean1.setPag(indexa++);
                            listindex.add(pageBean1);
                            break;
                        } else {
                            if (b >= 12) {
                                if (b == length) {
                                    bookBeans1 = bookBeans.subList(dd, b);
                                } else {
                                    bookBeans1 = bookBeans.subList(b - 12, b);
                                }
                                List<BookBean> bookBeans2 = new ArrayList<>();
                                bookBeans2.addAll(bookBeans1);
                                BookTowFragment bookTowFragment = new BookTowFragment();
                                Bundle bundles = new Bundle();
                                ZegoApplication.index = bookBean.getFamilyList().get(0).getRankingSeniority();
                                bundles.putSerializable("date", (Serializable) bookBeans2);
                                //bundles.putSerializable("datas",(Serializable)bookBean.getFamilyList());
                                bookTowFragment.setArguments(bundles);//数据传递到fragment中
                                this.list.add(bookTowFragment);
                                PageBean pageBean1 = new PageBean();
                                pageBean1.setName("首页1");
                                pageBean1.setPag(indexa++);
                                listindex.add(pageBean1);
                                if (b == length) {
                                    break;
                                }
                            }
                            if (b <= length - 12) {
                                b = b + 12;
                            } else {
                                dd = b;
                                b = length;
                            }
                        }
                    }
                }

                if (bookBean.getEntity().getEditorialBoard() != null && !bookBean.getEntity().getEditorialBoard().equals("")) {
                    int b = 0;
                    int dd = 0;
                    String str1 = "";
                    str = bookBean.getEntity().getEditorialBoard();
                    int length = str.length();

                        while (b <= length) {
                            if (length <= 790) {
                                str1 = str.substring(0, length);
                                BookFaiwFragment bookFaiwFragment = new BookFaiwFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("data", str1);
                                bundle.putString("content", bookBean.getEntity().getEditorialBoard()+"");
                                bundle.putString("treeid", bookBean.getEntity().getId() + "");
                                bundle.putString("type", bookBean.getEntity().getType() + "");
                                bookFaiwFragment.setArguments(bundle);//数据传递到fragment中
                                this.list.add(bookFaiwFragment);
                                //  listindex.add(indexa++);
                                PageBean pageBean2 = new PageBean();
                                pageBean2.setName("编委会");
                                pageBean2.setPag(indexa++);
                                listindex.add(pageBean2);
                                break;
                            } else {
                                if (b >= 790) {
                                    if (b == length) {
                                        str1 = str.substring(dd, b);
                                    } else {
                                        str1 = str.substring(b - 790, b);
                                    }
                                    BookFaiwFragment bookFaiwFragment = new BookFaiwFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("data", str1);
                                    bundle.putString("content", bookBean.getEntity().getEditorialBoard()+"");
                                    bundle.putString("treeid", bookBean.getEntity().getId() + "");
                                    bundle.putString("type", bookBean.getEntity().getType() + "");
                                    bookFaiwFragment.setArguments(bundle);//数据传递到fragment中
                                    list.add(bookFaiwFragment);
                                    //listindex.add(indexa++);
                                    PageBean pageBean2 = new PageBean();
                                    pageBean2.setName("编委会");
                                    pageBean2.setPag(indexa++);
                                    listindex.add(pageBean2);
                                    if (b == length) {
                                        break;
                                    }
                                }
                                if (b <= length - 790) {
                                    b = b + 790;
                                } else {
                                    dd = b;
                                    b = length;
                                }
                            }
                        }
                } else {
                    BookFaiwFragment bookFaiwFragment = new BookFaiwFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", bookBean.getEntity().getEditorialBoard()+"");

                    bundle.putString("content", bookBean.getEntity().getEditorialBoard()+"");
                    bundle.putString("treeid", bookBean.getEntity().getId() + "");
                    bundle.putString("type", bookBean.getEntity().getType() + "");
                    bookFaiwFragment.setArguments(bundle);//数据传递到fragment中
                    this.list.add(bookFaiwFragment);
                    //   listindex.add(indexa++);
                    PageBean pageBean2 = new PageBean();
                    pageBean2.setName("编委会");
                    pageBean2.setPag(indexa++);
                    listindex.add(pageBean2);
                }
                if (bookBean.getEntity().getPreface() != null && !bookBean.getEntity().getPreface().equals("")) {
                    int b = 0;
                    int dd = 0;
                    String str1 = "";
                    str = bookBean.getEntity().getPreface();
                    int length = str.length();
                    while (b <= length) {
                        if (length <= 790) {
                            str1 = str.substring(0, length);
                            BookzpxyFragment bookzpxyFragment = new BookzpxyFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("data", str1);
                            bundle.putString("content", bookBean.getEntity().getPreface()+"");
                            bundle.putString("treeid", bookBean.getEntity().getId() + "");
                            bundle.putString("type", bookBean.getEntity().getType() + "");
                            bookzpxyFragment.setArguments(bundle);//数据传递到fragment中
                            this.list.add(bookzpxyFragment);
                            //listindex.add(indexa++);
                            PageBean pageBean2 = new PageBean();
                            pageBean2.setName("家谱序言");
                            pageBean2.setPag(indexa++);
                            listindex.add(pageBean2);
                            break;
                        } else {
                            if (b >= 790) {
                                if (b == length) {
                                    str1 = str.substring(dd, b);
                                } else {
                                    str1 = str.substring(b - 790, b);
                                }
                                BookzpxyFragment bookzpxyFragment = new BookzpxyFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("data", str1);
                                bundle.putString("content", bookBean.getEntity().getPreface()+"");
                                bundle.putString("treeid", bookBean.getEntity().getId() + "");
                                bundle.putString("type", bookBean.getEntity().getType() + "");
                                bookzpxyFragment.setArguments(bundle);//数据传递到fragment中
                                this.list.add(bookzpxyFragment);
                                //  listindex.add(indexa++);
                                PageBean pageBean2 = new PageBean();
                                pageBean2.setName("家谱序言");
                                pageBean2.setPag(indexa++);
                                listindex.add(pageBean2);
                                if (b == length) {
                                    break;
                                }
                            }
                            if (b <= length - 790) {
                                b = b + 790;
                            } else {
                                dd = b;
                                b = length;
                            }
                        }
                    }
                } else {
                    BookzpxyFragment bookzpxyFragment = new BookzpxyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", bookBean.getEntity().getPreface()+"");
                    bundle.putString("content", bookBean.getEntity().getPreface()+"");
                    bundle.putString("treeid", bookBean.getEntity().getId() + "");
                    bundle.putString("type", bookBean.getEntity().getType() + "");
                    bookzpxyFragment.setArguments(bundle);//数据传递到fragment中
                    this.list.add(bookzpxyFragment);
                    // listindex.add(indexa++);
                    PageBean pageBean2 = new PageBean();
                    pageBean2.setName("家谱序言");
                    pageBean2.setPag(indexa++);
                    listindex.add(pageBean2);
                }

                if (bookBean.getEntity().getSurnameSources() != null && !bookBean.getEntity().getSurnameSources().equals("")) {
                    str = bookBean.getEntity().getSurnameSources();
                    int b = 0;
                    int dd = 0;
                    String str1="";
                    int length = str.length();
                    while (b <= length) {
                        if (length <= 790) {
                            str1 = str.substring(0, length);
                            BookxslyFragment bookxslyFragment = new BookxslyFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("data", str1);
                            bundle.putString("content", bookBean.getEntity().getSurnameSources()+"");
                            bundle.putString("treeid", bookBean.getEntity().getId() + "");
                            bundle.putString("type", bookBean.getEntity().getType() + "");
                            bookxslyFragment.setArguments(bundle);//数据传递到fragment中
                            this.list.add(bookxslyFragment);
                            // listindex.add(indexa++);
                            PageBean pageBean2 = new PageBean();
                            pageBean2.setName("姓氏来源");
                            pageBean2.setPag(indexa++);
                            listindex.add(pageBean2);
                            break;
                        } else {
                            if (b >=790) {
                                if (b == length) {
                                    str1 = str.substring(dd, b);
                                } else {
                                    str1 = str.substring(b - 790, b);
                                }
                                BookxslyFragment bookxslyFragment = new BookxslyFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("data", str1);
                                bundle.putString("content", bookBean.getEntity().getSurnameSources()+"");
                                bundle.putString("treeid", bookBean.getEntity().getId() + "");
                                bundle.putString("type", bookBean.getEntity().getType() + "");
                                bookxslyFragment.setArguments(bundle);//数据传递到fragment中
                                this.list.add(bookxslyFragment);
                                //  listindex.add(indexa++);
                                PageBean pageBean2 = new PageBean();
                                pageBean2.setName("姓氏来源");
                                pageBean2.setPag(indexa++);
                                listindex.add(pageBean2);
                                if (b == length) {
                                    break;
                                }
                            }
                            if (b <= length - 790) {
                                b = b + 790;
                            } else {
                                dd = b;
                                b = length;
                            }
                        }
                    }
                } else {
                    BookxslyFragment bookxslyFragment = new BookxslyFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", bookBean.getEntity().getSurnameSources()+"");
                    bundle.putString("content", bookBean.getEntity().getSurnameSources()+"");
                    bundle.putString("treeid", bookBean.getEntity().getId() + "");
                    bundle.putString("type", bookBean.getEntity().getType() + "");
                    bookxslyFragment.setArguments(bundle);//数据传递到fragment中
                    this.list.add(bookxslyFragment);
                    //listindex.add(indexa++);
                    PageBean pageBean2 = new PageBean();
                    pageBean2.setName("姓氏来源");
                    pageBean2.setPag(indexa++);
                    listindex.add(pageBean2);
                }

                if (bookBean.getEntity().getFamilyDiscipline() != null && !bookBean.getEntity().getFamilyDiscipline().equals("")) {
                    int b = 0;
                    int dd = 0;
                    String str1="";
                    str = bookBean.getEntity().getFamilyDiscipline();
                    int length = str.length();
                    while (b <= length) {
                        if (length <= 790) {
                            str1 = str.substring(0, length);
                            BookjgjxFragment bookjgjxFragment = new BookjgjxFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("data", str1);
                            bundle.putString("content", bookBean.getEntity().getFamilyDiscipline()+"");
                            bundle.putString("treeid", bookBean.getEntity().getId() + "");
                            bundle.putString("type", bookBean.getEntity().getType() + "");
                            bookjgjxFragment.setArguments(bundle);//数据传递到fragment中
                            this.list.add(bookjgjxFragment);
                            //listindex.add(indexa++);
                            PageBean pageBean2 = new PageBean();
                            pageBean2.setName("家规家训");
                            pageBean2.setPag(indexa++);
                            listindex.add(pageBean2);
                            break;
                        } else {
                            if (b >= 790) {
                                if (b == length) {
                                    str1 = str.substring(dd, b);
                                } else {
                                    str1 = str.substring(b - 790, b);
                                }
                                BookjgjxFragment bookjgjxFragment = new BookjgjxFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("data", str1);
                                bundle.putString("content", bookBean.getEntity().getFamilyDiscipline()+"");
                                bundle.putString("treeid", bookBean.getEntity().getId() + "");
                                bundle.putString("type", bookBean.getEntity().getType() + "");
                                bookjgjxFragment.setArguments(bundle);//数据传递到fragment中
                                this.list.add(bookjgjxFragment);
                                //listindex.add(indexa++);
                                PageBean pageBean2 = new PageBean();
                                pageBean2.setName("家规家训");
                                pageBean2.setPag(indexa++);
                                listindex.add(pageBean2);
                                if (b == length) {
                                    break;
                                }
                            }
                            if (b <= length - 790) {
                                b = b + 790;
                            } else {
                                dd = b;
                                b = length;
                            }
                        }
                    }
                } else {
                    BookjgjxFragment bookjgjxFragment = new BookjgjxFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", bookBean.getEntity().getFamilyDiscipline()+"");
                    bundle.putString("content", bookBean.getEntity().getFamilyDiscipline()+"");
                    bundle.putString("treeid", bookBean.getEntity().getId() + "");
                    bundle.putString("type", bookBean.getEntity().getType() + "");
                    bookjgjxFragment.setArguments(bundle);//数据传递到fragment中
                    this.list.add(bookjgjxFragment);
                    //  listindex.add(indexa++);
                    PageBean pageBean2 = new PageBean();
                    pageBean2.setName("家规家训");
                    pageBean2.setPag(indexa++);
                    listindex.add(pageBean2);
                }
                this.list.add(bookFousFragment);
                //listindex.add(indexa++);
                PageBean pageBean2 = new PageBean();
                pageBean2.setName("家族照");
                pageBean2.setPag(indexa++);
                listindex.add(pageBean2);
                BookThreFragment bookThreFragment = new BookThreFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("subjectId", bookBean.getEntity().getId() + "");
                bundle1.putSerializable("list", (Serializable) bookBean.getPiclist());
                bookThreFragment.setArguments(bundle1);//数据传递到fragment中
                this.list.add(bookThreFragment);
                //  listindex.add(indexa++);
                PageBean pageBean3 = new PageBean();
                pageBean3.setName("家族照1");
                pageBean3.setPag(indexa++);
                listindex.add(pageBean3);
                BookThre2Fragment bookThre2Fragment = new BookThre2Fragment();
                bookThre2Fragment.setArguments(bundle1);//数据传递到fragment中
                this.list.add(bookThre2Fragment);
                //listindex.add(indexa++);
                PageBean pageBean4 = new PageBean();
                pageBean4.setName("家族照2");
                pageBean4.setPag(indexa++);
                listindex.add(pageBean4);
                BookThre3Fragment bookThre3Fragment = new BookThre3Fragment();
                bookThre3Fragment.setArguments(bundle1);//数据传递到fragment中
                this.list.add(bookThre3Fragment);
                //listindex.add(indexa++);
                PageBean pageBean5 = new PageBean();
                pageBean5.setName("家族照3");
                pageBean5.setPag(indexa++);
                listindex.add(pageBean5);
                BookThre4Fragment bookThre4Fragment = new BookThre4Fragment();
                bookThre4Fragment.setArguments(bundle1);//数据传递到fragment中
                this.list.add(bookThre4Fragment);
                //listindex.add(indexa++);
                PageBean pageBean6 = new PageBean();
                pageBean6.setName("家族照4");
                pageBean6.setPag(indexa++);
                listindex.add(pageBean6);
                this.list.add(bookSixFragment);
                PageBean pageBean7 = new PageBean();
                pageBean7.setName("世系表");
                pageBean7.setPag(indexa++);
                listindex.add(pageBean7);
                //  listindex.add(indexa++);
                if (bookBean.getFamilyList() != null) {
                    for (int i = 0; i < bookBean.getFamilyList().size(); i = i + 2) {
                        if (i > bookBean.getFamilyList().size()) {
                            break;
                        }
                        ArrayList<ZpBean.FamilyListBean> familyListBeans = new ArrayList<>();
                        familyListBeans.add(bookBean.getFamilyList().get(i));
                        if (i + 1 < bookBean.getFamilyList().size())
                            familyListBeans.add(bookBean.getFamilyList().get(i + 1));
                        arrayLists.add(familyListBeans);
                    }

                    for (int i = 0; i < arrayLists.size(); i++) {
                        if (i == 0) {
                            ZegoApplication.index = arrayLists.get(0).get(0).getRankingSeniority();
                        }
                        BookSeavenFragment bookSeavenFragment = new BookSeavenFragment();
                        Bundle bundle7 = new Bundle();
                        bundle7.putSerializable("datas", (Serializable) arrayLists.get(i));
                        bookSeavenFragment.setArguments(bundle7);//数据传递到fragment中
                        this.list.add(bookSeavenFragment);
                        //listindex.add(indexa++);
                        PageBean pageBean8 = new PageBean();
                        pageBean8.setName("表" + i);
                        pageBean8.setPag(indexa++);
                        pageBean8.setData(arrayLists.get(i));
                        listindex.add(pageBean8);
                    }


                } else {
                    BookSeavenFragment bookSeavenFragment = new BookSeavenFragment();
                    this.list.add(bookSeavenFragment);
                    //listindex.add(indexa++);
                    PageBean pageBean9 = new PageBean();
                    pageBean9.setName("表1");
                    pageBean9.setPag(indexa++);
                    listindex.add(pageBean9);
                }


                if (bookBean.getEntity().getBiography() != null && !bookBean.getEntity().getBiography().equals("")) {
                    int b = 0;
                    int dd = 0;
                    String str1="";
                    str = bookBean.getEntity().getBiography();
                    int length = str.length();
                    while (b <= length) {
                        if (length <= 790) {
                            str1 = str.substring(0, length);
                            BookrwzFragment bookrwzFragment = new BookrwzFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("data", str1);
                            bundle.putString("content", bookBean.getEntity().getBiography()+"");
                            bundle.putString("treeid", bookBean.getEntity().getId() + "");
                            bundle.putString("type", bookBean.getEntity().getType() + "");
                            bookrwzFragment.setArguments(bundle);//数据传递到fragment中
                            this.list.add(bookrwzFragment);
                            // listindex.add(indexa++);
                            PageBean pageBean9 = new PageBean();
                            pageBean9.setName("人物传");
                            pageBean9.setPag(indexa++);
                            listindex.add(pageBean9);
                            break;
                        } else {
                            if (b >= 790) {
                                if (b == length) {
                                    str1 = str.substring(dd, b);
                                } else {
                                    str1 = str.substring(b - 790, b);
                                }
                                BookrwzFragment bookrwzFragment = new BookrwzFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("data", str1);
                                bundle.putString("content", bookBean.getEntity().getBiography()+"");
                                bundle.putString("treeid", bookBean.getEntity().getId() + "");
                                bundle.putString("type", bookBean.getEntity().getType() + "");
                                bookrwzFragment.setArguments(bundle);//数据传递到fragment中
                                this.list.add(bookrwzFragment);
                                //listindex.add(indexa++);
                                PageBean pageBean9 = new PageBean();
                                pageBean9.setName("人物传");
                                pageBean9.setPag(indexa++);
                                listindex.add(pageBean9);
                                if (b == length) {
                                    break;
                                }
                            }
                            if (b <= length - 790) {
                                b = b + 790;
                            } else {
                                dd = b;
                                b = length;
                            }
                        }
                    }
                } else {
                    BookrwzFragment bookrwzFragment = new BookrwzFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", bookBean.getEntity().getBiography()+"");
                    bundle.putString("content", bookBean.getEntity().getBiography()+"");
                    bundle.putString("treeid", bookBean.getEntity().getId() + "");
                    bundle.putString("type", bookBean.getEntity().getType() + "");
                    bookrwzFragment.setArguments(bundle);//数据传递到fragment中
                    this.list.add(bookrwzFragment);
                    //listindex.add(indexa++);
                    PageBean pageBean9 = new PageBean();
                    pageBean9.setName("人物传");
                    pageBean9.setPag(indexa++);
                    listindex.add(pageBean9);
                }
                if (bookBean.getEntity().getGreatChronicle() != null && !bookBean.getEntity().getGreatChronicle().equals("")) {
                    str = bookBean.getEntity().getGreatChronicle();
                    int b = 0;
                    int dd = 0;
                    String str1="";
                    int length = str.length();
                    while (b <= length) {
                        if (length <= 790) {
                            str1 = str.substring(0, length);
                            BookdsjFragment bookdsjFragment = new BookdsjFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("data", str1);
                            bundle.putString("content", bookBean.getEntity().getGreatChronicle()+"");
                            bundle.putString("treeid", bookBean.getEntity().getId() + "");
                            bundle.putString("type", bookBean.getEntity().getType() + "");
                            bookdsjFragment.setArguments(bundle);//数据传递到fragment中
                            this.list.add(bookdsjFragment);
                            //listindex.add(indexa++);
                            PageBean pageBean91 = new PageBean();
                            pageBean91.setName("大事记");
                            pageBean91.setPag(++indexa);
                            listindex.add(pageBean91);
                            break;
                        } else {
                            if (b >= 790) {
                                if (b == length) {
                                    str1 = str.substring(dd, b);
                                } else {
                                    str1 = str.substring(b - 790, b);
                                }
                                BookdsjFragment bookdsjFragment = new BookdsjFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("data", str1);
                                bundle.putString("content", bookBean.getEntity().getGreatChronicle()+"");
                                bundle.putString("treeid", bookBean.getEntity().getId() + "");
                                bundle.putString("type", bookBean.getEntity().getType() + "");
                                bookdsjFragment.setArguments(bundle);//数据传递到fragment中
                                this.list.add(bookdsjFragment);
                                //listindex.add(indexa++);
                                PageBean pageBean91 = new PageBean();
                                pageBean91.setName("大事记");
                                pageBean91.setPag(++indexa);
                                listindex.add(pageBean91);
                                if (b == length) {
                                    break;
                                }
                            }
                            if (b <= length - 790) {
                                b = b + 790;
                            } else {
                                dd = b;
                                b = length;
                            }
                        }
                    }
                } else {
                    BookdsjFragment bookdsjFragment = new BookdsjFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", bookBean.getEntity().getGreatChronicle()+"");
                    bundle.putString("content", bookBean.getEntity().getGreatChronicle()+"");
                    bundle.putString("treeid", bookBean.getEntity().getId() + "");
                    bundle.putString("type", bookBean.getEntity().getType() + "");
                    bookdsjFragment.setArguments(bundle);//数据传递到fragment中
                    this.list.add(bookdsjFragment);
                    //listindex.add(indexa++);
                    PageBean pageBean91 = new PageBean();
                    pageBean91.setName("大事记");
                    pageBean91.setPag(++indexa);
                    listindex.add(pageBean91);
                }
                if (bookBean.getEntity().getEpilogue() != null && !bookBean.getEntity().getEpilogue().equals("")) {
                    int b = 0;
                    int dd = 0;
                    String str1="";
                    str = bookBean.getEntity().getEpilogue();
                    int length = str.length();
                    while (b <= length) {
                        if (length <= 790) {
                            str1 = str.substring(0, length);
                            BookhjFragment bookhjFragment = new BookhjFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString("data", str1);
                            bundle.putString("content", bookBean.getEntity().getEpilogue()+"");
                            bundle.putString("treeid", bookBean.getEntity().getId() + "");
                            bundle.putString("type", bookBean.getEntity().getType() + "");
                            bookhjFragment.setArguments(bundle);//数据传递到fragment中
                            this.list.add(bookhjFragment);
                            //listindex.add(indexa++);
                            PageBean pageBean9 = new PageBean();
                            pageBean9.setName("后记");
                            pageBean9.setPag(++indexa);
                            listindex.add(pageBean9);
                            break;
                        } else {
                            if (b >= 790) {
                                if (b == length) {
                                    str1 = str.substring(dd, b);
                                } else {
                                    str1 = str.substring(b - 790, b);
                                }
                                BookhjFragment bookhjFragment = new BookhjFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("data", str1);
                                bundle.putString("content", bookBean.getEntity().getEpilogue()+"");
                                bundle.putString("treeid", bookBean.getEntity().getId() + "");
                                bundle.putString("type", bookBean.getEntity().getType() + "");
                                bookhjFragment.setArguments(bundle);//数据传递到fragment中
                                this.list.add(bookhjFragment);
                                //listindex.add(indexa++);
                                PageBean pageBean9 = new PageBean();
                                pageBean9.setName("后记");
                                pageBean9.setPag(++indexa);
                                listindex.add(pageBean9);
                                if (b == length) {
                                    break;
                                }
                            }
                            if (b <= length - 790) {
                                b = b + 790;
                            } else {
                                dd = b;
                                b = length;
                            }
                        }
                    }
                } else {
                    BookhjFragment bookhjFragment = new BookhjFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", bookBean.getEntity().getEpilogue()+"");
                    bundle.putString("content", bookBean.getEntity().getEpilogue()+"");
                    bundle.putString("treeid", bookBean.getEntity().getId() + "");
                    bundle.putString("type", bookBean.getEntity().getType() + "");
                    bookhjFragment.setArguments(bundle);//数据传递到fragment中
                    this.list.add(bookhjFragment);
                    //listindex.add(indexa++);
                    PageBean pageBean9 = new PageBean();
                    pageBean9.setName("后记");
                    pageBean9.setPag(++indexa);
                    listindex.add(pageBean9);
                }
                //设置适配器
                fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public int getCount() {
                        return CreateZpActivity.this.list.size();
                    }

                    @Override
                    public Fragment getItem(int i) {
              /*          View view = list.get(i).getView();
                        Bitmap cachebmp = loadBitmapFromView(view);
                        bitmaps.add(cachebmp);*/
                        return CreateZpActivity.this.list.get(i);
                    }
                };
                vp.setOffscreenPageLimit(4);
                vp.setAdapter(fragmentPagerAdapter);
                fragmentPagerAdapter.notifyDataSetChanged();
                // Log.e("视图",bitmaps.size()+""  );
                //滑动监听
                vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int i, float v, int i1) {

                    }

                    @Override
                    public void onPageSelected(int i) {
                        switch (i) {
                            case 0:
                                page = 1;
                                break;

                            default:
                                page = 0;
                                break;
                        }

                    }

                    @Override
                    public void onPageScrollStateChanged(int i) {

                    }
                });

            } else {
                Toast.makeText(this, bookBean.getMsg(), Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    /**
     * 将时间戳转换为时间
     */
    public String stampToDate(long timeMillis) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date(timeMillis);
        return simpleDateFormat.format(date);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reEventBus(Object o) {
        if (o instanceof String) {
            json = (String) o;
            Log.e("json", json);
        }
        if (o instanceof Integer) {
            Integer a = (Integer) o;
            if (a <= list.size()) {
                vp.setCurrentItem(a - 1);
            }
            if (a == 460) {
                startActivity(new Intent(CreateZpActivity.this, CreateZpActivity.class));
                finish();
            }
            if (a == 4001) {
                vp.setCurrentItem(0);
                EventBus.getDefault().post(3001);
            } else if (a == 4002) {
                vp.setCurrentItem(0);
                EventBus.getDefault().post(3002);
            } else if (a == 4003) {
                vp.setCurrentItem(0);
                EventBus.getDefault().post(3003);
            } else if (a == 4004) {
                vp.setCurrentItem(0);
                EventBus.getDefault().post(3004);
            }
        }

    }


    /***
     * 启动
     */
    public void showProgress(Context context, String message) {
        if (dialog == null) {
            dialog = new MyProgressDialog(context);
        }
        dialog.showMessage(message);
    }

    /***
     * 关闭
     */
    public void dismissProgress(Context context) {
        if (dialog == null) {
            dialog = new MyProgressDialog(context);
        }
        dialog.dismiss();
    }


    public void myBitmap() {
        Log.e("正常", "myBitmap: ");
        Bitmap bitmap = loadBitmapFromViewBySystem(vp);
        img.setImageBitmap(cropBitmap(bitmap));
        img.setVisibility(View.VISIBLE);
        img.enable();
        img.setBackgroundColor(Color.BLACK);
        boo = false;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boo = true;
                img.setVisibility(View.GONE);

            }
        });
    }


    public Bitmap loadBitmapFromViewBySystem(View v) {
        if (v == null) {
            return null;
        }
        v.destroyDrawingCache();
        v.setDrawingCacheEnabled(true);
        v.buildDrawingCache();
        Bitmap bitmap = v.getDrawingCache();
        return bitmap;
    }

    /**
     * 裁剪
     *
     * @param bitmap 原图
     * @return 裁剪后的图像
     */
    private Bitmap cropBitmap(Bitmap bitmap) {
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        //w/2 - sW/2   ,   h/2 - sH/2
        // int cropWidth = w >= h ? h : w;// 裁切后所取的正方形区域边长
      /*  int cropWidth = w / 2 - 500 / 2;
        // cropWidth /= 2;
        int cropHeight = h / 2 - 400 / 2;*/
        // int cropHeight = (int) (cropWidth / 1.2);
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, null, false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        vp = null;
    }

    public void oncLics(View view) {
        Log.e("正常", "onclics: ");
        Toast.makeText(this, "111", Toast.LENGTH_SHORT).show();
    }

    private Bitmap loadBitmapFromView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();

        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);

        c.drawColor(Color.WHITE);
        /** 如果不设置canvas画布为白色，则生成透明 */

        v.layout(0, 0, w, h);
        v.draw(c);
        return bmp;
    }


    private void getsave() {
        String Url = null;
        if (flag == 1) {
            Url = UrlCount.Base_Head + UrlCount.URL_outZp;
        } else if (flag == 2) {
            Url = UrlCount.Base_Head + UrlCount.URL_outZp2;
        }
        String finalUrl = Url;
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Log.e("请求", finalUrl);
                    URL url = new URL(finalUrl);
                    //建立连接
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //设置方法
                    urlConnection.setRequestMethod("POST");
                    //可读
                    urlConnection.setDoInput(true);
                    //可写
                    urlConnection.setDoOutput(true);
                    //参数拼接

                    String parmes = "uuid=" + URLEncoder.encode(sharedPreferences.getString("appLoginIdentity", null), "UTF-8") + "&treeType=" + URLEncoder.encode("2", "UTF-8")
                            + "&info=" + URLEncoder.encode(json, "UTF-8");
                    OutputStream outputStream = urlConnection.getOutputStream();
                    //参数写入
                    outputStream.write(parmes.getBytes());
                    //刷新
                    outputStream.flush();
                    //关闭
                    outputStream.close();
                    //得到请求码
                    int responseCode = urlConnection.getResponseCode();
                    Log.e("请求", responseCode + "");
                    if (responseCode == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        int len = -1;
                        byte[] bytes = new byte[1024];
                        StringBuffer stringBuffer = new StringBuffer();
                        while ((len = inputStream.read(bytes)) != -1) {
                            String s = new String(bytes, 0, len);
                            stringBuffer.append(s);
                        }
                        path = stringBuffer.toString();
                        Log.e("数据", path);
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    } else {
                        Log.e("TAG", "请求失败");
                        Message message = new Message();
                        message.what = 2;
                        handler.sendMessage(message);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }

}
