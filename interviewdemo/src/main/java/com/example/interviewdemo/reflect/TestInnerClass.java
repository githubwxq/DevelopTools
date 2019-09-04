package com.example.interviewdemo.reflect;

import java.lang.reflect.Field;

public class TestInnerClass {
    public String file1;

    public TestInnerClass(String file1, String file2) {
        this.file1 = file1;
        this.file2 = file2;
    }

    public String file2;



    public static void main(String[] args) {
        TestInnerClass main = new TestInnerClass("1111","222");
        main.test();
    }

    private void test() {
        ReflectCallback callback=new ReflectCallback() {
          public   String name="wxq";
            public  String age="18";

            @Override
            public void callback(String s) {
                System.out.println("file1"+file1);
            }

            @Override
            public void callback2(String s2) {
                System.out.println("file2"+file2);
            }
        };
        callback.callback("");
       Class clazz=callback.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field declaredField : declaredFields) {
       ;
            System.out.println("fileName"+  declaredField.getName()); //属性名

            try {


                if (declaredField.get(callback) instanceof TestInnerClass) {
                    System.out.println(((TestInnerClass)(declaredField.get(callback))).file1);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }


}
