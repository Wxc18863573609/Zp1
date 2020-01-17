package com.zupu.zp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/14 11:43
 * update: $date$
 */
public class WxPayBean {
    /**
     * msg : success
     * code : 0
     * payType : 1
     * data : {"package":"Sign=WXPay","appid":"wx3c8adc40cdcd29e6","paystr":{"appid":"wx3c8adc40cdcd29e6","noncestr":"saozbjmse67pqz8b","package":"Sign=WXPay","partnerid":"1530323921","prepayid":"wx14114256776540074f9955201159332400","sign":"DF25B43BE9B605C4FE1D487FE9A598A1","timestamp":1576294977},"sign":"DF25B43BE9B605C4FE1D487FE9A598A1","partnerid":"1530323921","prepayid":"wx14114256776540074f9955201159332400","retmsg":"ok","noncestr":"saozbjmse67pqz8b","retcode":0,"timestamp":1576294977}
     * isOk : ok
     * platform : 1
     */

    private String msg;
    private int code;
    private String payType;
    private DataBean data;
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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
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

    public static class DataBean {
        /**
         * package : Sign=WXPay
         * appid : wx3c8adc40cdcd29e6
         * paystr : {"appid":"wx3c8adc40cdcd29e6","noncestr":"saozbjmse67pqz8b","package":"Sign=WXPay","partnerid":"1530323921","prepayid":"wx14114256776540074f9955201159332400","sign":"DF25B43BE9B605C4FE1D487FE9A598A1","timestamp":1576294977}
         * sign : DF25B43BE9B605C4FE1D487FE9A598A1
         * partnerid : 1530323921
         * prepayid : wx14114256776540074f9955201159332400
         * retmsg : ok
         * noncestr : saozbjmse67pqz8b
         * retcode : 0
         * timestamp : 1576294977
         */

        @SerializedName("package")
        private String packageX;
        private String appid;
        private PaystrBean paystr;
        private String sign;
        private String partnerid;
        private String prepayid;
        private String retmsg;
        private String noncestr;
        private int retcode;
        private int timestamp;

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public PaystrBean getPaystr() {
            return paystr;
        }

        public void setPaystr(PaystrBean paystr) {
            this.paystr = paystr;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
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

        public String getRetmsg() {
            return retmsg;
        }

        public void setRetmsg(String retmsg) {
            this.retmsg = retmsg;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public int getRetcode() {
            return retcode;
        }

        public void setRetcode(int retcode) {
            this.retcode = retcode;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public static class PaystrBean {
            /**
             * appid : wx3c8adc40cdcd29e6
             * noncestr : saozbjmse67pqz8b
             * package : Sign=WXPay
             * partnerid : 1530323921
             * prepayid : wx14114256776540074f9955201159332400
             * sign : DF25B43BE9B605C4FE1D487FE9A598A1
             * timestamp : 1576294977
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
    }
}
