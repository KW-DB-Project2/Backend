package com.example.db.controller;

import com.example.db.entity.Transaction;
import com.example.db.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody Transaction transaction) {
        int rowsAffected = transactionService.saveTransaction(transaction);
        if (rowsAffected > 0) {
            return ResponseEntity.ok().body(rowsAffected);
        }
        return ResponseEntity.badRequest().body("There is no saved data");
    }
}
