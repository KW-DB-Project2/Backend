package com.example.db.controller;

import com.example.db.dto.ProductReportDTO;
import com.example.db.dto.ReviewReportDTO;
import com.example.db.entity.ProductReport;
import com.example.db.entity.ReviewReport;
import com.example.db.service.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("report")
public class ReportController {
    private final ReportService reportService;

    @PostMapping("/product")
    public ProductReport createProductReport(@RequestBody ProductReportDTO productReportDTO){
        return reportService.createProductReport(productReportDTO);
    }

    @PostMapping("/review")
    public ReviewReport createReviewReport(@RequestBody ReviewReportDTO reviewReportDTO){
        return reportService.createReviewReport(reviewReportDTO);
    }



}
