package com.zupu.zp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/31 17:29
 * update: $date$
 */
public class BookBean implements Serializable {
    private String name;
    private String page;
    private int indexs;
    public int getIndexs() {
        return indexs;
    }

    public void setIndexs(int indexs) {
        this.indexs = indexs;
    }

    private ZpBean.FamilyListBean listbean;
    public String getName() {
        return name;
    }

    public ZpBean.FamilyListBean getListbean() {
        return listbean;
    }

    public void setListbean(ZpBean.FamilyListBean listbean) {
        this.listbean = listbean;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
