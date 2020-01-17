package com.zupu.zp.bean;

import java.util.List;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/27 21:03
 * update: $date$
 */
public class QueryPKGiftBean {
    /**
     * msg : success
     * list1 : ["13064802951","https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/images/seat_occupying/applogo.png","6","麻花","http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/3772bc4c411544d4aac4c6f724d8004c.png","10"]
     * code : 0
     * userName : 李世栋
     */

    private String msg;
    private int code;
    private String userName;
    private List<String> list1;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<String> getList1() {
        return list1;
    }

    public void setList1(List<String> list1) {
        this.list1 = list1;
    }
}
