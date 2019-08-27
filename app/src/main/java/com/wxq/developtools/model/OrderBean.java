package com.wxq.developtools.model;

public class OrderBean {

    public static final String PAYSUCCESS="30";
    public static final String PAYFAIL="40";
    public static final String WAITPAY="10";


    /**
     * createTime :
     * flowNum :
     * id :
     * num :
     * orderDate :
     * productId :
     * productName :
     * productPackageId :
     * productPackageName :
     * ticketType :
     */

    public String createTime;
    public String flowNum;
    public String id;
    public String num;
    public String orderDate;
    public String productId;
    public String productName;
    public String productPackageId;
    public String productPackageName;
    public String ticketType;
    public String ticketDate;
    public String pic="";
    public String price="";
    public String status=""; //10 待支付   30支付成功 40支付失败
    public String unitPrice;
    public String productPackageDetailId;

    public String getTicketDescribe() {
        if (ticketType.equals("1")){
            return "成人"+num;
        }else {
            return "儿童"+num;
        }
    }
    public String getStateName() {
        if (status.equals(WAITPAY)) {
            return "待支付";
        }
        if (status.equals(PAYSUCCESS)) {
            return "支付成功";
        }
        if (status.equals(PAYFAIL)) {
            return "支付失败";
        }
        return "其他";
    }

}
