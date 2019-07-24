package com.wxq.developtools.model;

import java.io.Serializable;

/**
 * 成人套餐详情 价格
 */
public  class VosBean implements Serializable {
        /**
         * id : 
         * productPackageId : 
         * sellingPrice : 0
         * stockNum :   库存
         * ticketDate : 
         * ticketType : 
         */

        public String id;
        public String productPackageId;
        public String sellingPrice;
        public String stockNum;
        public String ticketDate;

        public String getTicketTypeName() {
                if ("1".equals(ticketType)) {
                        return "成人";
                }
                if ("2".equals(ticketType)) {
                        return "儿童";
                }

                return "成人";
        }

        public String ticketType;  //商品套餐类别 1成人 2儿童



    }