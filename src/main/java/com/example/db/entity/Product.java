package com.example.db.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long productId;
    private Long userId;
    private String productTitle;
    private String productContent;
    private Long productPrice;
    private boolean productStatus;
    private byte[] productImg;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
}
