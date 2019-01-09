package com.wxq.commonlibrary.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/10/27
 * @description 公共的跟用户信息无关的数据存在这里
 */
public class PublicPreference {
    private SharedPreferences settings;

    public PublicPreference(Context ctx) {
        if (ctx != null && settings == null) {
            this.settings = ctx.getSharedPreferences("exue_public_data", 0);
        }
    }

    public void storeUid(String uid) {
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("uid", uid);
        edit.commit();
    }

    public String getUid() {
        return settings.getString("uid", "");
    }

    public void storeToken(String uid) {
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("token", uid);
        edit.commit();
    }

    public String getToken() {
        return settings.getString("token", "");
    }

    /*****************************存数据时加stu和tea前缀**********************************/



/*    *//**
     * @param autologin 0 不自动  1 自动
     *//*
    public void storeAutoLogin(int autologin) {
        SharedPreferences.Editor edit = settings.edit();
        edit.putInt("auto_login", autologin);
        edit.commit();
    }

    public int getAutoLogin() {
        return settings.getInt("auto_login", 0);
    }

    public void storeIsInsertProvinceInfo(boolean isInsert) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isInsertProvinceInfo", isInsert);
        editor.commit();
    }

    public boolean getIsInsertProvinceInfo() {
        return settings.getBoolean("isInsertProvinceInfo", false);
    }



    public int getProvinceVersion() {
        return settings.getInt("ProvinceVersion", 0);
    }

    *//**
     * 设置当前省市区版本号 默认重0开始
     * @return
     *//*
    public void storeProvinceInfo(int version) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("ProvinceVersion", version);
        editor.commit();
    }





    public void storeIsFirstUse(boolean isFirstUse) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isFirstUse", isFirstUse);
        editor.commit();
    }

    public boolean getIsFirstUse() {
        return settings.getBoolean("isFirstUse", false);
    }

    *//**
     * @param loginType 0：用户名密码登录  1：验证码登录  2：第三方登录
     *//*
    public void storeLoginType(int loginType) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("loginType", loginType);
        editor.commit();
    }

    public int getLoginType() {
        return settings.getInt("loginType", 1);
    }

    public void storeThirdAccountUserName(String name) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("thirdAccountUserName", name);
        editor.commit();
    }

    public String getThirdAccountUserName() {
        return settings.getString("thirdAccountUserName", "");
    }

    public void storeThirdAccountUserHead(String head) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("thirdAccountUserHead", head);
        editor.commit();
    }

    public String getThirdAccountUserHead() {
        return settings.getString("thirdAccountUserHead", "");
    }

    public void storeThirdAccountUserId(String id) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("thirdAccountUserId", id);
        editor.commit();
    }

    public String getThirdAccountUserId() {
        return settings.getString("thirdAccountUserId", "");
    }

    public void storeThirdAccountType(String type) {
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("thirdAccountUserType", type);
        editor.commit();
    }

    public String getThirdAccountUserType() {
        return settings.getString("thirdAccountUserType", "");
    }*/
}
