package com.example.db.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Transaction {
    private Long transactionId;
    private Long userId;
    private Long productId;
    private Date transactionDate;
    private Double transactionAmount;
}
