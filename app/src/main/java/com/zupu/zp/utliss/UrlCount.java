package com.zupu.zp.utliss;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/7/30 15:21
 * update: 接口
 */
public class UrlCount {
    // https://zupu.honarise.com/mobile_LiveBroadcast/queryAuthInfo?roomNum=1
    // public static final String Base_Head = "http://192.168.0.119:8080/zupu/";
    //  public static final String Base_Head = "http://192.168.0.106:8080/zupu/";
//http://192.168.0.114:8080/zupu
    //http://192.168.0.121:8080/zupu/
   //  public static final String Base_Head = "https://zupu.honarise.com/";
    // public static final String Base_Head = "http://192.168.0.102:8080/zp/";
    //http://192.168.0.118:8080/zp/
    // public static final String Base_Head = "http://192.168.0.17:8080/zupu/";
    //  public static final String Base_Head = "http://192.168.0.108:8080/zp2.0/";
    //192.168.0.107
    //public static final String Base_Head = "http://192.168.0.12:8080/zp2.0/";//历史懂
  //  public static final String Base_Head = "http://192.168.0.3:8080/zupu/";//历史懂
    public static final String Base_Head = "http://192.168.0.22:8080/zp2.0/";//历史懂
    //回放地址
    public static final String Base_Huifang = "mobile_LiveBroadcast/queryAuthInfo";
    //送礼物
    public static final String Base_SendGift = "mobile_LiveBroadcast/sendGift";
    //pk送礼物
    public static final String Base_SendGiftpk = "mobile_LiveBroadcast/sendGiftAndroid";
    //pk查礼物根据房间号查询谁送de
    public static final String Base_Querygiftpk = "mobile_LiveBroadcast/selectgiftAndroid";
    //根据房间号查询谁送的礼物
    public static final String Base_Querygift = "mobile_LiveBroadcast/selectgift";
    //查询礼物集合
    ///mobile_LiveBroadcast/getCurrentUser
    public static final String Base_Giftlist = "mobile_LiveBroadcast/queryGift";
    //https://zupu.honarise.com/mobile_LiveBroadcast/queryGift
    ///mobile_LiveBroadcast/querryRoom
    //房间列表
    public static final String Base_Live = "mobile_LiveBroadcast/queryRoom";
    //存入fangjian
    public static final String Base_saveuser = "mobile_LiveBroadcast/saveUser";
    //查询用户数
    public static final String Base_getCurrentUser = "mobile_LiveBroadcast/getCurrentUser";
    //移除用户
    public static final String Base_RemoveUser = "mobile_LiveBroadcast/delUser";
    ///mobile_LiveBroadcast/createLiveRoom
    //创建房间
    public static final String Base_CreatRoom = "mobile_LiveBroadcast/createLiveRoom";
    ///mobile_user/login
    //登录
    public static final String Base_Login = "mobile_user/login";
    ///mobile_user/queryByOpenId"
    //微信登录
    public static final String URL_WXDl = "mobile_user/queryByOpenId";
    ///mobile_zuce/sendzhaohuiecode
    //发送注册过的找回密码短信
    public static final String URL_Dx = "mobile_zuce/sendzhaohuiecode";
    //验证短信是否正确
    ///mobile_zuce/checkCode
    public static final String URL_DtLoging = "mobile_zuce/checkCode";
    //发送没注册过的短信
    ///mobile_zuce/sendbangdingcode
    public static final String URL_Dx1 = "mobile_zuce/sendbangdingcode";
    //修改密码
    ///mobile_zuce/savePssword
    public static final String URL_Gpwd = "mobile_zuce/savePssword";
    //用户注册接口
    ///mobile_zuce/saveUserInfo
    public static final String URL_Rigist = "mobile_zuce/saveUserInfo";
    //动态登录
    ///mobile_zuce/dongtaiLoad
    public static final String URL_DLoging = "mobile_zuce/dongtaiLoad";
    // /mobile_LiveBroadcast/queryAuthInfo
    //查询主播信息
    public static final String URL_Query_Live_msg = "mobile_LiveBroadcast/queryAuthInfo";
    // /mobile_LiveBroadcast/queryMyFol
    //查询是否关注主播
    public static final String URL_Query_isAttention = "mobile_LiveBroadcast/queryMyFol";
    //mobile_LiveBroadcast/dealGuanzhu
    public static final String URL_Guanzhu = "mobile_LiveBroadcast/dealGuanzhu";
    //用户微信信息
    ///mobile_user/saveUser"
    public static final String URL_SaveUser = "mobile_user/saveUser";

    //个推推送消息
    public static final String URL_GtSend = "message/push";
    //撤销推送接口   /message/canclePush
    public static final String URL_GtCx = "message/canclePush";
    //提交副主播userid
    //mobile_LiveBroadcast/getAnchorUserId
    public static final String URL_GetAnchorUserId = "mobile_LiveBroadcast/getAnchorUserId";
    //http://192.168.0.122:8080/zupu/mobile_LiveBroadcast//getAnchorUserIdTwo
    //查询pk房间主播信息的
    public static final String URL_QueryAnchorUserIdTwo = "mobile_LiveBroadcast/queryRoomAndAnthAndTwouser";
    //直播封面搜索商品
    // http://192.168.0.122:8080/zupu/mobile_LiveBroadcast/queryProduct
    public static final String URL_Search = "mobile_LiveBroadcast/queryProduct";
    //创建房间时提交当前经纬度的接口
    //http://localhost:8080/zp2.0/gaoDeAddress/getAddressBylogandlat?log=116.397499&lat=39.908722
    public static final String URL_WE = "gaoDeAddress/getAddressBylogandlat";
    //mobile_PayOrder/payOrder
    //支付接口
    public static final String URL_ZF = "mobile_PayOrder/payOrder";
    //mobile_RealNameAuthentication/queryAuthenticationDetails
    //查询用户是否有实名认证
    //public static final String URL_NameRZ="mobile_RealNameAuthentication/queryAuthenticationDetails";
    //保存分享信息
    //mobile_ShareRecord/saveShareRecordss
    public static final String URL_Share = "mobile_ShareRecord/saveShareRecordss";
    //图片上传
    ///sys_joininves/addOssUrlImg
    public static final String URL_UpPic = "sys_joininves/filesUpload";
    //mobile_lateonepavilion/addlateonedataxixni
    public static final String URL_guan = "mobile_lateonepavilion/addlateonedataxixni";
    //多图
    public static final String URL_morePic = "fileOssUpdate/chuliFileOssUpdateMEssage";
    ///listeningTheOnline/getListeningTheOnline
    //告诉服务器当前用户是否在线
    public static final String URL_onLine = "listeningTheOnline/getListeningTheOnline";
    //查询当前状态余额等
    //mobile_user/selectByUserId
    public static final String URL_Zt = "mobile_user/selectByUserId";
    //通话状态
    //mobile_teacher/xuigaiTeacherEvaluatexinxiState
    public static final String URL_CllZt = "mobile_user/selectByUserId";
    //mobile_teacher/xiugaiTeacherEvaluatezaixianStateByid
    //老师状态
    public static final String URL_techerzt = "mobile_teacher/xiugaiTeacherEvaluatezaixianStateByid";
    //mobile_teacher/xiugaiTeacherEvaluatonghuaTime
    //是否通话中
    public static final String URL_callzhong = "mobile_teacher/xiugaiTeacherEvaluatonghuaTime";
    ///mobile_OrderInfo/saveOrderInfoAndOrderProduct
    //支付接口1
    public static final String URL_zf1 = "syllabusTeacher/xuFeipayTeacherShoujiao";
    ///mobile_PayOrder/payOrder"
    //支付接口2
    public static final String URL_zf2 = "mobile_PayOrder/payOrder";
    //mobile_LiveBroadcast/queryLiveProduct
    public static final String URL_Queryproduct = "mobile_LiveBroadcast/queryLiveProduct";
    //mobile_OrderInfo/getOrderidPaySuccess  //查询订单是否成功
    public static final String URL_Ddzt = "mobile_OrderInfo/getOrderidPaySuccess";
    ///mobile_OrderInfo/saveOrderInfoAndOrderProduct
    public static final String URL_Zbzf = "mobile_OrderInfo/saveOrderInfoAndOrderProduct";
    ///mobile_PayOrder/payOrder"
    //mobile_OrderInfo/saveOrderInfoAndOrderProduct
    //mobile_configuration/insertIntegralTopup
    //充值第一步
    public static final String URL_Zbzf1 = "mobile_configuration/insertIntegralTopup";
    //http://192.168.0.118:8080/zp/mobile/get_banben
    public static final String URL_updata = "mobile/get_banben";
    //applePay/doPlay
    public static final String URL_ZbFf = "applePay/doPlay";
    //applePay/doConsumption
    public static final String URL_LviewZF = "applePay/doConsumption";
    //族谱数据
    //zuPuFamily/queryInfo
    public static final String URL_ZpData = "zuPuFamily/queryInfotwo";
    public static final String URL_SevePic = "zuPuFamily/updateFamilyPicTwo";
    //保存族谱图片
    //zuPuFamily/saveFamilyPic
    // public static final String URL_SevePic = "zuPuFamily/saveFamilyPic";
    //封面信息保存/zuPuFamily/setFamilyBook
    ///zuPuFamily/setFamilyBook
    public static final String URL_SeveFm = "zuPuFamily/setFamilyBook";
    //导出
    ///zuPuFamily/getPdfPath
    public static final String URL_outZp = "zuPuFamily/getPdfPath";
    //mobile_GenealogyList/getPdfPath
    public static final String URL_outZp2 = "mobile_GenealogyList/getPdfPath";
}
