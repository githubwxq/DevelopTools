package com.example.algorithm.sort;

import java.util.Arrays;

/**
 * Created by 王晓清.
 * data 2019/5/28.
 * describe . 选择排序
 */
public class SelectSort {

    public static void sort(int[] arr, boolean asc) {
        for (int i = 0; i < arr.length; i++) {
            int index = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (asc) {
                    if (arr[index] > arr[j]) {
                        index = j;
                    }
                } else {
                    if (arr[index] < arr[j]) {// 降序，选择无序区最大的元素
                        index = j;
                    }
                }
            }

            if (index != i) {
                swap(arr, index, i);
            }


        }


    }


    public static void sort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
             int currentMax=i; //从大到小    先把第一个位子的最大的拿到 付给第一个位子 然后在在判断第二个位子的
            for (int j=i+1;j<arr.length;j++){
                if (arr[currentMax]<arr[j]) {
                    currentMax=j;
                }
            }
             int temp=arr[i];
            arr[i]=arr[currentMax];
            arr[currentMax]=temp;
        }

    }




    /**
     * 交换数组中的两个元素的位置
     *
     * @param arr // 数组
     * @param i   // 变量i
     * @param j   // 变量j
     */
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public static void main(String[] args) {
        int[] arr = {1, 3, 23, 8, 10, 34, 13, 56};
//        System.out.println("排序数组：" + Arrays.toString(arr));
//        sort(arr, true);
//        System.out.println("升序排列：" + Arrays.toString(arr));
//        sort(arr, false);
        sort(arr);

        System.out.println("降序排列：" + Arrays.toString(arr));




    }


}
