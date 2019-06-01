package com.example.interviewdemo.testleak;

import android.content.Context;

public class IMManager {
  private Context context;
  private static IMManager mInstance;
 
  public static IMManager getInstance(Context context) {
    if (mInstance == null) {
      synchronized (IMManager.class) {
        if (mInstance == null)
          mInstance = new IMManager(context);
      }
    }
    return mInstance;
  }
 
  private IMManager(Context context) {
    this.context = context;
  }
 
}