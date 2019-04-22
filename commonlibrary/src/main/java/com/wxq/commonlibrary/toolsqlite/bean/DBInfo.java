package com.wxq.commonlibrary.toolsqlite.bean;

/**
 * 用户名和数据库名
 */
public class DBInfo {
    //用户名
    private String userName;
    //数据库名
    private String dbName;

    public DBInfo() {
    }

    public DBInfo(String userName, String dbName) {
        this.userName = userName;
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
        String s;
    }

    @Override
    public String toString() {
        return "DBInfo{" +
                "userName='" + userName + '\'' +
                ", dbName='" + dbName + '\'' +
                '}';
    }

    /**
     * 判断值是否相等
     *
     * @param info
     * @return
     */
    public boolean equals(DBInfo info) {
        if (info == null || !this.toString().equals(info.toString())) {
            return false;
        }
        return true;
    }
}
