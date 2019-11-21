package com.juziwl.uilibrary.basebanner.callback;

import android.view.View;

public  interface PageHelperListener<T> {
   void getItemView(View mCurrentContent, T item, int position);
}
