package com.example.interviewdemo.producerandconsumer;

public class PrintEven implements Runnable {
    Num num;

    public PrintEven(Num num) {
        this.num = num;
    }

    @Override
    public void run() {
        while (num.i <= 100) {
            synchronized (num) {
                // 必须要用同一把锁对象，这个对象是num
                if (!num.flag) {
                    try {
                        num.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("偶数" + num.i);
                    num.i++;
                    num.flag = false;//下次进来需要等待被唤起
                    num.notify();
                }
            }
        }
    }
}
