package com.example.db.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Product {
    private Long productId;
    private Long userId;
    private String productTitle;
    private String productClass;
    private String productContent;
    private int productPrice;
    private boolean productStatus;
    private byte[] productImg;
    private int createId;
    private Date createTime;
    private int updateId;
    private Date updateTime;
}
