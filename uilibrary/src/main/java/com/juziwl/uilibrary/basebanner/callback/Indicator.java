package com.juziwl.uilibrary.basebanner.callback;

public interface Indicator {

   void setSelect(int position);

   /**
    *
    * @param totalSize
    * @param isFirst 是否是初始化数据
    */
   void setTotalSize(int totalSize, boolean isFirst);

}
