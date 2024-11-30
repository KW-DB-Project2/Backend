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
public class ProductReportDTOWithId {

    private Long productReportId;
    private Long userId;
    private Long productId;
    private Long reportedUserId;
    private String productReportContent;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
}
