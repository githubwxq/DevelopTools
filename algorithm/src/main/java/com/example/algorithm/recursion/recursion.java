package com.example.algorithm.recursion;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/29
 * desc:
 *  递归满足2个条件：
 *
 *     1）有反复执行的过程（调用自身）
 *
 *     2）有跳出反复执行过程的条件（递归出口）
 *
 * version:1.0
 */
public class recursion {

    public static  long fibolter(int n){
       if (n==0){
           return 0;
       }else if (n==1){
           return 1;
       }else {

           return fibolter(n-1)+fibolter(n-2);
       }





    }


    /***
     16      * 递归阶乘  n! = n*(n-1)*(n-2)*...*1
     17      * @param n 需要求取阶乘的数值
     18      * @return
     19      */
  public static int recursionMulity(int n){

         if(n == 1){
                  return 1;
              }
             return n * recursionMulity(n-1);
          }


    // 递归法 Q：一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
    public int JumpFloor(int target) {
        if (target <= 2) {
            return target;
        } else {
            return  JumpFloor(target-1)+JumpFloor(target-2);
        }
    }
    //迭代法 Q：一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
    public  int jumpFloor2(int target){
        if (target<=2) {
            return target;
        }
        int f1=1;
        int f2=2;
        int f=0;
        for (int i=3;i<target;i++){
            f=f1+f2;
            f1=f2;
            f2=f;
        }
        return f;
    }







   public static  void  main(String[] args){
      System.out.println(fibolter(1));
      System.out.println(fibolter(2));
      System.out.println(fibolter(3));
      System.out.println(fibolter(4));
      System.out.println(fibolter(5));
      System.out.println(fibolter(6));
      System.out.println(fibolter(7));



       System.out.println("阶乘"+recursionMulity(4));

   }




}
