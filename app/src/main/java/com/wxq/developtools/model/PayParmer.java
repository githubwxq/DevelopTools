package com.wxq.developtools.model;

import java.util.List;

public class PayParmer {

    public  String price;

    public List<PayDetail> dtos;


    public static class PayDetail {
          public String num;
          public String productId;
          public String productPackageDetailId;
          public String productPackageId;
          public String remark;
          public List<String> userAccountIds;
    }
}


