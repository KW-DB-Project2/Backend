package com.example.db.controller;

import com.example.db.dto.MonthlyTransactionData;
import com.example.db.entity.Transaction;
import com.example.db.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("monthly/{userId}")
    public List<MonthlyTransactionData> getTransactionsByUser(@PathVariable("userId") Long userId){
        return transactionService.getTransactionByUser(userId);
    }


}
