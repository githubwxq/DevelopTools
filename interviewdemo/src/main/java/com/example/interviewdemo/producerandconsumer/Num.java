package com.example.interviewdemo.producerandconsumer;

public class Num {
    int i = 1;
    // 两个线程看， 交替执行的一个标志
    boolean flag = false;


    public static void main(String[] args) {
        Num num = new Num();

        PrintOdd printOdd = new PrintOdd(num);
        PrintEven printEven = new PrintEven(num);

        Thread thread1 = new Thread(printOdd);
        Thread thread2 = new Thread(printEven);

        thread1.start();
        thread2.start();

    }

}
