package com.example.db.jdbc;

import com.example.db.entity.ProductReport;
import com.example.db.entity.ReviewReport;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Repository
@AllArgsConstructor
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductReport createProductReport(ProductReport productReport){
        final String sql = "insert into product_report(user_id, product_id, product_report_content, create_id, create_time, update_id, update_time) "+
                "values(?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Timestamp createTime = new Timestamp(productReport.getCreateTime().getTime());
        Timestamp updateTime = new Timestamp(productReport.getUpdateTime().getTime());
        jdbcTemplate.update(con->{
            PreparedStatement ps = con.prepareStatement(sql, new String[] {"product_report_id"});
            ps.setLong(1, productReport.getUserId());
            ps.setLong(2, productReport.getProductId());
            ps.setString(3, productReport.getProductReportContent());
            ps.setLong(4, productReport.getCreateId());
            ps.setTimestamp(5, createTime);
            ps.setLong(6,productReport.getUpdateId());
            ps.setTimestamp(7, updateTime);
            return ps;
        }, keyHolder);

        Long generatedProductReportId = keyHolder.getKey().longValue();
        String selectSql = "select * from product_report where product_report_id = ?";
        return jdbcTemplate.queryForObject(selectSql,new BeanPropertyRowMapper<>(ProductReport.class),generatedProductReportId);
    }

    public ReviewReport createReviewReport(ReviewReport reviewReport){
        final String sql = "insert into review_report(user_id, review_id, review_report_content, create_id, create_time, update_id, update_time) "+
                "values(?,?,?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Timestamp createTime = new Timestamp(reviewReport.getCreateTime().getTime());
        Timestamp updateTime = new Timestamp(reviewReport.getUpdateTime().getTime());
        jdbcTemplate.update(con->{
            PreparedStatement ps = con.prepareStatement(sql, new String[] {"review_report_id"});
            ps.setLong(1, reviewReport.getUserId());
            ps.setLong(2, reviewReport.getReviewId());
            ps.setString(3, reviewReport.getReviewReportContent());
            ps.setLong(4, reviewReport.getCreateId());
            ps.setTimestamp(5, createTime);
            ps.setLong(6,reviewReport.getUpdateId());
            ps.setTimestamp(7, updateTime);
            return ps;
        }, keyHolder);


        Long generatedReviewReportId = keyHolder.getKey().longValue();
        String selectSql = "select * from review_report where review_report_id = ?";
        return jdbcTemplate.queryForObject(selectSql,new BeanPropertyRowMapper<>(ReviewReport.class),generatedReviewReportId);
    }

    public List<ProductReport> getAllProductReport(){
        String sql = "SELECT * FROM product_report";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductReport.class));
    }

    public List<ReviewReport> getAllReviewReport(){
        String sql = "SELECT * FROM review_report";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ReviewReport.class));
    }
}
