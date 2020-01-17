package com.zupu.zp.bean;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/2 17:17
 * update: $date$
 */
public class ZfbBean {

    /**
     * msg : success
     * code : 0
     * payType : 2
     * data : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2019030663468386&biz_content=%7B%22body%22%3A%22%22%2C%22out_trade_no%22%3A%2290000000001502862445%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E4%BD%99%E9%A2%9D%E5%85%85%E5%80%BC-%E3%80%90%E5%AE%97%E4%BA%B2%E3%80%91%E4%BD%99%E9%A2%9D%E5%85%85%E5%80%BC%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.01%22%7D&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=https%3A%2F%2Fzupu.honarise.com%2FalipayNotify%2FpayNotify&sign=uRhiNIp7oG8vjK%2BAQ%2FyG65CwYBARKkwfw6Q0vC7nrx0fTk4ht4ji2h%2BXHyvdAOhMp9Xpyfwv4e1fE9gHLHMUXpj5fjT9PHL%2FmBEaVqhr%2FYql7S6mxQWyW%2FR1%2B%2BchYYWBhva8K%2Fpz8kr4VlA4OAAiIFTtAuw3UT%2FSSPUeaq1SGCDZHPpI6I5FSCD9oEhjEjCx0Nemqouv40ECdxD1TGPATdZ1PXP%2BKAYr%2FX4S5vnXezg1XG6OIyjoQ0En%2BSq7Gazkn2%2FoVikUFF3ByxtC2RRnOsiDD%2F7Eyd%2BIrtrOMcpZIXYKnZNvz35z5Ttyh7kDyKh7L20RKmU5FPEHUAnNGM2z%2Fw%3D%3D&sign_type=RSA2&timestamp=2019-12-02+15%3A36%3A42&version=1.0&sign=uRhiNIp7oG8vjK%2BAQ%2FyG65CwYBARKkwfw6Q0vC7nrx0fTk4ht4ji2h%2BXHyvdAOhMp9Xpyfwv4e1fE9gHLHMUXpj5fjT9PHL%2FmBEaVqhr%2FYql7S6mxQWyW%2FR1%2B%2BchYYWBhva8K%2Fpz8kr4VlA4OAAiIFTtAuw3UT%2FSSPUeaq1SGCDZHPpI6I5FSCD9oEhjEjCx0Nemqouv40ECdxD1TGPATdZ1PXP%2BKAYr%2FX4S5vnXezg1XG6OIyjoQ0En%2BSq7Gazkn2%2FoVikUFF3ByxtC2RRnOsiDD%2F7Eyd%2BIrtrOMcpZIXYKnZNvz35z5Ttyh7kDyKh7L20RKmU5FPEHUAnNGM2z%2Fw%3D%3D
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
