package com.example.interviewdemo.reflect;

import androidx.annotation.IntDef;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 测试源码注解 
 */  
public class TestSourceAnnotation {  
  
    // 状态值  
    public static final int STATUS_OPEN = 1;  
    public static final int STATUS_CLOSE = 2;  
  
    private static int sStatus = STATUS_OPEN;  
  
  
    private TestSourceAnnotation() {}  
  
  
    // 定义适用于参数的注解，限定取值范围为{STATUS_OPEN, STATUS_CLOSE}  
    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({STATUS_OPEN, STATUS_CLOSE})
  
    public @interface Status {  
    }  
  
    /** 
     * 定义方法并使用@Status限定参数的取值 
     * @param status 
     */  
    public static void setStatus(@Status int status) {  
        sStatus = status;  
    }  
  
    public static int getStatus() {  
        return sStatus;  
    }  
  
  
    public static String getStatusDesc() {  
        if (sStatus == STATUS_OPEN) {  
            return "打开状态";  
        } else {  
            return "关闭状态";  
        }  
    }  
}  