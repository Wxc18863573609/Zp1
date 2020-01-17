package com.zupu.zp.bean;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/14 11:23
 * update: $date$
 */
public class DdBean {

    /**
     * msg : success
     * code : 0
     * orderId : 6687
     * paymentAmount : null
     * orderpId : 7777
     */

    private String msg;
    private int code;
    private int orderId;
    private Object paymentAmount;
    private int orderpId;

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

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Object getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Object paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public int getOrderpId() {
        return orderpId;
    }

    public void setOrderpId(int orderpId) {
        this.orderpId = orderpId;
    }
}
