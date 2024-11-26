package com.example.db.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductReport {
    private Long productReportId;
    private Long userId;
    private Long productId;
    private String productReportContent;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
}
