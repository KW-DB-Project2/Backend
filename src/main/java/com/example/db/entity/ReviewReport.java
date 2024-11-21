package com.example.db.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ReviewReport {
    private Long reviewReportId;
    private Long userId;
    private Long productId;
    private String reviewReportContent;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
}
