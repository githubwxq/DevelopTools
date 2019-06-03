package com.example.interviewdemo.producerandconsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Resource2 {
  private BlockingQueue resourceQueue =new LinkedBlockingDeque(10);

  public void add(){
      try {
          resourceQueue.put(1);
            System.out.println("生产者" + Thread.currentThread().getName()
                          + "生产一件资源," + "当前资源池有" + resourceQueue.size() +
                                "个资源");



      } catch (InterruptedException e) {
          e.printStackTrace();
      }


  }




public void remove(){
    try {
        Object o = resourceQueue.take();

        System.out.println("消费者" + Thread.currentThread().getName() +
                            "消耗一件资源," + "当前资源池有" + resourceQueue.size()
                    + "个资源");

    } catch (InterruptedException e) {
        e.printStackTrace();
    }


}







}
