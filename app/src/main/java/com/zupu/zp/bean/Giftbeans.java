package com.zupu.zp.bean;

import java.util.List;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/11/6 19:55
 * update: $date$
 */
public class Giftbeans {
    /**
     * msg : success
     * code : 0
     * list : [{"id":1,"giftName":"测试1","picture":"http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/565785d208f54f00ae5205470de6e070.png","amount":11,"isShow":"1","isDelete":"N","greateTime":null,"updateTime":1565020800000,"extPara1":null,"extPara2":null,"extPara3":null,"extPara4":null,"extPara5":null,"extPara6":null},{"id":2,"giftName":"a","picture":"http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/dbd2ad0193724ff68104164bcff0cc4c.png","amount":1,"isShow":"1","isDelete":"N","greateTime":1564934400000,"updateTime":1564934400000,"extPara1":null,"extPara2":null,"extPara3":null,"extPara4":null,"extPara5":null,"extPara6":null},{"id":3,"giftName":"月饼","picture":"http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/1c6cd595b1014ab5b9483384f7799934.png","amount":1,"isShow":"1","isDelete":"N","greateTime":1565020800000,"updateTime":1565020800000,"extPara1":null,"extPara2":null,"extPara3":null,"extPara4":null,"extPara5":null,"extPara6":null},{"id":4,"giftName":"甜点","picture":"http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/b5bb9924864a447aba46e1a4912b6fe3.png","amount":2,"isShow":"1","isDelete":"N","greateTime":1565020800000,"updateTime":1565020800000,"extPara1":null,"extPara2":null,"extPara3":null,"extPara4":null,"extPara5":null,"extPara6":null},{"id":5,"giftName":"食物","picture":"http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/4a59f39f23384c0c96d66732a5da3a97.png","amount":3,"isShow":"1","isDelete":"N","greateTime":1565020800000,"updateTime":1565020800000,"extPara1":null,"extPara2":null,"extPara3":null,"extPara4":null,"extPara5":null,"extPara6":null},{"id":6,"giftName":"麻花","picture":"http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/3772bc4c411544d4aac4c6f724d8004c.png","amount":4,"isShow":"1","isDelete":"N","greateTime":1565020800000,"updateTime":1565020800000,"extPara1":null,"extPara2":null,"extPara3":null,"extPara4":null,"extPara5":null,"extPara6":null},{"id":7,"giftName":"糕点","picture":"http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/fc98c92fc948497f94e4e8bfacbaee1f.png","amount":5,"isShow":"1","isDelete":"N","greateTime":1565020800000,"updateTime":1565020800000,"extPara1":null,"extPara2":null,"extPara3":null,"extPara4":null,"extPara5":null,"extPara6":null},{"id":8,"giftName":"衣服","picture":"http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/565e1a62fa624f458e4dd27c023c5c34.png","amount":6,"isShow":"1","isDelete":"N","greateTime":1565020800000,"updateTime":1566316800000,"extPara1":null,"extPara2":null,"extPara3":null,"extPara4":null,"extPara5":null,"extPara6":null},{"id":9,"giftName":"衣服2","picture":"http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/ef7a125847254ba88bb71ce7ae6bcb81.png","amount":8,"isShow":"1","isDelete":"N","greateTime":1565020800000,"updateTime":1565020800000,"extPara1":null,"extPara2":null,"extPara3":null,"extPara4":null,"extPara5":null,"extPara6":null}]
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
         * id : 1
         * giftName : 测试1
         * picture : http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/565785d208f54f00ae5205470de6e070.png
         * amount : 11
         * isShow : 1
         * isDelete : N
         * greateTime : null
         * updateTime : 1565020800000
         * extPara1 : null
         * extPara2 : null
         * extPara3 : null
         * extPara4 : null
         * extPara5 : null
         * extPara6 : null
         */

        private int id;
        private String giftName;
        private String picture;
        private int amount;
        private String isShow;
        private String isDelete;
        private Object greateTime;
        private long updateTime;
        private Object extPara1;
        private Object extPara2;
        private Object extPara3;
        private Object extPara4;
        private Object extPara5;
        private Object extPara6;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGiftName() {
            return giftName;
        }

        public void setGiftName(String giftName) {
            this.giftName = giftName;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public String getIsShow() {
            return isShow;
        }

        public void setIsShow(String isShow) {
            this.isShow = isShow;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
        }

        public Object getGreateTime() {
            return greateTime;
        }

        public void setGreateTime(Object greateTime) {
            this.greateTime = greateTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public Object getExtPara1() {
            return extPara1;
        }

        public void setExtPara1(Object extPara1) {
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
    }
}
