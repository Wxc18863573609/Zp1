package com.zupu.zp.utliss;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.zupu.zp.MainActivity;
import com.zupu.zp.R;
import com.zupu.zp.bean.DdBean;
import com.zupu.zp.bean.PayResult;
import com.zupu.zp.bean.ShareBean;
import com.zupu.zp.bean.WxPayBean;
import com.zupu.zp.bean.WxZfBean;
import com.zupu.zp.bean.Wxbean;
import com.zupu.zp.bean.Wxmorebean;
import com.zupu.zp.bean.YBean;
import com.zupu.zp.bean.ZbddBean;
import com.zupu.zp.bean.ZfbBean;
import com.zupu.zp.bean.ZfbPayBean;
import com.zupu.zp.bean.ZtBean;
import com.zupu.zp.entity.ZegoApplication;
import com.zupu.zp.myactivity.CreatLiveActivity;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/30 15:48
 * update: 支付工具
 */
public class ZfUtil {
    public  int integral;
    String userid;
    Httputlis1 instance = Httputlis1.getInstance();
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    Log.e("zfb", payResult.getMemo() + "______" + payResult.getResult() + "_____" + payResult.getResultStatus());
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(ZegoApplication.getContexta(), "支付成功", Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(10);
//                        Intent BuySucceed = new Intent(context ,Order_BuySucceed_Activity.class);
//                        context.startActivity(BuySucceed);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(ZegoApplication.getContexta(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG:
                    break;
                default:
                    break;
            }
        }
    };
    public ZfUtil(){}

    private static class ZfUtilHouder {
        private static final ZfUtil zfUtil = new ZfUtil();
    }
    public static ZfUtil getInstance() {
        return ZfUtilHouder.zfUtil;
    }

    public void wxzfMethod(String json) {
        Log.e("TAG", "wxzfMethod: " + json);
        Gson gson = new Gson();
        WxZfBean pay = gson.fromJson(json, WxZfBean.class);
        Log.e("err", json + ".............");
        PayReq req = new PayReq();
        req.appId = pay.getAppid();
        req.partnerId = pay.getPartnerid();
        req.prepayId = pay.getPrepayid();
        req.sign = pay.getSign();
        req.timeStamp = String.valueOf(pay.getTimestamp());
        req.packageValue = pay.getPackageX();
        req.nonceStr = pay.getNoncestr();
        WXUtils.reg(ZegoApplication.getContexta()).sendReq(req);
        Log.e("TAG", pay.getAppid() + "???");
        //去调微信

    }

    public void zfbmethod(String json, final Activity activity) {
/*        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("money", 1);
        map1.put("payType", 2);
        instance.posthttps(UrlCount.URL_WX, map1, new HttpUtlis.MyCllBacks() {
            @Override
            public void sucesess(String json) {
                Gson gson = new Gson();
                ZFBbean zfBbean = gson.fromJson(json, ZFBbean.class);
                final String orderInfo = zfBbean.getResult();// "服务器返回的OrderInfo";   // 订单信息
                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask((Activity) ZegoApplication.getContexta());
                        Map<String, String> result = alipay.payV2(orderInfo,true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };
                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();
            }

        });*/
     /*   Gson gson = new Gson();
        ZfbBean zfBbean = gson.fromJson(json, ZfbBean.class);*/
        // final String orderInfo = zfBbean.getData();// "服务器返回的OrderInfo";   // 订单信息
        final String orderInfo = json;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();

    }

    public void shareMethod(final String uuid, final String subjectId, final String type, final String title, final String applyId, final Context context) {
        Httputlis1 instance = Httputlis1.getInstance();
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("subjectId", subjectId);
        map.put("type", type);
        map.put("title", title);
        if (applyId != null) {
            map.put("applyId", applyId);
        }
        instance.posthttps(UrlCount.URL_Share, map, new Httputlis1.Mycallbacks() {
            @Override
            public void sucess(String json) {
                Gson gson = new Gson();
                ShareBean shareBean = gson.fromJson(json, ShareBean.class);
                if (shareBean.getCode() == 0) {
                    final String longUrl = shareBean.getLongUrl();
                    final String title1 = shareBean.getTitle();
                    final String content = shareBean.getContent();
                    final String img = shareBean.getImg();
                    View view = LayoutInflater.from(context).inflate(R.layout.popshare, null);
                    final PopupWindow window1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                    window1.setContentView(view);
                    window1.setOutsideTouchable(true);
                    LinearLayout wx = view.findViewById(R.id.wxx);
                    RelativeLayout pyq = view.findViewById(R.id.wxxx);
                    Button qxx = view.findViewById(R.id.qxx);
                    wx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //调用url分享方法
                            ShareUtils instance = ShareUtils.getInstance();
                            instance.shareUrlToWx(longUrl, title1,
                                    content, img, 0, null);
                            window1.dismiss();
                        }
                    });
                    pyq.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //调用url分享方法
                            ShareUtils instance = ShareUtils.getInstance();
                            instance.shareUrlToWx(longUrl, title1,
                                    content, img, 1, null);
                            window1.dismiss();
                        }
                    });
                    qxx.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            window1.dismiss();
                        }
                    });
                    View inflate = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
                    window1.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

                } else {
                    Toast.makeText(context, "网络请求失败", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    public void Textviews(Context context, String json) {
        View view = LayoutInflater.from(context).inflate(R.layout.textviewfz, null);
        final PopupWindow window1 = new PopupWindow(view, 650, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window1.setContentView(view);
        window1.setOutsideTouchable(true);
        TextView wx = view.findViewById(R.id.fuzhi);
        TextView wx1 = view.findViewById(R.id.phones);
        wx1.setText(json);
        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setText(wx1.getText());
                Toast.makeText(context, "复制成功", Toast.LENGTH_SHORT).show();
                window1.dismiss();
            }
        });
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
        window1.showAtLocation(inflate, Gravity.BOTTOM, 0, 900);
    }


    public void zfMethod(Activity context, String roomid) {
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        editor = sharedPreferences.edit();
        String appLoginIdentity = sharedPreferences.getString("appLoginIdentity", null);
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", appLoginIdentity);
        map.put("id", roomid);
        Httputlis1 instance = Httputlis1.getInstance();
        View view = LayoutInflater.from(context).inflate(R.layout.zflayout, null);
        final PopupWindow window1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window1.setContentView(view);
        window1.setOutsideTouchable(true);
        RelativeLayout wx = view.findViewById(R.id.wxx);
        RelativeLayout zfb = view.findViewById(R.id.wxxx);
        RelativeLayout yex = view.findViewById(R.id.yex);
        Button qxx = view.findViewById(R.id.qxx);
        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.posthttps(UrlCount.URL_zf1, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Gson gson = new Gson();
                        DdBean ddBean = gson.fromJson(json, DdBean.class);
                        if (ddBean.getCode() == 0) {
                            int orderInfoId = ddBean.getOrderId();
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("uuid", appLoginIdentity);
                            map1.put("orderIds", orderInfoId);
                            map1.put("platform", "1");
                            map1.put("tradeType", "1");
                            instance.posthttps(UrlCount.URL_zf2, map1, new Httputlis1.Mycallbacks() {
                                @Override
                                public void sucess(String json) {
                                    Gson gson1 = new Gson();
                                    WxPayBean wxPayBean = gson1.fromJson(json, WxPayBean.class);
                                    if (wxPayBean.getCode() == 0) {
                                        Gson gson2 = new Gson();
                                        Wxmorebean wxmorebean = gson2.fromJson(json, Wxmorebean.class);
                                        JSONObject root = new JSONObject();
                                        try {
                                            /**
                                             * appid : wx3c8adc40cdcd29e6
                                             * noncestr : suanmjvgb9bntpqh
                                             * package : Sign=WXPay
                                             * partnerid : 1530323921
                                             * prepayid : wx13141229251146e555e70b9e1889011800
                                             * sign : E37C81AE15250F17530C62B7016B8BC3
                                             * timestamp : 1576217550
                                             */
                                            root.put("appid", wxmorebean.getData().getPaystr().getAppid());
                                            root.put("noncestr", wxmorebean.getData().getPaystr().getNoncestr());
                                            root.put("package", wxmorebean.getData().getPaystr().getPackageX());
                                            root.put("partnerid", wxmorebean.getData().getPaystr().getPartnerid());
                                            root.put("prepayid", wxmorebean.getData().getPaystr().getPrepayid());
                                            root.put("sign", wxmorebean.getData().getPaystr().getSign());
                                            root.put("timestamp", wxmorebean.getData().getPaystr().getTimestamp());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        wxzfMethod(root.toString());
                                        window1.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.posthttps(UrlCount.URL_zf1, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Gson gson = new Gson();
                        DdBean ddBean = gson.fromJson(json, DdBean.class);
                        if (ddBean.getCode() == 0) {
                            int orderInfoId = ddBean.getOrderId();
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("uuid", appLoginIdentity);
                            map1.put("orderIds", orderInfoId);
                            map1.put("platform", "2");
                            map1.put("tradeType", "1");
                            instance.posthttps(UrlCount.URL_zf2, map1, new Httputlis1.Mycallbacks() {
                                @Override
                                public void sucess(String json) {
                                    Gson gson1 = new Gson();
                                    ZfbPayBean zfbPayBean = gson1.fromJson(json, ZfbPayBean.class);
                                    if (zfbPayBean.getCode() == 0) {
                                        zfbmethod(zfbPayBean.getData(), context);
                                        window1.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        //余额支付
        yex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.posthttps(UrlCount.URL_zf1, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Gson gson = new Gson();
                        DdBean ddBean = gson.fromJson(json, DdBean.class);
                        if (ddBean.getCode() == 0) {
                            int orderInfoId = ddBean.getOrderId();
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("uuid", appLoginIdentity);
                            map1.put("orderIds", orderInfoId);
                            map1.put("platform", "3");
                            map1.put("tradeType", "1");
                            instance.posthttps(UrlCount.URL_zf2, map1, new Httputlis1.Mycallbacks() {
                                @Override
                                public void sucess(String json) {
                                    Log.e("余额",json );
                                    Gson gson1 = new Gson();
                                    YBean yBean = gson1.fromJson(json, YBean.class);
                                    if ( yBean.getCode()==0){
                                        EventBus.getDefault().post(10);
                                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                                        HashMap<String, Object> map1 = new HashMap<>();
                                        map1.put("orderid", orderInfoId);
                                        instance.posthttps(UrlCount.URL_Ddzt, map1, new Httputlis1.Mycallbacks() {
                                            @Override
                                            public void sucess(String json) {
                                                Gson gson1 = new Gson();
                                                Log.e("充值余额", json);
                                                window1.dismiss();
                                            }
                                        });


                                    }else if ( yBean.getCode()==-2){
                                        Toast.makeText(context, "余额不足", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    }
                });

            }
        });

        qxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window1.dismiss();
            }
        });
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
        window1.showAtLocation(inflate, Gravity.BOTTOM, 0, 650);
    }


    public void zbZfMethod(Activity context, Double money) {
        SharedPreferences loging = ZegoApplication.Loging();
        sharedPreferences = loging;
        editor = sharedPreferences.edit();
        userid=sharedPreferences.getString("userId",null);
        String appLoginIdentity = sharedPreferences.getString("appLoginIdentity", null);
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", appLoginIdentity);
        map.put("type", "9");
        map.put("productPrice", money);
        map.put("mallNo", 1);
        map.put("remark", "余额");
        map.put("isShowBuyer", "1");
        map.put("mallName", "宗亲平台");
        map.put("mallPic", "https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/images/seat_occupying/applogo.png");
        map.put("productName", "【宗亲】余额充值");
        map.put("specVal", "【宗亲】余额充值");
        map.put("productPic", "https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/images/seat_occupying/applogo.png");
        map.put("amount", 1);
        map.put("price", money);
        map.put("totalPrice", money);
        map.put("productId", "");
        map.put("productNo", "");
        Httputlis1 instance = Httputlis1.getInstance();
        View view = LayoutInflater.from(context).inflate(R.layout.zflayout, null);
        final PopupWindow window1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window1.setContentView(view);
        window1.setOutsideTouchable(true);
        RelativeLayout wx = view.findViewById(R.id.wxx);
        RelativeLayout zfb = view.findViewById(R.id.wxxx);
        RelativeLayout yex = view.findViewById(R.id.yex);
        Button qxx = view.findViewById(R.id.qxx);
        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.posthttps(UrlCount.URL_Zbzf, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Gson gson = new Gson();
                        ZbddBean ddBean = gson.fromJson(json, ZbddBean.class);
                        if (ddBean.getCode() == 0) {
                            Log.e("微信", json);
                            int orderInfoId = ddBean.getOrderInfoId();
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("uuid", appLoginIdentity);
                            map1.put("orderIds", orderInfoId);
                            map1.put("platform", "1");
                            map1.put("tradeType", "1");
                            instance.posthttps(UrlCount.URL_zf2, map1, new Httputlis1.Mycallbacks() {
                                @Override
                                public void sucess(String json) {
                                    Log.e("微信2", json);
                                    Gson gson1 = new Gson();
                                    WxPayBean wxPayBean = gson1.fromJson(json, WxPayBean.class);
                                    if (wxPayBean.getCode() == 0) {
                                        Gson gson2 = new Gson();
                                        Wxmorebean wxmorebean = gson2.fromJson(json, Wxmorebean.class);
                                        JSONObject root = new JSONObject();
                                        try {
                                            /**
                                             * appid : wx3c8adc40cdcd29e6
                                             * noncestr : suanmjvgb9bntpqh
                                             * package : Sign=WXPay
                                             * partnerid : 1530323921
                                             * prepayid : wx13141229251146e555e70b9e1889011800
                                             * sign : E37C81AE15250F17530C62B7016B8BC3
                                             * timestamp : 1576217550
                                             */
                                            root.put("appid", wxmorebean.getData().getPaystr().getAppid());
                                            root.put("noncestr", wxmorebean.getData().getPaystr().getNoncestr());
                                            root.put("package", wxmorebean.getData().getPaystr().getPackageX());
                                            root.put("partnerid", wxmorebean.getData().getPaystr().getPartnerid());
                                            root.put("prepayid", wxmorebean.getData().getPaystr().getPrepayid());
                                            root.put("sign", wxmorebean.getData().getPaystr().getSign());
                                            root.put("timestamp", wxmorebean.getData().getPaystr().getTimestamp());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        wxzfMethod(root.toString());
                                        window1.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        zfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.posthttps(UrlCount.URL_Zbzf, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Gson gson = new Gson();
                        ZbddBean ddBean = gson.fromJson(json, ZbddBean.class);
                        if (ddBean.getCode() == 0) {
                            int orderInfoId = ddBean.getOrderInfoId();
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("uuid", appLoginIdentity);
                            map1.put("orderIds", orderInfoId);
                            map1.put("platform", "2");
                            map1.put("tradeType", "1");
                            instance.posthttps(UrlCount.URL_zf2, map1, new Httputlis1.Mycallbacks() {
                                @Override
                                public void sucess(String json) {
                                    Gson gson1 = new Gson();
                                    ZfbPayBean zfbPayBean = gson1.fromJson(json, ZfbPayBean.class);
                                    if (zfbPayBean.getCode() == 0) {
                                        zfbmethod(zfbPayBean.getData(), context);
                                        window1.dismiss();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
        //余额支付
        yex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.posthttps(UrlCount.URL_Zbzf, map, new Httputlis1.Mycallbacks() {
                    @Override
                    public void sucess(String json) {
                        Gson gson = new Gson();
                        ZbddBean ddBean = gson.fromJson(json, ZbddBean.class);
                        if (ddBean.getCode() == 0) {
                            int orderInfoId = ddBean.getOrderInfoId();
                            HashMap<String, Object> map1 = new HashMap<>();
                            map1.put("uuid", appLoginIdentity);
                            map1.put("orderIds", orderInfoId);
                            map1.put("platform", "3");
                            map1.put("tradeType", "1");
                            instance.posthttps(UrlCount.URL_zf2, map1, new Httputlis1.Mycallbacks() {
                                @Override
                                public void sucess(String json) {
                                    Log.e("余额",json );
                                    Gson gson1 = new Gson();
                                    YBean yBean = gson1.fromJson(json, YBean.class);
                                    if ( yBean.getCode()==0){
                                        Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                                        EventBus.getDefault().post(10);
                                        HashMap<String, Object> map = new HashMap<>();
                                        map.put("userId",Integer.parseInt(userid));
                                        instance.posthttps(UrlCount.URL_Zt, map, new Httputlis1.Mycallbacks() {
                                            @Override
                                            public void sucess(String json) {
                                                Gson gson2 = new Gson();
                                                ZtBean ztBean = gson2.fromJson(json, ZtBean.class);
                                                ZtBean.UserBean user = ztBean.getUser();
                                                integral = user.getIntegral();

                                            }
                                        });

                                        HashMap<String, Object> map1 = new HashMap<>();
                                        map1.put("orderid", orderInfoId);
                                        instance.posthttps(UrlCount.URL_Ddzt, map1, new Httputlis1.Mycallbacks() {
                                            @Override
                                            public void sucess(String json) {
                                                Gson gson1 = new Gson();
                                                Log.e("充值余额", json);
                                                window1.dismiss();
                                            }
                                        });


                                    }else if ( yBean.getCode()==-2){
                                        Toast.makeText(context, "余额不足", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                        }
                    }
                });

            }
        });

        qxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window1.dismiss();
            }
        });
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
        window1.showAtLocation(inflate, Gravity.BOTTOM, 0, 650);
    }
    public void  myshare(String longUrl,String title1,String content,String img,Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.popshare, null);
        final PopupWindow window1 = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        window1.setContentView(view);
        window1.setOutsideTouchable(true);
        LinearLayout wx = view.findViewById(R.id.wxx);
        RelativeLayout pyq = view.findViewById(R.id.wxxx);
        Button qxx = view.findViewById(R.id.qxx);
        wx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用url分享方法
                ShareUtils instance = ShareUtils.getInstance();
                instance.shareUrlToWx(longUrl, title1,
                        content, img, 0, null);
                window1.dismiss();
            }
        });
        pyq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用url分享方法
                ShareUtils instance = ShareUtils.getInstance();
                instance.shareUrlToWx(longUrl, title1,
                        content, img, 1, null);
                window1.dismiss();
            }
        });
        qxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window1.dismiss();
            }
        });
        View inflate = LayoutInflater.from(context).inflate(R.layout.activity_main, null);
        window1.showAtLocation(inflate, Gravity.BOTTOM, 0, 0);

    }

}
