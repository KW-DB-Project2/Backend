package com.example.db.jdbc;

import com.example.db.entity.Review;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReviewRepository {

    private final JdbcTemplate template;

    public ReviewRepository(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    public List<Review> searchReview(String keyword){
        //String sql = "SELECT * FROM review WHERE review_title LIKE ? OR review_content LIKE ?";
        //return template.query(sql, reviewRowMapper, "%" + keyword + "%", "%" + keyword + "%");
        String sql = "SELECT r.* FROM review r " +
                "JOIN account c ON r.user_id = c.id " +
                "WHERE r.review_title LIKE ? OR r.review_content LIKE ? OR c.username LIKE ?";
        return template.query(sql, reviewRowMapper, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
    }

    public int createReview(Review review){
        String sql = "INSERT INTO review (user_id, product_id, review_title, review_content, review_img, create_id, create_time) VALUES (?, ?, ?, ?, ?, ?, NOW())";
        return template.update(sql, review.getUserId(), review.getProductId(),  review.getReviewTitle(), review.getReviewContent(), review.getReviewImg(), review.getUserId());
    }

    public int updateReview(Review review) {
        String sql = "UPDATE review SET review_title = ?, review_content = ?, review_img = ?, update_id = ?, update_time = NOW() WHERE review_id = ? AND user_id = ?";
        return template.update(sql, review.getReviewTitle(), review.getReviewContent(), review.getReviewImg(), review.getUserId(), review.getReviewId(), review.getUserId());
    }

    public int deleteReview(Long reviewId, Long userId) {
        String sql = "DELETE FROM review WHERE review_id = ? AND user_id = ?";
        return template.update(sql, reviewId, userId);
    }

    private RowMapper<Review> reviewRowMapper = (rs, rowNum) -> {
        Review review = new Review();
        review.setReviewId(rs.getLong("review_id"));
        review.setUserId(rs.getLong("user_id"));
        review.setProductId(rs.getLong("product_id"));
        review.setReviewTitle(rs.getString("review_title"));
        review.setReviewContent(rs.getString("review_content"));
        review.setReviewImg(rs.getBytes("review_img"));
        review.setCreateId(rs.getLong("create_id"));
        review.setCreateTime(rs.getDate("create_time"));
        review.setUpdateId(rs.getLong("update_id"));
        review.setUpdateTime(rs.getDate("update_time"));
        return review;
    };
}
