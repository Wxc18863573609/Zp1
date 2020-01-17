package com.zupu.zp.bean;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/26 10:28
 * update: $date$
 */
public class GetuiBean {

    /**
     * userName : 13064802951
     * userHead : https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/images/seat_occupying/applogo.png
     * userid : 203
     * roomid : -103
     */

    private String userName;
    private String userHead;
    private String userid;
    private String roomid;
    private String title;
    private boolean isvoice;

    public String getTitle() {
        return title;
    }

    public boolean isIsvoice() {
        return isvoice;
    }

    public void setIsvoice(boolean isvoice) {
        this.isvoice = isvoice;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRoomid() {
        return roomid;
    }

    public void setRoomid(String roomid) {
        this.roomid = roomid;
    }
}
