package com.example.db.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewInfo {

    private Long reviewId;
    private Long loginId;  //user_Id
    private Long productId;
    private String reviewTitle;
    private String reviewContent;
    private byte[] reviewProductImg;
}
