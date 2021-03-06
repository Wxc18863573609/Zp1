package com.zupu.zp.bean;

import java.util.List;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/11 18:51
 * update: $date$
 */
public class LiveBean {
    /**
     * msg : success
     * code : 0
     * list : [{"id":536,"roomNumber":"2019092519536128","userId":128,"createTime":1569410464000,"status":"2","openingTime":1569410464000,"closingTime":null,"title":"快来我的直播间吧","cover":"https://zupu-resource.oss-cn-beijing.aliyuncs.com/live_room/cover_photo/image/3286941192793091/1569410460745.PNG","type":"1","userNum":0,"isplayback":"0","replayurl":null,"closeType":null,"extPara1":null,"extPara2":null,"extPara3":null,"extPara4":null,"extPara5":null,"extPara6":null,"extPara7":null,"extPara8":null,"extPara9":null,"extPara10":null,"user":null}]
     */

    private String msg;
    private int code;
    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 536
         * roomNumber : 2019092519536128
         * userId : 128
         * createTime : 1569410464000
         * status : 2
         * openingTime : 1569410464000
         * closingTime : null
         * title : 快来我的直播间吧
         * cover : https://zupu-resource.oss-cn-beijing.aliyuncs.com/live_room/cover_photo/image/3286941192793091/1569410460745.PNG
         * type : 1
         * userNum : 0
         * isplayback : 0
         * replayurl : null
         * closeType : null
         * extPara1 : null
         * extPara2 : null
         * extPara3 : null
         * extPara4 : null
         * extPara5 : null
         * extPara6 : null
         * extPara7 : null
         * extPara8 : null
         * extPara9 : null
         * extPara10 : null
         * user : null
         */

        private int id;
        private String roomNumber;
        private int userId;
        private long createTime;
        private String status;
        private long openingTime;
        private Object closingTime;
        private String title;
        private String cover;
        private String type;
        private int userNum;
        private String isplayback;
        private String replayurl;
        private Object closeType;
        private String extPara1;
        private Object extPara2;
        private Object extPara3;
        private Object extPara4;
        private Object extPara5;
        private Object extPara6;
        private Object extPara7;
        private Object extPara8;
        private Object extPara9;
        private Object extPara10;
        private Object user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getRoomNumber() {
            return roomNumber;
        }

        public void setRoomNumber(String roomNumber) {
            this.roomNumber = roomNumber;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public long getOpeningTime() {
            return openingTime;
        }

        public void setOpeningTime(long openingTime) {
            this.openingTime = openingTime;
        }

        public Object getClosingTime() {
            return closingTime;
        }

        public void setClosingTime(Object closingTime) {
            this.closingTime = closingTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getUserNum() {
            return userNum;
        }

        public void setUserNum(int userNum) {
            this.userNum = userNum;
        }

        public String getIsplayback() {
            return isplayback;
        }

        public void setIsplayback(String isplayback) {
            this.isplayback = isplayback;
        }

        public String getReplayurl() {
            return replayurl;
        }

        public void setReplayurl(String replayurl) {
            this.replayurl = replayurl;
        }

        public Object getCloseType() {
            return closeType;
        }

        public void setCloseType(Object closeType) {
            this.closeType = closeType;
        }

        public String getExtPara1() {
            return extPara1;
        }

        public void setExtPara1(String extPara1) {
            this.extPara1 = extPara1;
        }

        public Object getExtPara2() {
            return extPara2;
        }

        public void setExtPara2(Object extPara2) {
            this.extPara2 = extPara2;
        }

        public Object getExtPara3() {
            return extPara3;
        }

        public void setExtPara3(Object extPara3) {
            this.extPara3 = extPara3;
        }

        public Object getExtPara4() {
            return extPara4;
        }

        public void setExtPara4(Object extPara4) {
            this.extPara4 = extPara4;
        }

        public Object getExtPara5() {
            return extPara5;
        }

        public void setExtPara5(Object extPara5) {
            this.extPara5 = extPara5;
        }

        public Object getExtPara6() {
            return extPara6;
        }

        public void setExtPara6(Object extPara6) {
            this.extPara6 = extPara6;
        }

        public Object getExtPara7() {
            return extPara7;
        }

        public void setExtPara7(Object extPara7) {
            this.extPara7 = extPara7;
        }

        public Object getExtPara8() {
            return extPara8;
        }

        public void setExtPara8(Object extPara8) {
            this.extPara8 = extPara8;
        }

        public Object getExtPara9() {
            return extPara9;
        }

        public void setExtPara9(Object extPara9) {
            this.extPara9 = extPara9;
        }

        public Object getExtPara10() {
            return extPara10;
        }

        public void setExtPara10(Object extPara10) {
            this.extPara10 = extPara10;
        }

        public Object getUser() {
            return user;
        }

        public void setUser(Object user) {
            this.user = user;
        }
    }
}
