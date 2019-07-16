package com.wxq.developtools.model;

import java.io.Serializable;
import java.util.List;

public class ProductDetailBean implements Serializable {


    @Override
    public String toString() {
        return "ProductDetailBean{" +
                "cover='" + cover + '\'' +
                ", description='" + description + '\'' +
                ", id='" + id + '\'' +
                ", introduction='" + introduction + '\'' +
                ", isCollect='" + isCollect + '\'' +
                ", mustUnderstand='" + mustUnderstand + '\'' +
                '}';
    }

    /**
     * cover :
     * description :
     * id :
     * introduction :
     * isCollect :
     * mustUnderstand :
     * name :
     * price : 0
     * priceInclude :
     * priceUninclude :
     * problem :
     * productPackageVos : [{"id":"","name":""}]
     * videoUrl :
     */

    public String cover;
    public String description;
    public String id;
    public String introduction;
    public String isCollect;
    public String mustUnderstand;
    public String name;
    public int price;
    public String priceInclude;
    public String priceUninclude;
    public String problem;
    public String videoUrl;
    public List<ProductPackageVosBean> productPackageVos;


}
