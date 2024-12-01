package com.example.db.jdbc;

import com.example.db.dto.MonthlyTransactionData;
import com.example.db.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
@Slf4j
public class TransactionRepository {

    private final JdbcTemplate jdbcTemplate;

    public TransactionRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    /*
    public List<MonthlyTransactionData> getMonthlyTransactions() {
        String sql = "SELECT MONTH(transaction_date) as month, " +
                "       COUNT(*) as count, " +
                "       SUM(transaction_amount) as totalAmount " +
                "FROM transactions " +
                "GROUP BY MONTH(transaction_date)";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MonthlyTransactionData.class));
    }
    */
    public List<MonthlyTransactionData> getMonthlyTransactions() {
        String sql = "SELECT YEAR(transaction_date) as year, " +
                "       MONTH(transaction_date) as month, " +
                "       COUNT(*) as count, " +
                "       SUM(transaction_amount) as totalAmount " +
                "FROM transactions " +
                "GROUP BY YEAR(transaction_date), MONTH(transaction_date) " +
                "ORDER BY YEAR(transaction_date), MONTH(transaction_date) ASC";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MonthlyTransactionData.class));
    }




    public int saveTransaction(Transaction transaction){
        String sql = "INSERT INTO transactions (user_id, product_id, transaction_date, transaction_amount) " +
                "VALUES (?, ?, ?, ?)";
        Date temp_date = new Date();
        Timestamp transactionDate = new Timestamp(temp_date.getTime());
        return jdbcTemplate.update(sql,
                transaction.getUserId(),
                transaction.getProductId(),
                transactionDate,
                transaction.getTransactionAmount());
    }

    public List<MonthlyTransactionData> getTransactionByUser(Long userId){
        List<MonthlyTransactionData> temp = null;
        try{
            String sql = "select month(transaction_date) as month, count(*) as count, sum(transaction_amount) as totalAmount "
                    + "from transactions where user_id = ? " +
                    "group by month(transaction_date)";
            temp = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MonthlyTransactionData.class), userId);

        }catch(Exception e){
            log.error("user별 transaction 가져오기 실패");
            e.printStackTrace();
        }
        return temp;
    }
}
