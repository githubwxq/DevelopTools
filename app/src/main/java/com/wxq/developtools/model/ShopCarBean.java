package com.wxq.developtools.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopCarBean implements Serializable {
    /**
     * allPrice : 0
     * coverUrl :
     * id :
     * num :
     * outDate :
     * productId :
     * productName :
     * productPackageDetailId :
     * productPackageId :
     * productPackageName :
     * ticketDate :
     * ticketType :
     * unitPrice : 0
     * userId :
     */

    public String allPrice;
    public String coverUrl;
    public String id;
    public String num;
    public String outDate;
    public String productId;
    public String productName;
    public String productPackageDetailId;
    public String productPackageId;
    public String productPackageName;
    public String ticketDate;
    public String ticketType;
    public String unitPrice;
    public String userId;

    public boolean isSelect;

    public String getMonney() {
        return    Double.toString(Double.valueOf(unitPrice)* Integer.valueOf(num))+ "";
    }
    public String getTicketTypeName() {
        if ("1".equals(ticketType)) {
            return "成人";
        }
        if ("2".equals(ticketType)) {
            return "儿童";
        }

        return "成人";
    }


    /**
     * 对应的已经选中的出行人员默认为空
     */
    public  List<PersonInfo> personList=new ArrayList<>();

}