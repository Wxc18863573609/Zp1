package com.zupu.zp.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zupu.zp.R;
import com.zupu.zp.base.mvp_no_dagger.BaseFragment;
import com.zupu.zp.bean.ZpBean;
import com.zupu.zp.entity.ZegoApplication;

import java.util.ArrayList;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/12 11:08
 * update: $date$
 */
public class BookSeavenFragment extends BaseFragment {


    private View view;
    private TextView nshi;
    private TextView xmqk;
    private TextView number;
    private LinearLayout tableft;
    private TextView name;
    private TextView sheng;
    private TextView si;
    private ImageView imghead;
    private TextView poname;
    private TextView sheng1;
    private TextView si1;
    private ImageView pohead;
    private TextView fm;
    private TextView zn1;
    private LinearLayout rrtab;
    private LinearLayout tabrighttop;
    private TextView jianjie;
    private LinearLayout tabrightbootm;
    private LinearLayout tabright;
    private RelativeLayout aad;
    private RelativeLayout renoe;
    private TextView nshi1;
    private TextView xmqk1;
    private TextView number1;
    private LinearLayout tableft1;
    private TextView name1;
    private ImageView imghead1;
    private TextView poname1;
    private TextView sheng2;
    private TextView si2;
    private ImageView pohead1;
    private TextView fm1;
    private TextView zn2;
    private LinearLayout rrtab1;
    private LinearLayout tabrighttop1;
    private TextView jianjie1;
    private LinearLayout tabrightbootm1;
    private LinearLayout tabright1;
    private RelativeLayout aad1;
    private RelativeLayout retow;
    private TextView sheng11;
    private TextView si11;
    private TextView pwsheng1;
    private TextView pwsi1;
    private TextView pwsheng2;
    private TextView pwsi2;

    @Override
    public View initlayout(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.booksevnlayout, container, false);
        return view;
    }

    @Override
    public void initview() {
        xmqk = view.findViewById(R.id.xmqk);
        xmqk1 = view.findViewById(R.id.xmqk1);
        sheng = view.findViewById(R.id.sheng);
        sheng1 = view.findViewById(R.id.sheng1);
        pwsheng1 = view.findViewById(R.id.pwsheng1);
        pwsheng2 = view.findViewById(R.id.pwsheng2);
        pwsi1 = view.findViewById(R.id.pwsi1);
        pwsi2 = view.findViewById(R.id.pwsi2);
        si = view.findViewById(R.id.si);
        si1 = view.findViewById(R.id.si1);
        zn1 = view.findViewById(R.id.zn1);
        zn2 = view.findViewById(R.id.zn2);
        fm = view.findViewById(R.id.fm);
        fm1 = view.findViewById(R.id.fm1);
        name = view.findViewById(R.id.name);
        name1 = view.findViewById(R.id.name1);
        nshi = view.findViewById(R.id.nshi);
        nshi1 = view.findViewById(R.id.nshi1);
        number = view.findViewById(R.id.number);
        number1 = view.findViewById(R.id.number1);
        jianjie = view.findViewById(R.id.jianjie);
        jianjie1 = view.findViewById(R.id.jianjie1);
        imghead= view.findViewById(R.id.imghead);
        imghead1= view.findViewById(R.id.imghead1);
        pohead= view.findViewById(R.id.pohead);
        pohead1= view.findViewById(R.id.pohead1);
    }

    @Override
    public void initdata() {
        Bundle bundle = this.getArguments();//得到从Activity传来的数据
        if (bundle != null) {
            ArrayList<ZpBean.FamilyListBean> data = (ArrayList<ZpBean.FamilyListBean>) bundle.getSerializable("datas");
            if (data.get(0).getName() != null) {
                reSizeTextView(name, data.get(0).getSurname() + data.get(0).getName() + "", 100);
            }
            if (data.size()>=2&&data.get(1).getName() != null) {
                reSizeTextView(name1, data.get(1).getSurname() + data.get(1).getName() + "", 100);
            }
            if (data.get(0).getBirthdayTime() != null) {
                reSizeTextView(sheng, data.get(0).getBirthdayTime() + "", 100);
            }
            if (data.size()>=2&&data.get(1).getBirthdayTime() != null) {
                reSizeTextView(sheng1, data.get(1).getBirthdayTime() + "", 100);
            }
            if (data.get(0).getDeathTime() != null) {
                reSizeTextView(si, data.get(0).getDeathTime() + "", 100);
            }
            if (data.size()>=2&&data.get(1).getDeathTime() != null) {
                reSizeTextView(si1, data.get(1).getDeathTime() + "", 100);
            }
            if (data.get(0).getMother() != null && data.get(0).getFather() != null) {
                reSizeTextView(fm, "" + data.get(0).getFather().getSurname() + data.get(0).getFather().getName() + " " + data.get(0).getMother().getSurname() + data.get(0).getMother().getName(), 270);
            }
            if (data.size()>=2&&data.get(1).getMother() != null && data.get(1).getFather() != null) {
                reSizeTextView(fm, "" + data.get(1).getFather().getSurname() + data.get(1).getFather().getName() + " " + data.get(1).getMother().getSurname() + data.get(1).getMother().getName(), 270);
            }
            if (0 != data.get(0).getRankingSeniority()) {
                nshi.setText(ZegoApplication.index - data.get(0).getRankingSeniority() + 1 + "世");
            }
            if (data.size()>=2&&0 != data.get(1).getRankingSeniority()) {
                nshi1.setText(ZegoApplication.index - data.get(1).getRankingSeniority() + 1 + "世");
            }
            if (data.get(0).getExtPara7() != 0) {
                xmqk.setText("兄妹" + data.get(0).getExtPara7() + "人");
            }
            if (data.size()>=2&&data.get(1).getExtPara7() != 0) {
                xmqk1.setText("兄妹" + data.get(1).getExtPara7() + "人");
            }
            if (data.get(0).getAgeRanking() != null) {
                number.setText(data.get(0).getAgeRanking() + "");
            }
            if (data.size()>=2&&data.get(1).getAgeRanking() != null) {
                number1.setText(data.get(1).getAgeRanking() + "");
            }
            if (data.get(0).getExtPara6() != null) {
                jianjie.setText(data.get(0).getExtPara6() + "");
            }
            if (data.size()>=2&&data.get(1).getExtPara6() != null) {
                jianjie1.setText(data.get(1).getExtPara6() + "");
            }

            if (null!=data.get(0).getSpouseEntity()) {
                if (null!=data.get(0).getSpouseEntity().getBirthdayTime())
                pwsheng1.setText(data.get(0).getSpouseEntity().getBirthdayTime()+"");
            }
            if (data.size()>=2&&null!=data.get(1).getSpouseEntity()) {
               if (null!=data.get(1).getSpouseEntity().getBirthdayTime())
                pwsheng2.setText(data.get(1).getSpouseEntity().getBirthdayTime()+"");
            }
            if (null!=data.get(0).getSpouseEntity()) {
                if (null!=data.get(0).getSpouseEntity().getDeathTime())
                    pwsi1.setText(data.get(0).getSpouseEntity().getDeathTime()+"");
            }
            if (data.size()>=2&&null!=data.get(1).getSpouseEntity()) {
                if (null!=data.get(1).getSpouseEntity().getDeathTime())
                pwsi2.setText(data.get(1).getSpouseEntity().getDeathTime()+"");
            }
            if (null!=data.get(0).getPhontUrl()) {
                Glide.with(getActivity()).load(data.get(0).getPhontUrl()).error(R.drawable.zhanwei).into(imghead);
            }
            if (data.size()>=2&&null!=data.get(1).getPhontUrl()) {
                Glide.with(getActivity()).load(data.get(1).getPhontUrl()).error(R.drawable.zhanwei).into(imghead1);
            }

            if (null!=data.get(0).getSpouseEntity()) {
                if (null!=data.get(0).getSpouseEntity().getPhontUrl())
                Glide.with(getActivity()).load(data.get(0).getSpouseEntity().getPhontUrl()).error(R.drawable.zhanwei).into(pohead);
            }
            if (data.size()>=2&&null!=data.get(1).getSpouseEntity()) {
                if (null!=data.get(1).getSpouseEntity().getPhontUrl() )
                Glide.with(getActivity()).load(data.get(1).getSpouseEntity().getPhontUrl()).error(R.drawable.zhanwei).into(pohead1);
            }

            if (null!=data.get(0).getChildList()) {
                String str="";
                for (int i = 0; i <data.get(0).getChildList().size() ; i++) {
                    str= str+data.get(0).getChildList().get(i).getSurname()+data.get(0).getChildList().get(i).getName();
                }
                reSizeTextView(zn1,str,200);
            }
            if (data.size()>=2&&null!=data.get(1).getChildList()) {
                String str="";
                for (int i = 0; i <data.get(1).getChildList().size() ; i++) {
                    str= str+data.get(1).getChildList().get(i).getSurname()+data.get(1).getChildList().get(i).getName();
                }
                reSizeTextView(zn2,str,200);
            }
        }

    }

    @Override
    public void initlinsenter() {

    }

    @Override
    public void persenter() {

    }

    @Override
    public void sucecess(Object o) {


    }

    private void reSizeTextView(TextView textView, String text, float maxWidth) {
        Paint paint = textView.getPaint();
        float textWidth = paint.measureText(text);
        int textSizeInDp = 30;

        if (textWidth > maxWidth) {
            for (; textSizeInDp > 0; textSizeInDp--) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, textSizeInDp);
                paint = textView.getPaint();
                textWidth = paint.measureText(text);
                if (textWidth <= maxWidth) {
                    break;
                }
            }
        }
        textView.setText(text);
        textView.invalidate();
    }




/*    public static float adjustTvTextSize(TextView tv, int maxWidth, String text) {
        int avaiWidth = maxWidth - tv.getPaddingLeft() - tv.getPaddingRight() - 10;

        if (avaiWidth <= 0 || StringUtils.isEmpty(text)) {
            return tv.getPaint().getTextSize();
        }

        TextPaint textPaintClone = new TextPaint(tv.getPaint());
        // note that Paint text size works in px not sp
        float trySize = textPaintClone.getTextSize();

        while (textPaintClone.measureText(text) > avaiWidth) {
            trySize--;
            textPaintClone.setTextSize(trySize);
        }

        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        return trySize;
    }*/


}
