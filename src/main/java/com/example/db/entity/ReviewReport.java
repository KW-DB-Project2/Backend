package com.example.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
