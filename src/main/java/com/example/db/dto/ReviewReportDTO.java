package com.example.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewReportDTO {
    private Long reviewReportId;
    private Long userId;
    private Long productId;
    private String reviewReportContent;
}
