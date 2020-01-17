package com.zupu.zp.bean;

import java.util.List;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/7 18:00
 * update: $date$
 */
public class QueryGZbean {
    /**
     * msg : success
     * code : 0
     * list : []
     */

    private String msg;
    private int code;
    private List<?> list;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
