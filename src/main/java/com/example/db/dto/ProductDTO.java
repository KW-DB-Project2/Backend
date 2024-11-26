package com.example.db.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private Long productId;
    private Long userId;
    private String productTitle;
    //private String productClass;
    private String productContent;
    private Long productPrice;
    private boolean productStatus;
    private byte[] productImg;
}
