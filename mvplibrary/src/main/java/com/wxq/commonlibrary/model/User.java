package com.wxq.commonlibrary.model;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.io.Serializable;

import org.greenrobot.greendao.annotation.Generated;

/**
 * @author Army
 * @version V_5.0.0
 * @date 2016年02月21日
 * @description 用户实体类
 */
@Entity
public class User implements Serializable {
    @Transient
    private static final long serialVersionUID = -8901488872936236870L;
    @Id(autoincrement = true)
    private Long id;
    public String classBlocked = "false";
    public String userId = "";
    public String accessToken = "";
    public String time = "";
    public String userName = "";
    public String phoneNumber = "";
    public String userImageUrl = "";
    public String provinceName = "";
    public String provinceId = "";
    public String cityName = "";
    public String cityId = "";
    public String districtName = "";
    public String districtId = "";
    public String registered = "";
    public String xxCode = "";
    public String role = "";
    public String studentName = "";
    public Integer type = 0;
    /**
     * 添加好友时使用
     */
    public String addGlag = "";
    public String pinyin = "";
    /**
     * 在群聊中是否禁言，禁言为-1，非禁言为1
     */
    public Integer isChat;
    /**
     * 在禁言功能中的选择联系人页面要用
     */
    public Boolean ischecked = false;

    /**
     * 这是用来区分存在数据里的人的类别, -1是好友
     */
    public Integer flag = 0;

    /**
     * 存这条数据的人
     */
    public String storeUid = "";

    @Generated(hash = 298379899)
    public User(Long id, String classBlocked, String userId, String accessToken,
            String time, String userName, String phoneNumber, String userImageUrl,
            String provinceName, String provinceId, String cityName, String cityId,
            String districtName, String districtId, String registered,
            String xxCode, String role, String studentName, Integer type,
            String addGlag, String pinyin, Integer isChat, Boolean ischecked,
            Integer flag, String storeUid) {
        this.id = id;
        this.classBlocked = classBlocked;
        this.userId = userId;
        this.accessToken = accessToken;
        this.time = time;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.userImageUrl = userImageUrl;
        this.provinceName = provinceName;
        this.provinceId = provinceId;
        this.cityName = cityName;
        this.cityId = cityId;
        this.districtName = districtName;
        this.districtId = districtId;
        this.registered = registered;
        this.xxCode = xxCode;
        this.role = role;
        this.studentName = studentName;
        this.type = type;
        this.addGlag = addGlag;
        this.pinyin = pinyin;
        this.isChat = isChat;
        this.ischecked = ischecked;
        this.flag = flag;
        this.storeUid = storeUid;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassBlocked() {
        return this.classBlocked;
    }

    public void setClassBlocked(String classBlocked) {
        this.classBlocked = classBlocked;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserImageUrl() {
        return this.userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public String getProvinceName() {
        return this.provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return this.cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictName() {
        return this.districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getDistrictId() {
        return this.districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getRegistered() {
        return this.registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getXxCode() {
        return this.xxCode;
    }

    public void setXxCode(String xxCode) {
        this.xxCode = xxCode;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStudentName() {
        return this.studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAddGlag() {
        return this.addGlag;
    }

    public void setAddGlag(String addGlag) {
        this.addGlag = addGlag;
    }

    public String getPinyin() {
        return this.pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public Integer getIsChat() {
        return this.isChat;
    }

    public void setIsChat(Integer isChat) {
        this.isChat = isChat;
    }

    public Boolean getIschecked() {
        return this.ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }

    public Integer getFlag() {
        return this.flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getStoreUid() {
        return this.storeUid;
    }

    public void setStoreUid(String storeUid) {
        this.storeUid = storeUid;
    }


}