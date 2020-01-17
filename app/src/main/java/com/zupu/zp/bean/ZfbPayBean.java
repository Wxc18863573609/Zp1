package com.zupu.zp.bean;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/14 11:49
 * update: $date$
 */
public class ZfbPayBean {
    /**
     * msg : success
     * code : 0
     * payType : 2
     * data : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2019030663468386&biz_content=%7B%22body%22%3A%22%22%2C%22out_trade_no%22%3A%2290000000000043060382%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E4%BD%99%E9%A2%9D%E5%85%85%E5%80%BC-%E4%BD%99%E9%A2%9D%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.1%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fzupu.honarise.com%2FalipayNotify%2FpayNotify&sign=R2vRugUZgCR9sWqYOnUq2Gr4Q9%2FGVnkGLI4Yp%2FeOV57EbsxM%2F%2BVCvLjbD5T5LWyf0WlbvOnx0OyBEFc250%2FNWe8FPxxt7tM3bRLJ0ooUMhpoM7xn9CIHctYnEA1EV9YnJUfhe%2FojDKA9t4RKBchQfHe%2FOgU%2FiIpvTRZ59PKJBDrtclFloAodSvjSjK%2F3MI0lH2OJtmeMWt9n%2B9RpEkmK11oORTshSVjRCkd7%2F9I4jSu3qV%2F4E5JP0Wm1hKBx%2BBSeI%2FeWh3e3zzOkkBDym0tzDYY3nDV7NQQGPHUVOgDLgGoIwc9VUY5IgUmKXo8v%2BklmBupVA5sQNyUNi7mMwPinAw%3D%3D&sign_type=RSA2&timestamp=2019-12-14+11%3A47%3A53&version=1.0&sign=R2vRugUZgCR9sWqYOnUq2Gr4Q9%2FGVnkGLI4Yp%2FeOV57EbsxM%2F%2BVCvLjbD5T5LWyf0WlbvOnx0OyBEFc250%2FNWe8FPxxt7tM3bRLJ0ooUMhpoM7xn9CIHctYnEA1EV9YnJUfhe%2FojDKA9t4RKBchQfHe%2FOgU%2FiIpvTRZ59PKJBDrtclFloAodSvjSjK%2F3MI0lH2OJtmeMWt9n%2B9RpEkmK11oORTshSVjRCkd7%2F9I4jSu3qV%2F4E5JP0Wm1hKBx%2BBSeI%2FeWh3e3zzOkkBDym0tzDYY3nDV7NQQGPHUVOgDLgGoIwc9VUY5IgUmKXo8v%2BklmBupVA5sQNyUNi7mMwPinAw%3D%3D
     * isOk : ok
     * platform : 2
     */

    private String msg;
    private int code;
    private String payType;
    private String data;
    private String isOk;
    private String platform;

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

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIsOk() {
        return isOk;
    }

    public void setIsOk(String isOk) {
        this.isOk = isOk;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
