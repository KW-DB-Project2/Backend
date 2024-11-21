package com.example.db.entity;

import lombok.Data;

import java.util.Date;

@Data
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
