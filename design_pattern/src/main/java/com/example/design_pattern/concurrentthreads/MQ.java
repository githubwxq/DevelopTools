package com.example.design_pattern.concurrentthreads;

import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class MQ {
    /**
     * 并发队列
     */
    private static Queue<String> queue = null;

   public static Queue<String> initQueue(){
       if (queue==null) {
           queue=new ConcurrentLinkedQueue<>();
       }
       String tasklist = "AAAAAAAAA,BBBBBBBB,CCCCCCC,DDDDDD,EEEEEEEEE,FFFFFFFFF,GGGGGGGG,HHHHHHHHH,IIIIIIIII,JJJJJJJJJ,JF1SH98FXAG186997,JF1BM92D8BG022510,JF1BM92DXAG013855,JF1BM94D8EG036618";
       String[] split = tasklist.split(",");
       List<String> task = Arrays.asList(split);
       queue.addAll(task);
       return queue;

   }




}
