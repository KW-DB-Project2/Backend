package com.example.db.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewReportDTO {
    private Long reviewReportId;
    private Long userId;
    private Long productId;
    private String reviewReportContent;
}
