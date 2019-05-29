package com.example.algorithm.search;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/29
 * desc: 二分查找
 * version:1.0
 */
public class BinarySearch {



    public static int binarySearch(int[] a,int key){

        int low,mid,high;
        low =0;

        high=a.length-1;


        while (low<=high){
            mid = (high + low) / 2;//折半下标

             if(key>a[mid]){
                 low=mid+1;  //关键字比折半值大，则最小下标调成折半下标的下一位
             }else if(key<a[mid]){
                 high=mid-1; //关键字比折半值小，则最大下标调成折半下标的前一位
             }else {
                 return mid; //关键字和折半值相等时返回折半下标
             }




        }

          return -1;

    }


}
