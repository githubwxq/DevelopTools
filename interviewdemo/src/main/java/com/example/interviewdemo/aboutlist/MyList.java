package com.example.interviewdemo.aboutlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;

public class MyList {
    /*  三者都属于List的子类，方法基本相同。比如都可以实现数据的添加、删除、定位以及都有迭代器进行数据的查找，但是每个类
        在安全，性能，行为上有着不同的表现。
                　　Vector是Java中线程安全的集合类，如果不是非要线程安全，不必选择使用，毕竟同步需要额外的性能
        开销，底部实现也是数组来操作，再添加数据时，会自动根据需要创建新数组增加长度来保存数据，并拷贝原有数组数据
    　　ArrayList是应用广泛的动态数组实现的集合类，不过线程不安全，所以性能要好的多，也可以根据需要增加数组容量，不过与
        Vector的调整逻辑不同，ArrayList增加50%，而Vector会扩容1倍。
                　　LinkedList是基于双向链表实现，不需要增加长度，也不是线程安全的
    　　Vector与ArrayList在使用的时候，应保证对数据的删除、插入操作的减少，因为每次对改集合类进行这些操作时，都会使原有数据
        进行移动除了对尾部数据的操作，所以非常适合随机访问的场合。
                　　LinkedList进行节点的插入、删除却要高效的多，但是随机访问对于该集合类要慢的多。
    */
    public List<String> list=new ArrayList<>();

    public List<String> linkedList=new LinkedList<>();

    public List<String> vector=new Vector<>();













    Map map=new HashMap();

    Queue queue=new ArrayBlockingQueue(10);

    Stack<Integer> stack=new Stack<Integer>();

    Set<Integer> integerSet=new HashSet<>();


    public static  void main(String[] args){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Runnable");
            }
        }).start();

        new MyThread().start();




    }

    static class  MyThread extends Thread{

        @Override
        public void run() {
           System.out.println("mytherad");

        }
    }



}

/*
  Map
          1、键值对的对象，一个映射不能包括重复的键；

          2、某些映射实现可以确保顺序，如TreeMap类；

          3、另一些映射不能保证顺序，如HashMap类；

          4、KeySet（）抽取key序列；

          5、Map中的所有Keys生成一个集合，不重复；

          6、Values（）抽取value序列；

          7、Map中的所有Values生成一个Collection；

          List
          1、可以随机访问包含的元素，元素是有序的；

          2、可以在任意位置增、删元素；

          3、不管访问多少次，元素位置不变；

          4、允许重复元素；

          5、用Iterator实现单向遍历；

          6、用ListIterator实现双向遍历;

          Queue
          1、先进先出；

          2、用offer（）加入元素；

          3、用poll（）来获取移除元素；

          4、用element（）或peek（）获得前端元素；

          Stack
          1、后进先出；

          2、继承Vector，线程同步，即线程安全的；

          3、使用push、pop、peek、empty、search等方法；

          Set
          1、包含不重复元素的Collection；

          2、Set不包含o1.equals(o2)的元素；

          3、对o1和o2 最多包含一个null元素；

          4、不可以随机访问包含的元素；

          5、只能用Iterator实现单向遍历；

          6、Set没有同步方法；
    */
