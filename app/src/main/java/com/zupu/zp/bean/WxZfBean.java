package com.zupu.zp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/2 17:13
 * update: $date$
 */
public class WxZfBean {

    /**
     * appid : wx3c8adc40cdcd29e6
     * noncestr : suanmjvgb9bntpqh
     * package : Sign=WXPay
     * partnerid : 1530323921
     * prepayid : wx13141229251146e555e70b9e1889011800
     * sign : E37C81AE15250F17530C62B7016B8BC3
     * timestamp : 1576217550
     */

    private String appid;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String sign;
    private int timestamp;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
}
