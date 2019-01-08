package com.juzi.win.gank.bean;

import java.util.List;

/**
 * @author 文庆
 * @date 2019/1/8.
 * @description
 */

public class GankBaseResponse {
    public boolean error;
    public List<GankBean> results;

    public static class GankBean {

        /**
         * _id : 5c25db189d21221e8ada8664
         * createdAt : 2018-12-28T08:13:12.688Z
         * desc : 2018-12-28
         * publishedAt : 2018-12-28T00:00:00.0Z
         * source : web
         * type : 福利
         * url : https://ws1.sinaimg.cn/large/0065oQSqly1fymj13tnjmj30r60zf79k.jpg
         * used : true
         * who : lijinshanmx
         */

        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;
        public List<String> images;


    }

}
