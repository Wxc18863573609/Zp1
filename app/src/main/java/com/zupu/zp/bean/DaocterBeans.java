package com.zupu.zp.bean;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/12 18:01
 * update: $date$
 */
public class DaocterBeans {

    /**
     * roomID : 23
     * userid : 114
     * username : 张策
     * userHead : https: //zupu-resource.oss-cn-beijing.aliyuncs.com/945164130169390/1569145359999/.jpg
     * isvoice : false
     * time : 1800000
     * parameters : 1
     */

    private int roomID;
    private int userid;
    private String username;
    private String userHead;
    private boolean isvoice;
    private int time;
    private int parameters;

    public int getRoomID() {
        return roomID;
    }

    public void setRoomID(int roomID) {
        this.roomID = roomID;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public boolean isIsvoice() {
        return isvoice;
    }

    public void setIsvoice(boolean isvoice) {
        this.isvoice = isvoice;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getParameters() {
        return parameters;
    }

    public void setParameters(int parameters) {
        this.parameters = parameters;
    }
}
