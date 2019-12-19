package com.example.interviewdemo.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@RuntimeAnnotation.ClassInfo("TestRunTimeAnnotation1111111111111111")
public class TestAnnotation {
    public TestAnnotation(String fileinfo, int i) {
        this.fileinfo = fileinfo;
        this.i = i;
    }

    @RuntimeAnnotation.FieldInfo(value = {1, 2})
    public String fileinfo = "fileInfo";
    @RuntimeAnnotation.FieldInfo(value = {100000})
    public int i = 100;

    @RuntimeAnnotation.MethodInfo(name = "wxq", data = "bigbig")
    public void testMethod(@RuntimeAnnotation.ParmeterInfo("id") String id, @RuntimeAnnotation.ParmeterInfo("age") int age) {
        System.out.println("2q121222");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("11111");
        StringBuffer sb = new StringBuffer();
        Class cls = TestAnnotation.class;
        Constructor[] constructors = cls.getConstructors();
        for (Constructor constructor : constructors) {
            System.out.println(constructor.getName());
        }
        sb.append("class 上面的注解").append("\n");
        RuntimeAnnotation.ClassInfo classInfo = (RuntimeAnnotation.ClassInfo) cls.getAnnotation(RuntimeAnnotation.ClassInfo.class);
        if (classInfo != null) {
            sb.append(cls.getModifiers()).append("\n");

            sb.append(cls.getSimpleName()).append("\n");
            sb.append("注解职").append(classInfo.value()).append("/n/n");
        }

        for (Field declaredField : cls.getDeclaredFields()) {
            RuntimeAnnotation.FieldInfo fieldInfo = declaredField.getAnnotation(RuntimeAnnotation.FieldInfo.class);
            if (fieldInfo != null) {
                sb.append(Modifier.toString(declaredField.getModifiers()));
                sb.append(declaredField.getName());
                sb.append("注解值：").append(Arrays.toString(fieldInfo.value())).append("\n\n");
            }
        }
        for (Method method : cls.getDeclaredMethods()) {
            RuntimeAnnotation.MethodInfo methodInfo = method.getAnnotation(RuntimeAnnotation.MethodInfo.class);
            if (methodInfo != null) {
                sb.append("method.getModifiers   " + Modifier.toString(method.getModifiers())).append(" ")
                        .append("ReturnType  " + method.getReturnType().getSimpleName()).append(" ")
                        .append("method_name   " + method.getName()).append("\n");
                // 注解值
                sb.append("注解值：").append("\n");
                sb.append("name: ").append(methodInfo.name()).append("\n");
                sb.append("data: ").append(methodInfo.data()).append("\n");
                sb.append("age: ").append(methodInfo.age()).append("\n");
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                RuntimeAnnotation.ParmeterInfo s = (RuntimeAnnotation.ParmeterInfo) parameterAnnotations[0][0];
                sb.append("parameterAnnotations_______" + s.value());
            }
        }
        System.out.println(sb.toString());
    }

}


//  retrofit源码分析
//https://www.jianshu.com/p/f1a8356c615f