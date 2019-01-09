package com.wxq.commonlibrary.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.Map;
import java.util.Set;


/**
 * @author wxq
 * @version V_5.0.0
 * @date 2018年2月21日
 * @description 存储用户信息
 */
public class UserPreference {
    private SharedPreferences settings;
    PublicPreference publicPreference;
    public String getCertificateType() {
        checkNull();
        return settings.getString("CertificateType", "");
    }

    public void storeCertificateType(String sCertificateType) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("CertificateType", sCertificateType);
        edit.commit();
    }

    public String getCertificateNumber() {
        checkNull();
        return settings.getString("CertificateNumber", "");
    }

    public void storeCertificateNumber(String sCertificateNumber) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("CertificateNumber", sCertificateNumber);
        edit.commit();
    }

    public String getBirth() {
        checkNull();
        return settings.getString("Birth", "");
    }

    public void storeBirth(String sBirth) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("Birth", sBirth);
        edit.commit();
    }

    public String getMaritalStatus() {
        checkNull();
        return settings.getString("MaritalStatus", "");
    }

    public void storeMaritalStatus(String sMaritalStatus) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("MaritalStatus", sMaritalStatus);
        edit.commit();
    }

    public String getProvinceName() {
        checkNull();
        return settings.getString("ProvinceName", "");
    }

    public void storeProvinceName(String sProvinceName) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("ProvinceName", sProvinceName);
        edit.commit();
    }

    public String getProvinceId() {
        checkNull();
        return settings.getString("ProvinceId", "");
    }

    public void storeProvinceId(String sProvinceId) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("ProvinceId", sProvinceId);
        edit.commit();
    }

    public String getCityName() {
        checkNull();
        return settings.getString("CityName", "");
    }

    public void storeCityName(String sCityName) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("CityName", sCityName);
        edit.commit();
    }

    public String getCityId() {
        checkNull();
        return settings.getString("CityId", "");
    }

    public void storeCityId(String sCityId) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("CityId", sCityId);
        edit.commit();
    }

    public String getAreaName() {
        checkNull();
        return settings.getString("AreaName", "");
    }

    public void storeAreaName(String sAreaName) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("AreaName", sAreaName);
        edit.commit();
    }

    public String getAreaId() {
        checkNull();
        return settings.getString("AreaId", "");
    }

    public void storeAreaId(String sAreaId) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("AreaId", sAreaId);
        edit.commit();
    }

    public String getQrCode() {
        checkNull();
        return settings.getString("QrCode", "");
    }

    public void storeQrCode(String sQrCode) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("QrCode", sQrCode);
        edit.commit();
    }

    public String getLoginTime() {
        checkNull();
        return settings.getString("loginTime", "");
    }

    public void storeLoginTime(String loginTime) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("loginTime", loginTime);
        edit.commit();
    }

    public String getSex() {
        checkNull();
        return settings.getString("sex", "");
    }

    public void storeSex(String ssex) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("sex", ssex);
        edit.commit();
    }

    public UserPreference(PublicPreference publicPreference) {
        this.publicPreference=publicPreference;
    }

    /**
     * 切换到相应用户的文件里去
     */
    public void setUserid(Context ctx, String uid) {
        if (TextUtils.isEmpty(uid)) {
            uid = "error_userinfo";
        }
        if (ctx != null) {
            settings = ctx.getSharedPreferences(uid, 0);
        }
    }

    public void storePhoneNo(String phoneNo) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("phone_number", phoneNo);
        edit.commit();
    }

    public String getPhoneNo() {
        checkNull();
        return settings.getString("phone_number", "");
    }

    public void storeExueNo(String exueNo) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("exue_number", exueNo);
        edit.commit();
    }

    public String getExueNo() {
        checkNull();
        return settings.getString("exue_number", "");
    }

    public void storePassword(String password) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("password", password);
        edit.commit();
    }

    public String getPassword() {
        checkNull();
        return settings.getString("password", "");
    }

    public void storeUserName(String userName) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("fullName", userName);
        edit.commit();
    }

    public String getUserName() {
        checkNull();
        return settings.getString("fullName", "");
    }

    public void storeFriendUids(String frienduids) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("frienduids", frienduids);
        edit.commit();
    }

    public String getFriendUids() {
        checkNull();
        return settings.getString("frienduids", "");
    }

    public void storeAvatar(String userImageUrl) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("userImageUrl", userImageUrl);
        edit.commit();
    }

    public String getAvatar() {
        checkNull();
        return settings.getString("userImageUrl", "");
    }

    public void storeAudio(String isin) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("audio", isin);
        edit.commit();
    }

    /**
     * 静音模式，值为0
     * 震动模式，值为1
     * 响铃模式，值为2
     * 响铃震动，值为3
     */
    public String getAudio() {
        checkNull();
        return settings.getString("audio", "2");
    }

    public void storeClassShowName(String clazzId, boolean flag) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putBoolean(clazzId, flag);
        edit.commit();
    }

    public boolean getClassShowName(String clazzId) {
        checkNull();
        return settings.getBoolean(clazzId, true);
    }

    /**
     * 聊天需要的签名
     */
    public void storeSign(String sign) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("sign", sign);
        edit.commit();
    }

    public String getSign() {
        checkNull();
        return settings.getString("sign", "");
    }

    /**
     * 聊天需要的id
     */
    public void storeImId(String sign) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("imId", sign);
        edit.commit();
    }

    public String getImId() {
        checkNull();
        return settings.getString("imId", "");
    }

    public void storeCurrentSchoolId(String schoolId) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("currentSchoolId", schoolId);
        edit.commit();
    }

    public String getCurrentSchoolId() {
        checkNull();
        return settings.getString("currentSchoolId", "");
    }

    public void storeCurrentSchoolName(String schoolName) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("currentSchoolName", schoolName);
        edit.commit();
    }

    public String getCurrentSchoolName() {
        checkNull();
        return settings.getString("currentSchoolName", "");
    }

    public void storeCurrentSchoolType(String schoolType) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("currentSchoolType", schoolType);
        edit.commit();
    }

    public String getCurrentSchoolType() {
        checkNull();
        return settings.getString("currentSchoolType", "0");
    }

    public void storeCurrentClasName(String schoolName) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("currentClassName", schoolName);
        edit.commit();
    }

    public String getCurrentClassName() {
        checkNull();
        return settings.getString("currentClassName", "");
    }

    public void storeCurrentStudentId(String schoolId) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("currentStudentId", schoolId);
        edit.commit();
    }

    public String getCurrentStudentId() {
        checkNull();
        return settings.getString("currentStudentId", "");
    }

    public void storeCurrentStudentName(String schoolName) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("currentStudentName", schoolName);
        edit.commit();
    }

    public String getCurrentStudentName() {
        checkNull();
        return settings.getString("currentStudentName", "");
    }

    public void storePayPassword(String password) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("payPassword", password);
        edit.commit();
    }

    public String getPayPassword() {
        checkNull();
        return settings.getString("payPassword", "");
    }

    public void storeSanJieHe(String sanJieHeUrl) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("sanJieHe", sanJieHeUrl);
        edit.commit();
    }

    public String getSanJieHe() {
        checkNull();
        return settings.getString("sanJieHe", "");
    }

    /**
     * @param number 直播已使用次数
     */
    public void storeHasBroadCastNumber(String number) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("usenumber", number);
        edit.commit();
    }

    public String getHasBroadCastNumber() {
        checkNull();
        return settings.getString("usenumber", "0");
    }


    /**
     * @param number 会员使用次数
     */
    public void storeTryVipNumber(String number) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("usenumber", number);
        edit.commit();
    }

    public String getTryVipNumber() {
        checkNull();
        return settings.getString("usenumber", "3");
    }

    /**
     * 用户身份，登录的时候用到，见GlobalContent里的USERTYPE，家庭端会用到
     */
    public void storeUserType(String type) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("userType", type);
        edit.commit();
    }

    public String getUserType() {
        checkNull();
        return settings.getString("userType", "1");
    }

    public void storeSystemMsgTime(String time) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("systemMsgTime", time);
        edit.commit();
    }

    public String getSystemMsgTime() {
        checkNull();
        return settings.getString("systemMsgTime", "");
    }

    public void storeInteractiveMsgTime(String time) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("interactiveMsgTime", time);
        edit.commit();
    }

    public String getInteractiveMsgTime() {
        checkNull();
        return settings.getString("interactiveMsgTime", "");
    }

    public void storeNoticeTime(String time) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("noticeTime", time);
        edit.commit();
    }

    public String getNoticeTime() {
        checkNull();
        return settings.getString("noticeTime", System.currentTimeMillis() + "");
    }

    public void storeETopicTime(String time) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("eTopicTime", time);
        edit.commit();
    }

    public String getETopicTime() {
        checkNull();
        return settings.getString("eTopicTime", System.currentTimeMillis() + "");
    }

    public void storeLoginAccount(String account) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("loginAccount", account);
        edit.commit();
    }

    public String getLoginAccount() {
        checkNull();
        return settings.getString("loginAccount", "");
    }

    public void storeIsHasPassword(boolean isHasPassword) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putBoolean("isHasPassword", isHasPassword);
        edit.commit();
    }

    public boolean getIsHasPassword() {
        checkNull();
        return settings.getBoolean("isHasPassword", false);
    }


    public int getIsVip() {
        checkNull();
        return settings.getInt("isVip", 0);
    }

    /**
     * 设置是否是会员
     *
     * @param flag 0：不是 1：开通未过期 2 过期
     */
    public void setIsVip(int flag) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putInt("isVip", flag);
        edit.commit();
    }

    /**
     * 得到过期时间过期时间
     *
     * @param
     */
    public String getVipDate() {
        checkNull();
        return settings.getString("vipDate", "");
    }

    /**
     * 设置会员过期时间
     *
     * @param date 过期时间
     */
    public void setVipDate(String date) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("vipDate", date);
        edit.commit();
    }


    /**
     * @param vipNum 设置会员number
     */
    public void setVipNum(String vipNum) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("vipNum", vipNum);
        edit.commit();
    }


    /**
     * 获取vip号码
     *
     * @param
     */
    public String getVipNum() {
        checkNull();
        return settings.getString("vipNum", "");
    }


    /**
     * 设置是否第一进入作业界面
     */
    public void setForTheFirstTask(boolean b) {
        checkNull();
        SharedPreferences.Editor edit = settings.edit();
        edit.putBoolean("isForTheFirstTask", b);
        edit.commit();
    }


    /**
     * 获取是否第一进入作业界面
     *
     * @return
     */
    public boolean isForTheFirstTask() {
        checkNull();
        return settings.getBoolean("isForTheFirstTask", true);
    }


    private void checkNull() {
//        if (settings == null) {
//            if (Global.application == null) {
//                Activity topActivity = AppManager.getInstance().getTopActivity();
//                if (topActivity != null) {
//                    setUserid(topActivity.getApplication(), publicPreference.getUid());
//                } else {
//                    AppManager.getInstance().killAllActivity();
//                    Process.killProcess(Process.myPid());
//                    return;
//                }
//            } else {
//                setUserid(Global.application, publicPreference.getUid());
//            }
//        }
    }

    /**
     * 获取所有存储数据的方法（这个方法放在类的最下面）
     *
     * @return
     */
    public String getAllData() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n----------------------------------UserPreference---------------------------------------\n");
        Set<? extends Map.Entry<String, ?>> entries = settings.getAll().entrySet();
        for (Map.Entry<String, ?> entry : entries) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
