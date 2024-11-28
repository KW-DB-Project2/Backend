package com.example.db.service;

import com.example.db.dto.MonthlyTransactionData;
import com.example.db.entity.Transaction;
import com.example.db.jdbc.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public List<MonthlyTransactionData> getMonthlyTransactions() {
        return transactionRepository.getMonthlyTransactions();
    }

    @Transactional
    public int saveTransaction(Transaction transaction) {
        return transactionRepository.saveTransaction(transaction);
    }
}
