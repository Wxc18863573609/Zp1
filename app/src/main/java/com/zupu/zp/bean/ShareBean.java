package com.zupu.zp.bean;

/**
 * description: dell
 * author: 王新昌
 * date: 2019/12/2 20:30
 * update: $date$
 */
public class ShareBean {


    /**
     * msg : success
     * shareCode : 24KciKE6SjOxpGiMxOKi
     * img : http://data-huayang.oss-cn-zhangjiakou.aliyuncs.com/hy/TJdGi65w7Z.jpg/share_img
     * code : 0
     * longUrl : https://zupu.honarise.com/zp/mobile/share_general_memorial.html?gzid=55&shareCode=24KciKE6SjOxpGiMxOKi
     * title : 祭祀馆分享
     * user : {"id":203,"patriarchId":null,"genealogyListId":null,"surnames":null,"realName":"13064802951","nickName":"13064802951","photoUrl":"https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/images/seat_occupying/applogo.png","gender":null,"phone":"13064802951","role":"4","leagueTermEndTime":null,"cardNumber":null,"birthday":null,"professional":null,"local":null,"individualitySignature":null,"balance":4.58,"integral":-171012,"payPassword":null,"loginPassword":"4f39dda60af7ea3d4ddc4b03ce83a77d0bc7c4a9a2cfdb49c8b66b9584f4cbc6","registrationTime":1573462280000,"isCertification":"4","certificationId":78,"failAuthenticationReason":null,"wechatLoginIdentity":null,"appLoginIdentity":"c099b4586cfa46348aa0c0df3dc2f113","rongToken":null,"lastLoginTime":1575341532000,"userStatus":"1","closureTime":null,"closureReason":null,"closureMan":null,"pushCid":"","loginSystemType":"2","isFirstLogin":"0","provinceCode":null,"cityCode":null,"districtCode":null,"anchorStatus":"1","school1":null,"school2":null,"school3":null,"school":null,"job":null,"extPara1":null,"extPara2":"1","extPara3":null,"extPara4":null,"extPara5":"1","extPara6":null,"extPara7":null,"extPara8":1,"extPara9":null,"extPara10":null,"extPara11":null,"extPara12":null,"extPara13":null,"extPara14":null,"genealogyListEntity":null,"roleName":null}
     * content : 禹祭祀馆
     */

    private String msg;
    private String shareCode;
    private String img;
    private int code;
    private String longUrl;
    private String title;
    private UserBean user;
    private String content;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getShareCode() {
        return shareCode;
    }

    public void setShareCode(String shareCode) {
        this.shareCode = shareCode;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static class UserBean {
        /**
         * id : 203
         * patriarchId : null
         * genealogyListId : null
         * surnames : null
         * realName : 13064802951
         * nickName : 13064802951
         * photoUrl : https://zupu-resource.oss-cn-beijing.aliyuncs.com/appresources/images/seat_occupying/applogo.png
         * gender : null
         * phone : 13064802951
         * role : 4
         * leagueTermEndTime : null
         * cardNumber : null
         * birthday : null
         * professional : null
         * local : null
         * individualitySignature : null
         * balance : 4.58
         * integral : -171012
         * payPassword : null
         * loginPassword : 4f39dda60af7ea3d4ddc4b03ce83a77d0bc7c4a9a2cfdb49c8b66b9584f4cbc6
         * registrationTime : 1573462280000
         * isCertification : 4
         * certificationId : 78
         * failAuthenticationReason : null
         * wechatLoginIdentity : null
         * appLoginIdentity : c099b4586cfa46348aa0c0df3dc2f113
         * rongToken : null
         * lastLoginTime : 1575341532000
         * userStatus : 1
         * closureTime : null
         * closureReason : null
         * closureMan : null
         * pushCid :
         * loginSystemType : 2
         * isFirstLogin : 0
         * provinceCode : null
         * cityCode : null
         * districtCode : null
         * anchorStatus : 1
         * school1 : null
         * school2 : null
         * school3 : null
         * school : null
         * job : null
         * extPara1 : null
         * extPara2 : 1
         * extPara3 : null
         * extPara4 : null
         * extPara5 : 1
         * extPara6 : null
         * extPara7 : null
         * extPara8 : 1
         * extPara9 : null
         * extPara10 : null
         * extPara11 : null
         * extPara12 : null
         * extPara13 : null
         * extPara14 : null
         * genealogyListEntity : null
         * roleName : null
         */

        private int id;
        private Object patriarchId;
        private Object genealogyListId;
        private Object surnames;
        private String realName;
        private String nickName;
        private String photoUrl;
        private Object gender;
        private String phone;
        private String role;
        private Object leagueTermEndTime;
        private Object cardNumber;
        private Object birthday;
        private Object professional;
        private Object local;
        private Object individualitySignature;
        private double balance;
        private int integral;
        private Object payPassword;
        private String loginPassword;
        private long registrationTime;
        private String isCertification;
        private int certificationId;
        private Object failAuthenticationReason;
        private Object wechatLoginIdentity;
        private String appLoginIdentity;
        private Object rongToken;
        private long lastLoginTime;
        private String userStatus;
        private Object closureTime;
        private Object closureReason;
        private Object closureMan;
        private String pushCid;
        private String loginSystemType;
        private String isFirstLogin;
        private Object provinceCode;
        private Object cityCode;
        private Object districtCode;
        private String anchorStatus;
        private Object school1;
        private Object school2;
        private Object school3;
        private Object school;
        private Object job;
        private Object extPara1;
        private String extPara2;
        private Object extPara3;
        private Object extPara4;
        private String extPara5;
        private Object extPara6;
        private Object extPara7;
        private int extPara8;
        private Object extPara9;
        private Object extPara10;
        private Object extPara11;
        private Object extPara12;
        private Object extPara13;
        private Object extPara14;
        private Object genealogyListEntity;
        private Object roleName;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getPatriarchId() {
            return patriarchId;
        }

        public void setPatriarchId(Object patriarchId) {
            this.patriarchId = patriarchId;
        }

        public Object getGenealogyListId() {
            return genealogyListId;
        }

        public void setGenealogyListId(Object genealogyListId) {
            this.genealogyListId = genealogyListId;
        }

        public Object getSurnames() {
            return surnames;
        }

        public void setSurnames(Object surnames) {
            this.surnames = surnames;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }

        public Object getGender() {
            return gender;
        }

        public void setGender(Object gender) {
            this.gender = gender;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public Object getLeagueTermEndTime() {
            return leagueTermEndTime;
        }

        public void setLeagueTermEndTime(Object leagueTermEndTime) {
            this.leagueTermEndTime = leagueTermEndTime;
        }

        public Object getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(Object cardNumber) {
            this.cardNumber = cardNumber;
        }

        public Object getBirthday() {
            return birthday;
        }

        public void setBirthday(Object birthday) {
            this.birthday = birthday;
        }

        public Object getProfessional() {
            return professional;
        }

        public void setProfessional(Object professional) {
            this.professional = professional;
        }

        public Object getLocal() {
            return local;
        }

        public void setLocal(Object local) {
            this.local = local;
        }

        public Object getIndividualitySignature() {
            return individualitySignature;
        }

        public void setIndividualitySignature(Object individualitySignature) {
            this.individualitySignature = individualitySignature;
        }

        public double getBalance() {
            return balance;
        }

        public void setBalance(double balance) {
            this.balance = balance;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public Object getPayPassword() {
            return payPassword;
        }

        public void setPayPassword(Object payPassword) {
            this.payPassword = payPassword;
        }

        public String getLoginPassword() {
            return loginPassword;
        }

        public void setLoginPassword(String loginPassword) {
            this.loginPassword = loginPassword;
        }

        public long getRegistrationTime() {
            return registrationTime;
        }

        public void setRegistrationTime(long registrationTime) {
            this.registrationTime = registrationTime;
        }

        public String getIsCertification() {
            return isCertification;
        }

        public void setIsCertification(String isCertification) {
            this.isCertification = isCertification;
        }

        public int getCertificationId() {
            return certificationId;
        }

        public void setCertificationId(int certificationId) {
            this.certificationId = certificationId;
        }

        public Object getFailAuthenticationReason() {
            return failAuthenticationReason;
        }

        public void setFailAuthenticationReason(Object failAuthenticationReason) {
            this.failAuthenticationReason = failAuthenticationReason;
        }

        public Object getWechatLoginIdentity() {
            return wechatLoginIdentity;
        }

        public void setWechatLoginIdentity(Object wechatLoginIdentity) {
            this.wechatLoginIdentity = wechatLoginIdentity;
        }

        public String getAppLoginIdentity() {
            return appLoginIdentity;
        }

        public void setAppLoginIdentity(String appLoginIdentity) {
            this.appLoginIdentity = appLoginIdentity;
        }

        public Object getRongToken() {
            return rongToken;
        }

        public void setRongToken(Object rongToken) {
            this.rongToken = rongToken;
        }

        public long getLastLoginTime() {
            return lastLoginTime;
        }

        public void setLastLoginTime(long lastLoginTime) {
            this.lastLoginTime = lastLoginTime;
        }

        public String getUserStatus() {
            return userStatus;
        }

        public void setUserStatus(String userStatus) {
            this.userStatus = userStatus;
        }

        public Object getClosureTime() {
            return closureTime;
        }

        public void setClosureTime(Object closureTime) {
            this.closureTime = closureTime;
        }

        public Object getClosureReason() {
            return closureReason;
        }

        public void setClosureReason(Object closureReason) {
            this.closureReason = closureReason;
        }

        public Object getClosureMan() {
            return closureMan;
        }

        public void setClosureMan(Object closureMan) {
            this.closureMan = closureMan;
        }

        public String getPushCid() {
            return pushCid;
        }

        public void setPushCid(String pushCid) {
            this.pushCid = pushCid;
        }

        public String getLoginSystemType() {
            return loginSystemType;
        }

        public void setLoginSystemType(String loginSystemType) {
            this.loginSystemType = loginSystemType;
        }

        public String getIsFirstLogin() {
            return isFirstLogin;
        }

        public void setIsFirstLogin(String isFirstLogin) {
            this.isFirstLogin = isFirstLogin;
        }

        public Object getProvinceCode() {
            return provinceCode;
        }

        public void setProvinceCode(Object provinceCode) {
            this.provinceCode = provinceCode;
        }

        public Object getCityCode() {
            return cityCode;
        }

        public void setCityCode(Object cityCode) {
            this.cityCode = cityCode;
        }

        public Object getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(Object districtCode) {
            this.districtCode = districtCode;
        }

        public String getAnchorStatus() {
            return anchorStatus;
        }

        public void setAnchorStatus(String anchorStatus) {
            this.anchorStatus = anchorStatus;
        }

        public Object getSchool1() {
            return school1;
        }

        public void setSchool1(Object school1) {
            this.school1 = school1;
        }

        public Object getSchool2() {
            return school2;
        }

        public void setSchool2(Object school2) {
            this.school2 = school2;
        }

        public Object getSchool3() {
            return school3;
        }

        public void setSchool3(Object school3) {
            this.school3 = school3;
        }

        public Object getSchool() {
            return school;
        }

        public void setSchool(Object school) {
            this.school = school;
        }

        public Object getJob() {
            return job;
        }

        public void setJob(Object job) {
            this.job = job;
        }

        public Object getExtPara1() {
            return extPara1;
        }

        public void setExtPara1(Object extPara1) {
            this.extPara1 = extPara1;
        }

        public String getExtPara2() {
            return extPara2;
        }

        public void setExtPara2(String extPara2) {
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

        public String getExtPara5() {
            return extPara5;
        }

        public void setExtPara5(String extPara5) {
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

        public int getExtPara8() {
            return extPara8;
        }

        public void setExtPara8(int extPara8) {
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

        public Object getExtPara11() {
            return extPara11;
        }

        public void setExtPara11(Object extPara11) {
            this.extPara11 = extPara11;
        }

        public Object getExtPara12() {
            return extPara12;
        }

        public void setExtPara12(Object extPara12) {
            this.extPara12 = extPara12;
        }

        public Object getExtPara13() {
            return extPara13;
        }

        public void setExtPara13(Object extPara13) {
            this.extPara13 = extPara13;
        }

        public Object getExtPara14() {
            return extPara14;
        }

        public void setExtPara14(Object extPara14) {
            this.extPara14 = extPara14;
        }

        public Object getGenealogyListEntity() {
            return genealogyListEntity;
        }

        public void setGenealogyListEntity(Object genealogyListEntity) {
            this.genealogyListEntity = genealogyListEntity;
        }

        public Object getRoleName() {
            return roleName;
        }

        public void setRoleName(Object roleName) {
            this.roleName = roleName;
        }
    }
}
