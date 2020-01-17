package com.zupu.zp.bean;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/7 14:23
 * update: $date$
 */
public class GiftJsonBean {
    /**
     * userIcon : http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epEEqpwBRHOxwuuQEiaHNUJWzoveVoxicUE891sPoXgOlsnIOPOa0lvf5iaEH52ZvmPu3GV6OU3YoSZw/132
     * userName : wu
     * giftName : 麻花
     * giftImage : http://zupu-resource.oss-cn-beijing.aliyuncs.com/create_gift/picture/3772bc4c411544d4aac4c6f724d8004c.png
     * giftGifImage : 麻花
     * defaultCount : 0
     * sendCount : 66
     * giftId : 6
     */

    private String userIcon;
    private String userName;
    private String giftName;
    private String giftImage;
    private String giftGifImage;
    private String giftKey;
    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getGiftKey() {
        return giftKey;
    }

    public void setGiftKey(String giftKey) {
        this.giftKey = giftKey;
    }

    public String getDefaultCount() {
        return defaultCount;
    }

    public void setDefaultCount(String defaultCount) {
        this.defaultCount = defaultCount;
    }

    private String defaultCount;
    private String sendCount;
    private int giftId;

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftImage() {
        return giftImage;
    }

    public void setGiftImage(String giftImage) {
        this.giftImage = giftImage;
    }

    public String getGiftGifImage() {
        return giftGifImage;
    }

    public void setGiftGifImage(String giftGifImage) {
        this.giftGifImage = giftGifImage;
    }



    public String getSendCount() {
        return sendCount;
    }

    public void setSendCount(String sendCount) {
        this.sendCount = sendCount;
    }

    public int getGiftId() {
        return giftId;
    }

    public void setGiftId(int giftId) {
        this.giftId = giftId;
    }
}
