package com.wxq.developtools.model;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public  class RecomentProductsBean implements MultiItemEntity {
        /**
         * cover :
         * id :
         * name :
         */

        public String cover;
        public String id;
        public String name;

    @Override
    public int getItemType() {
        return 3;
    }
}