package com.example.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Long reviewId;
    private Long userId;
    private Long productId;
    private String reviewTitle;
    private String reviewContent;
    private String username;  // 추가된 필드
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
}
