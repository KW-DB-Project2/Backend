package com.example.db.service;

import com.example.db.dto.ProductReportDTO;
import com.example.db.dto.ReviewReportDTO;
import com.example.db.entity.ProductReport;
import com.example.db.entity.ReviewReport;
import com.example.db.jdbc.ReportRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {
    private final ReportRepository reportRepository;

    @Transactional
    public ProductReport createProductReport(ProductReportDTO productReportDTO) {
        ProductReport productReport = ProductReport.builder()
                .userId(productReportDTO.getUserId())
                .productId(productReportDTO.getProductId())
                .productReportContent(productReportDTO.getProductReportContent())
                .createId(productReportDTO.getUserId())
                .createTime(new Date())
                .updateId(productReportDTO.getUserId())
                .updateTime(new Date())
                .build();
        return reportRepository.createProductReport(productReport);
    }

    @Transactional
    public ReviewReport createReviewReport(ReviewReportDTO reviewReportDTO){
        ReviewReport reviewReport = ReviewReport.builder()
                .userId(reviewReportDTO.getUserId())
                .reviewId(reviewReportDTO.getReviewId())
                .reviewReportContent(reviewReportDTO.getReviewReportContent())
                .createId(reviewReportDTO.getUserId())
                .createTime(new Date())
                .updateId(reviewReportDTO.getUserId())
                .updateTime(new Date())
                .build();
        return reportRepository.createReviewReport(reviewReport);
    }

    public List<ProductReport> getAllProductReport(){
        return reportRepository.getAllProductReport();
    }

    public List<ReviewReport> getAllReviewReport(){
        return reportRepository.getAllReviewReport();
    }

}
