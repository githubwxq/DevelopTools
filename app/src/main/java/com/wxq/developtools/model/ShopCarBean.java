package com.wxq.developtools.model;

public class ShopCarBean {
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


}