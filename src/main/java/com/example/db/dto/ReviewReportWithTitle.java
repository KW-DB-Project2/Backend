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
public class ReviewReportWithTitle {

    private Long reviewReportId;
    private Long userId;
    private Long reviewId;
    private String reviewReportContent;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
    private String productTitle;
}
