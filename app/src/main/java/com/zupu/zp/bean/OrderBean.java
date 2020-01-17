package com.zupu.zp.bean;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/23 13:15
 * update: $date$
 */
public class OrderBean {

    /**
     * msg : 请前往支付
     * code : 0
     * orderInfo : {"id":7796,"orderNo":"70000000000083318131","type":"n","expressPrice":null,"productPrice":1,"payType":null,"tradeType":null,"payAccount":null,"thirdOrderNo":null,"buyerId":203,"createTime":1577086408614,"closeTime":1577087308614,"payTime":null,"receiverName":null,"receiverPhone":null,"receiverProvinceCode":null,"receiverCityCode":null,"receiverCountryCode":null,"receiverProvinceName":null,"receiverCityName":null,"receiverCountryName":null,"receiverAddress":null,"status":"0","cancelTime":null,"cancelReason":null,"buyerMessage":null,"isCommont":null,"remark":"开通主播","mallNo":1,"mallId":null,"mallName":"宗亲平台","mallPic":"https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/images/seat_occupying/applogo.png","sendTime":null,"receiveTime":null,"finalRefundTime":null,"deliveryType":null,"expressNo":null,"isExistenceRefund":null,"refundMoney":null,"updateTime":1577086408614,"isShowBuyer":null,"isDistribution":null,"originalOrderInfoId":null,"isDelete":"1","extPara1":null,"extPara2":null,"extPara3":null,"extPara4":null,"extPara5":null,"extPara6":null,"extPara7":null,"extPara8":null,"extPara9":null,"extPara10":null,"orderProductList":null,"orderProduct":null,"user":null,"originalOrderInfo":null,"orderProductEntity":null,"isAuthorization":null}
     * status : 3001
     */

    private String msg;
    private int code;
    private OrderInfoBean orderInfo;
    private String status;

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

    public OrderInfoBean getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfoBean orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class OrderInfoBean {
        /**
         * id : 7796
         * orderNo : 70000000000083318131
         * type : n
         * expressPrice : null
         * productPrice : 1
         * payType : null
         * tradeType : null
         * payAccount : null
         * thirdOrderNo : null
         * buyerId : 203
         * createTime : 1577086408614
         * closeTime : 1577087308614
         * payTime : null
         * receiverName : null
         * receiverPhone : null
         * receiverProvinceCode : null
         * receiverCityCode : null
         * receiverCountryCode : null
         * receiverProvinceName : null
         * receiverCityName : null
         * receiverCountryName : null
         * receiverAddress : null
         * status : 0
         * cancelTime : null
         * cancelReason : null
         * buyerMessage : null
         * isCommont : null
         * remark : 开通主播
         * mallNo : 1
         * mallId : null
         * mallName : 宗亲平台
         * mallPic : https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/images/seat_occupying/applogo.png
         * sendTime : null
         * receiveTime : null
         * finalRefundTime : null
         * deliveryType : null
         * expressNo : null
         * isExistenceRefund : null
         * refundMoney : null
         * updateTime : 1577086408614
         * isShowBuyer : null
         * isDistribution : null
         * originalOrderInfoId : null
         * isDelete : 1
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
         * orderProductList : null
         * orderProduct : null
         * user : null
         * originalOrderInfo : null
         * orderProductEntity : null
         * isAuthorization : null
         */

        private int id;
        private String orderNo;
        private String type;
        private Object expressPrice;
        private int productPrice;
        private Object payType;
        private Object tradeType;
        private Object payAccount;
        private Object thirdOrderNo;
        private int buyerId;
        private long createTime;
        private long closeTime;
        private Object payTime;
        private Object receiverName;
        private Object receiverPhone;
        private Object receiverProvinceCode;
        private Object receiverCityCode;
        private Object receiverCountryCode;
        private Object receiverProvinceName;
        private Object receiverCityName;
        private Object receiverCountryName;
        private Object receiverAddress;
        private String status;
        private Object cancelTime;
        private Object cancelReason;
        private Object buyerMessage;
        private Object isCommont;
        private String remark;
        private int mallNo;
        private Object mallId;
        private String mallName;
        private String mallPic;
        private Object sendTime;
        private Object receiveTime;
        private Object finalRefundTime;
        private Object deliveryType;
        private Object expressNo;
        private Object isExistenceRefund;
        private Object refundMoney;
        private long updateTime;
        private Object isShowBuyer;
        private Object isDistribution;
        private Object originalOrderInfoId;
        private String isDelete;
        private Object extPara1;
        private Object extPara2;
        private Object extPara3;
        private Object extPara4;
        private Object extPara5;
        private Object extPara6;
        private Object extPara7;
        private Object extPara8;
        private Object extPara9;
        private Object extPara10;
        private Object orderProductList;
        private Object orderProduct;
        private Object user;
        private Object originalOrderInfo;
        private Object orderProductEntity;
        private Object isAuthorization;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getExpressPrice() {
            return expressPrice;
        }

        public void setExpressPrice(Object expressPrice) {
            this.expressPrice = expressPrice;
        }

        public int getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(int productPrice) {
            this.productPrice = productPrice;
        }

        public Object getPayType() {
            return payType;
        }

        public void setPayType(Object payType) {
            this.payType = payType;
        }

        public Object getTradeType() {
            return tradeType;
        }

        public void setTradeType(Object tradeType) {
            this.tradeType = tradeType;
        }

        public Object getPayAccount() {
            return payAccount;
        }

        public void setPayAccount(Object payAccount) {
            this.payAccount = payAccount;
        }

        public Object getThirdOrderNo() {
            return thirdOrderNo;
        }

        public void setThirdOrderNo(Object thirdOrderNo) {
            this.thirdOrderNo = thirdOrderNo;
        }

        public int getBuyerId() {
            return buyerId;
        }

        public void setBuyerId(int buyerId) {
            this.buyerId = buyerId;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getCloseTime() {
            return closeTime;
        }

        public void setCloseTime(long closeTime) {
            this.closeTime = closeTime;
        }

        public Object getPayTime() {
            return payTime;
        }

        public void setPayTime(Object payTime) {
            this.payTime = payTime;
        }

        public Object getReceiverName() {
            return receiverName;
        }

        public void setReceiverName(Object receiverName) {
            this.receiverName = receiverName;
        }

        public Object getReceiverPhone() {
            return receiverPhone;
        }

        public void setReceiverPhone(Object receiverPhone) {
            this.receiverPhone = receiverPhone;
        }

        public Object getReceiverProvinceCode() {
            return receiverProvinceCode;
        }

        public void setReceiverProvinceCode(Object receiverProvinceCode) {
            this.receiverProvinceCode = receiverProvinceCode;
        }

        public Object getReceiverCityCode() {
            return receiverCityCode;
        }

        public void setReceiverCityCode(Object receiverCityCode) {
            this.receiverCityCode = receiverCityCode;
        }

        public Object getReceiverCountryCode() {
            return receiverCountryCode;
        }

        public void setReceiverCountryCode(Object receiverCountryCode) {
            this.receiverCountryCode = receiverCountryCode;
        }

        public Object getReceiverProvinceName() {
            return receiverProvinceName;
        }

        public void setReceiverProvinceName(Object receiverProvinceName) {
            this.receiverProvinceName = receiverProvinceName;
        }

        public Object getReceiverCityName() {
            return receiverCityName;
        }

        public void setReceiverCityName(Object receiverCityName) {
            this.receiverCityName = receiverCityName;
        }

        public Object getReceiverCountryName() {
            return receiverCountryName;
        }

        public void setReceiverCountryName(Object receiverCountryName) {
            this.receiverCountryName = receiverCountryName;
        }

        public Object getReceiverAddress() {
            return receiverAddress;
        }

        public void setReceiverAddress(Object receiverAddress) {
            this.receiverAddress = receiverAddress;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getCancelTime() {
            return cancelTime;
        }

        public void setCancelTime(Object cancelTime) {
            this.cancelTime = cancelTime;
        }

        public Object getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(Object cancelReason) {
            this.cancelReason = cancelReason;
        }

        public Object getBuyerMessage() {
            return buyerMessage;
        }

        public void setBuyerMessage(Object buyerMessage) {
            this.buyerMessage = buyerMessage;
        }

        public Object getIsCommont() {
            return isCommont;
        }

        public void setIsCommont(Object isCommont) {
            this.isCommont = isCommont;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getMallNo() {
            return mallNo;
        }

        public void setMallNo(int mallNo) {
            this.mallNo = mallNo;
        }

        public Object getMallId() {
            return mallId;
        }

        public void setMallId(Object mallId) {
            this.mallId = mallId;
        }

        public String getMallName() {
            return mallName;
        }

        public void setMallName(String mallName) {
            this.mallName = mallName;
        }

        public String getMallPic() {
            return mallPic;
        }

        public void setMallPic(String mallPic) {
            this.mallPic = mallPic;
        }

        public Object getSendTime() {
            return sendTime;
        }

        public void setSendTime(Object sendTime) {
            this.sendTime = sendTime;
        }

        public Object getReceiveTime() {
            return receiveTime;
        }

        public void setReceiveTime(Object receiveTime) {
            this.receiveTime = receiveTime;
        }

        public Object getFinalRefundTime() {
            return finalRefundTime;
        }

        public void setFinalRefundTime(Object finalRefundTime) {
            this.finalRefundTime = finalRefundTime;
        }

        public Object getDeliveryType() {
            return deliveryType;
        }

        public void setDeliveryType(Object deliveryType) {
            this.deliveryType = deliveryType;
        }

        public Object getExpressNo() {
            return expressNo;
        }

        public void setExpressNo(Object expressNo) {
            this.expressNo = expressNo;
        }

        public Object getIsExistenceRefund() {
            return isExistenceRefund;
        }

        public void setIsExistenceRefund(Object isExistenceRefund) {
            this.isExistenceRefund = isExistenceRefund;
        }

        public Object getRefundMoney() {
            return refundMoney;
        }

        public void setRefundMoney(Object refundMoney) {
            this.refundMoney = refundMoney;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public Object getIsShowBuyer() {
            return isShowBuyer;
        }

        public void setIsShowBuyer(Object isShowBuyer) {
            this.isShowBuyer = isShowBuyer;
        }

        public Object getIsDistribution() {
            return isDistribution;
        }

        public void setIsDistribution(Object isDistribution) {
            this.isDistribution = isDistribution;
        }

        public Object getOriginalOrderInfoId() {
            return originalOrderInfoId;
        }

        public void setOriginalOrderInfoId(Object originalOrderInfoId) {
            this.originalOrderInfoId = originalOrderInfoId;
        }

        public String getIsDelete() {
            return isDelete;
        }

        public void setIsDelete(String isDelete) {
            this.isDelete = isDelete;
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

        public Object getOrderProductList() {
            return orderProductList;
        }

        public void setOrderProductList(Object orderProductList) {
            this.orderProductList = orderProductList;
        }

        public Object getOrderProduct() {
            return orderProduct;
        }

        public void setOrderProduct(Object orderProduct) {
            this.orderProduct = orderProduct;
        }

        public Object getUser() {
            return user;
        }

        public void setUser(Object user) {
            this.user = user;
        }

        public Object getOriginalOrderInfo() {
            return originalOrderInfo;
        }

        public void setOriginalOrderInfo(Object originalOrderInfo) {
            this.originalOrderInfo = originalOrderInfo;
        }

        public Object getOrderProductEntity() {
            return orderProductEntity;
        }

        public void setOrderProductEntity(Object orderProductEntity) {
            this.orderProductEntity = orderProductEntity;
        }

        public Object getIsAuthorization() {
            return isAuthorization;
        }

        public void setIsAuthorization(Object isAuthorization) {
            this.isAuthorization = isAuthorization;
        }
    }
}
