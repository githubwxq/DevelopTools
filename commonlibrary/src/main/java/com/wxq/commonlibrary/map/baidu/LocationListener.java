package com.wxq.commonlibrary.map.baidu;

import com.baidu.location.BDLocation;

public interface LocationListener {

        /**
         * 成功
         */
        void success(BDLocation success);

        /**
         * 权限问题
         */
        void permissionError();

        /**
         * 其他问题
         */
        void otherError(String error);


    }