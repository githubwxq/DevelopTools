package com.example.algorithm.stackqueue;

import java.util.Stack;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/05/29
 * desc:（手写算法）用两个栈实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型。
 * version:1.0
 */
public class Solution {
    Stack<Integer> stack1 = new Stack<>();
    Stack<Integer> stack2 = new Stack<>();


    //放入abc 我希望我出来的时候也是abc  另一个必须输cba 反过来
    public void add(int node) {

        stack1.push(node);

    }

    public int poll() {
        if (stack1.empty() && stack2.empty()) {
            throw new RuntimeException("Queue is empty!");
        }

        if (stack2.isEmpty()) {
            while (!stack1.empty()) {
                stack2.push(stack1.pop()); // stack1的栈顶获取的值放到stack2下面
            }

        }
        return stack2.pop();
    }


}
