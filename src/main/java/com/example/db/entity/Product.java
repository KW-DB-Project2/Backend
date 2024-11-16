package com.example.db.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private Long productId;
    private int userId;
    private String productTitle;
    private String productClass;
    private String productContent;
    private int productPrice;
    private boolean productStatus;
    private byte[] productImg;

}
