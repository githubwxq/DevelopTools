package com.example.interviewdemo.producerandconsumer;

public class TestClient {

    public static void main(String[] args){
//        Resource resource=new Resource();
//
//        ProducerThread producerThread=new ProducerThread(resource,"producerThread1");
//        ProducerThread producerThread2=new ProducerThread(resource,"producerThread2");
//        ConsumerThread consumerThread = new ConsumerThread(resource,"consumerThread");
//
//        producerThread.start();
//        producerThread2.start();
//        consumerThread.start();

        Resource2 resource=new Resource2();

        ProducerThread2 producerThread=new ProducerThread2(resource,"producerThread1");
        ProducerThread2 producerThread2=new ProducerThread2(resource,"producerThread2");
        ConsumerThread2 consumerThread = new ConsumerThread2(resource,"consumerThread");

        producerThread.start();
//        producerThread2.start();
        consumerThread.start();


    }

}
