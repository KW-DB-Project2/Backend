package com.example.db.jdbc;

import com.example.db.dto.MonthlyTransactionData;
import com.example.db.entity.Transaction;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<MonthlyTransactionData> getMonthlyTransactions() {
        String sql = "SELECT MONTH(transaction_date) as month, " +
                "       COUNT(*) as count, " +
                "       SUM(transaction_amount) as totalAmount " +
                "FROM transactions " +
                "GROUP BY MONTH(transaction_date)";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MonthlyTransactionData.class));
    }


    public int saveTransaction(Transaction transaction){
        String sql = "INSERT INTO transactions (user_id, product_id, transaction_date, transaction_amount) " +
                "VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                transaction.getUserId(),
                transaction.getProductId(),
                transaction.getTransactionDate(),
                transaction.getTransactionAmount());
    }
}
