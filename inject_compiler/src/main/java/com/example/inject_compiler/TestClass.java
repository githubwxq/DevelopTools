package com.example.inject_compiler;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

import sun.applet.Main;

/**
 * author:wxq
 * email:805380422@qq.com
 * time:2019/04/22
 * desc:
 * version:1.0
 */
public class TestClass {

    public static void main(String[] args) {
        TestClass mainClass = new TestClass();
        mainClass.generateHelloWord();
    }

    /**
     * 创建helloword文件
     */
    private void generateHelloWord() {

        MethodSpec main = MethodSpec.methodBuilder("show")
                .addModifiers(Modifier.PUBLIC,Modifier.STATIC)
                .addStatement("$T.out.println($S)",System.class,"Hello World!")
                .build();
        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.example.inject_compiler", helloWorld).build();
//        File outputFile = new File("src/"); //输出文件
        File outputFile = new File("/"); //输出文件

        try {
            javaFile.writeTo(outputFile);
            javaFile.writeTo(System.out);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
