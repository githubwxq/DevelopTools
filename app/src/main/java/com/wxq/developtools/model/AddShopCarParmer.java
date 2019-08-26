package com.wxq.developtools.model;

public class AddShopCarParmer {
    public String num;
    public String productId;
    public String productPackageDetailId;
    public String productPackageId;
    public String ticketDate;

    public AddShopCarParmer(String num, String productId, String productPackageDetailId, String productPackageId, String ticketDate, String ticketType) {
        this.num = num;
        this.productId = productId;
        this.productPackageDetailId = productPackageDetailId;
        this.productPackageId = productPackageId;
        this.ticketDate = ticketDate;
        this.ticketType = ticketType;
    }

    public String ticketType;


}
