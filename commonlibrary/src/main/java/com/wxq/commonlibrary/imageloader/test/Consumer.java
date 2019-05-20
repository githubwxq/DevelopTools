package com.wxq.commonlibrary.imageloader.test;

import java.util.concurrent.BlockingQueue;

//消费者
  public  class Consumer implements Runnable{
    boolean flag=false;
    private final BlockingQueue<Integer> blockingQueue;
    public Consumer(BlockingQueue<Integer> blockingQueue) {
      this.blockingQueue = blockingQueue;
    }
    public void run() {
      while(!flag){
        int info;
        try {
          info = blockingQueue.take();
          Thread.sleep(50);
        } catch (InterruptedException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }        
      }
    }
  }