package com.example.db.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private Long reviewId;
    private Long userId;
    private Long productId;
    private String reviewTitle;
    private String reviewContent;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
}
