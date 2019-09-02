package com.wxq.commonlibrary.datacenter;

public enum LoginStatue {
    COMMONLOGIN("普通登录","0"),  AUTOLOGION("自动登录","1"), ERRORLOGIN("出错登录","2"), EXITLOGIN("退出登录","3");
    // 成员变量
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;
    LoginStatue(String name, String code) {
        this.name = name;
        this.code = code;
    }

    // 普通方法
    public static String getNameBuyCode(String code) {
        for (LoginStatue c : LoginStatue.values()) {
            if (c.getCode().equals(code) ) {
                return c.name;
            }
        }
        return null;
    }
}
