package com.example.module_login.bean;

import java.util.List;

public class HomePageData {


    public List<HotCitiesBean> hotCities;
    public List<HotSellProductsBean> hotSellProducts;
    public List<RecomentProductsBean> recomentProducts;

    public static class HotCitiesBean {
        /**
         * cover :
         * id :
         * name :
         */

        public String cover;
        public String id;
        public String name;
    }

    public static class HotSellProductsBean {
        /**
         * cover :
         * id :
         * name :
         */

        public String cover;
        public String id;
        public String name;
    }

    public static class RecomentProductsBean {
        /**
         * cover :
         * id :
         * name :
         */

        public String cover;
        public String id;
        public String name;
    }
}
