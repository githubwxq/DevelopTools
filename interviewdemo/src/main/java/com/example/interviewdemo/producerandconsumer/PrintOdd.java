package com.example.interviewdemo.producerandconsumer;

//https://www.cnblogs.com/luozhijun/p/6895455.html
public class PrintOdd implements Runnable {

    public PrintOdd(Num num) {
        this.num = num;
    }

    Num num;


    @Override
    public void run() {
        while (num.i <= 100) {
            synchronized (num) {
                if (num.flag) {
                    try {
                        num.wait();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                } else {
                    System.out.println("奇数----" + num.i);
                    num.i++;
                    num.flag=true;
                    num.notify();
                }
            }
        }
    }
}
