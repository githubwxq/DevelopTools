package com.example.interviewdemo.reflect;

public class UserEntity {
    private String userName;
    private int userAge;
    
    private final int money = 10000;
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public int getUserAge() {
        return userAge;
    }
    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }
    //借钱方法
    public int getMoney(){
        System.out.println("你借了 " + money + "元！");
        return money;
    }
    //还钱方法，单个参数
    private void repay(int money){
        System.out.println("你还了 " + money + "元！");
    }
    //还钱方法，多个参数
    public void repay(String userName,int money){
        System.out.println(userName+ " 还了 " + money + "元！");
    }
}