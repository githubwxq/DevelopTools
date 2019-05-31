package com.example.interviewdemo.aboutjava.equalandhascode;

class Student{
    public String name;
    public int age;
    public Student(){
        
    }
    public Student(String name,int age){
        this.name = name;
        this.age = age;
    }
    public void test(){
        System.out.println(this.name);
        System.out.println(this.age);
    }
}