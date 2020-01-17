package com.zupu.zp.bean;

import com.google.gson.annotations.SerializedName;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/18 11:03
 * update: $date$
 */
public class DonwloadBean {

    /**
     * msg : success
     * currentLink : https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/apk/app-debug.apk
     * code : 0
     * results : {"1.0":"https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/apk/app-debug.apk","2.0":"https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/apk/app-debug.apk"}
     * currentVer : 1.0
     */

    private String msg;
    private String currentLink;
    private int code;
    private ResultsBean results;
    private String currentVer;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCurrentLink() {
        return currentLink;
    }

    public void setCurrentLink(String currentLink) {
        this.currentLink = currentLink;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public ResultsBean getResults() {
        return results;
    }

    public void setResults(ResultsBean results) {
        this.results = results;
    }

    public String getCurrentVer() {
        return currentVer;
    }

    public void setCurrentVer(String currentVer) {
        this.currentVer = currentVer;
    }

    public static class ResultsBean {
        @SerializedName("1.0")
        private String _$_1010; // FIXME check this code
        @SerializedName("2.0")
        private String _$_2041; // FIXME check this code

        public String get_$_1010() {
            return _$_1010;
        }

        public void set_$_1010(String _$_1010) {
            this._$_1010 = _$_1010;
        }

        public String get_$_2041() {
            return _$_2041;
        }

        public void set_$_2041(String _$_2041) {
            this._$_2041 = _$_2041;
        }
    }
}
