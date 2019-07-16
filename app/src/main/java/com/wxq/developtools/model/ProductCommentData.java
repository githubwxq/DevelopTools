package com.wxq.developtools.model;

import java.io.Serializable;
import java.util.List;

public class ProductCommentData implements Serializable {



    public List<CommentBean> list;

    public static class PaginationBean {
        /**
         * page : 0
         * rows : 0
         * total : 0
         */

        public int page;
        public int rows;
        public int total;
    }


}
