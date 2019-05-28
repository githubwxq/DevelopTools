package com.example.algorithm.sort;

import java.util.Arrays;

/**
 * Created by 王晓清.
 * data 2019/5/28.
 * describe .
 */
public class BubbleSort {


    /**
     * 冒泡排序
     * @param arr
     * @param asc
     */
    public static void sort(int [] arr,boolean asc){
        for (int i = 0; i < arr.length; i++) {
            for(int j=i+1;j<arr.length;j++){
                if(asc){ //升序
                    if (arr[i]>arr[j]) {
                        swap(arr,i,j);
                    }
                }else {
                    if (arr[i]<arr[j]){
                        swap(arr,i,j);
                    }
                }
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp= arr[i];

        // 后面都 给前面
        arr[i]=arr[j];

        arr[j]=temp;

    }

    public static void main(String[] args) {
        int[] arr = {1,3,2,5,9,10,53,33,55};
        System.out.println("排序数组" + Arrays.toString(arr));
        sort(arr,true);
        System.out.println("升序数组：" + Arrays.toString(arr));
        sort(arr,false);
        System.out.println("降序数组：" + Arrays.toString(arr));
    }



}
