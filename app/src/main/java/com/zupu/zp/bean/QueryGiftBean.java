package com.zupu.zp.bean;

import java.util.List;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/16 12:19
 * update: $date$
 */
public class QueryGiftBean {
    /**
     * msg : success
     * list1 : ["li","http://thirdwx.qlogo.cn/mmopen/vi_32/ial6PfbD236vWcW1b2p2jqsguQxGrg1ODsVHkefNbbiblL5L7lrH5wpm1kic1oShbIQa3g5g7ToFVXXbS3QCXkvIw/132","测试1","http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/565785d208f54f00ae5205470de6e070.png","10"]
     * code : 0
     */

    private String msg;
    private int code;
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

    public List<String> getList1() {
        return list1;
    }

    public void setList1(List<String> list1) {
        this.list1 = list1;
    }
}
