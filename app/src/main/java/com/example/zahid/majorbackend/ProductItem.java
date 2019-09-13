package com.example.zahid.majorbackend;

public class ProductItem {
    private String productname;
    private String productprice;
    private String productimageurl;

    public ProductItem(String productname, String productprice, String productimageurl) {
        this.productname = productname;
        this.productprice = productprice;
        this.productimageurl = productimageurl;
    }

    public String getProductname() {
        return productname;
    }

    public String getProductprice() {
        return productprice;
    }

    public String getProductimageurl() {
        return productimageurl;
    }
}
