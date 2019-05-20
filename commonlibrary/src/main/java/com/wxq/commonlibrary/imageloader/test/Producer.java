package com.wxq.commonlibrary.imageloader.test;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public  class Producer implements Runnable {
  private final BlockingQueue<Integer> blockingQueue;
  private Random random;
  boolean flag=false;

  public Producer(BlockingQueue<Integer> blockingQueue) {
    this.blockingQueue = blockingQueue;
    random = new Random();

  }

  public void run() {
    while (!flag) {
      int info = random.nextInt(100);
      try {
        blockingQueue.put(info);
        Thread.sleep(50);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}

