package com.zupu.zp.bean;

import java.util.ArrayList;

/**
 * description: dell
 * author: 王新昌
 * date: 2020/1/7 15:46
 * update: $date$
 */
public class PageBean {
    int pag;
    String name;
    ArrayList<ZpBean.FamilyListBean> data;

    public ArrayList<ZpBean.FamilyListBean> getData() {
        return data;
    }

    public void setData(ArrayList<ZpBean.FamilyListBean> data) {
        this.data = data;
    }

    public int getPag() {
        return pag;
    }

    public void setPag(int pag) {
        this.pag = pag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
