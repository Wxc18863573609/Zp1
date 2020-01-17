package com.zupu.zp.entity;

/**
 * Created by zego on 2018/11/15.
 */

public class GetAppIdConfig {

    /**
     * 请提前在即构管理控制台获取 appID 与 appSign
     *  AppID 填写样式示例：
            public static final long appId = 123456789L;
     *  appSign 填写样式示例：
            public static final byte[] appSign = {
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
            }
     *
     */

    public static final long appId = 3030559298L;
    public static final byte[] appSign =new byte[]
            {       (byte)0x3e,(byte)0x27,(byte)0xe0,(byte)0x97,(byte)0xba,(byte)0x50,
                    (byte)0xc1,(byte)0xd0, (byte)0xde,(byte)0x46,(byte)0xe8, (byte)0x44,
                    (byte)0x73,(byte)0xd2,(byte)0x8f,(byte)0x9d,(byte)0x14,
                    (byte)0x35, (byte)0x14,(byte)0x0f,(byte)0xc9,(byte)0xe8,
                    (byte)0xcc,(byte)0xf8,(byte)0x4b,(byte)0xbc, (byte)0xb8,
                    (byte)0x5d,(byte)0x15,(byte)0xa2,(byte)0xec,(byte)0xc1 };
}