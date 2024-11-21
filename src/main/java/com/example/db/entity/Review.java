package com.example.db.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class Review {

    private Long reviewId;
    private Long userId;
    private Long productId;
    private String reviewTitle;
    private String reviewContent;
    private byte[] reviewImg;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
}
