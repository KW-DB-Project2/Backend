package com.example.db.dto;

import lombok.Data;

@Data
public class MonthlyTransactionData {
    private int month;
    private int count;
    private double totalAmount;   // 거래 금액 합계
}
