package com.example.db.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductReportDTO {
    private Long productReportId;
    private Long userId;
    private Long productId;
    private String productReportContent;
}
