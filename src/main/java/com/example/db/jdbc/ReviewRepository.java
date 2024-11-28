package com.example.db.jdbc;

import com.example.db.dto.ReviewDTO;
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

    public List<Review> getAllReviews(){
        String sql = "SELECT * FROM review";
        return template.query(sql, reviewRowMapper);
    }

    /*
    public List<Review> searchReview(String keyword){
        //String sql = "SELECT * FROM review WHERE review_title LIKE ? OR review_content LIKE ?";
        //return template.query(sql, reviewRowMapper, "%" + keyword + "%", "%" + keyword + "%");
        String sql = "SELECT r.* FROM review r " +
                "JOIN account c ON r.user_id = c.id " +
                "WHERE r.review_title LIKE ? OR r.review_content LIKE ? OR c.username LIKE ?";
        return template.query(sql, reviewRowMapper, "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
    }*/
    public List<ReviewDTO> searchReview(String keyword) {
        String sql = "SELECT r.review_id, r.user_id, r.product_id, r.review_title, r.review_content, " +
                "r.create_id, r.create_time, r.update_id, r.update_time, c.username " +
                "FROM review r " +
                "JOIN account c ON r.user_id = c.id " +
                "WHERE r.review_title LIKE ? OR r.review_content LIKE ? OR c.username LIKE ?";

        return template.query(sql, (rs, rowNum) -> ReviewDTO.builder()
                .reviewId(rs.getLong("review_id"))
                .userId(rs.getLong("user_id"))
                .productId(rs.getLong("product_id"))
                .reviewTitle(rs.getString("review_title"))
                .reviewContent(rs.getString("review_content"))
                .username(rs.getString("username"))  // username 추가
                .createId(rs.getLong("create_id"))
                .createTime(rs.getTimestamp("create_time"))
                .updateId(rs.getLong("update_id"))
                .updateTime(rs.getTimestamp("update_time"))
                .build(), "%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%");
    }




    public int createReview(Review review){
        String sql = "INSERT INTO review (user_id, product_id, review_title, review_content, create_id, create_time) VALUES (?, ?, ?, ?, ?, NOW())";
        return template.update(sql, review.getUserId(), review.getProductId(),  review.getReviewTitle(), review.getReviewContent(), review.getUserId());
    }

    public int updateReview(Review review) {
        String sql = "UPDATE review SET review_title = ?, review_content = ?, update_id = ?, update_time = NOW() WHERE review_id = ? AND user_id = ?";
        return template.update(sql, review.getReviewTitle(), review.getReviewContent(), review.getUserId(), review.getReviewId(), review.getUserId());
    }

    public int deleteReview(Long reviewId, Long userId) {
        String sql = "DELETE FROM review WHERE review_id = ? AND user_id = ?";
        return template.update(sql, reviewId, userId);
    }
    public int deleteReview(Long reviewId) {
        String sql = "DELETE FROM review WHERE review_id = ?";
        return template.update(sql, reviewId);
    }

    public List<Review> findReviewsByProductId(Long productId) {
        String sql = "SELECT * FROM review WHERE product_id = ?";
        return template.query(sql, reviewRowMapper, productId);
    }

    public Review findReviewWithReviewId(Long reviewId){
        String sql = "SELECT * FROM review WHERE review_id = ?";
        return template.queryForObject(sql, reviewRowMapper, reviewId);
    }

    public List<ReviewDTO> findReviewsWithUsernameByProductId(Long productId) {
        String sql = "SELECT r.review_id, r.user_id, r.product_id, r.review_title, " +
                "r.review_content, r.create_id, r.create_time, r.update_id, r.update_time, c.username " +
                "FROM review r " +
                "JOIN account c ON r.user_id = c.id " +
                "WHERE r.product_id = ?";
        return template.query(sql, (rs, rowNum) -> ReviewDTO.builder()
                .reviewId(rs.getLong("review_id"))
                .userId(rs.getLong("user_id"))
                .productId(rs.getLong("product_id"))
                .reviewTitle(rs.getString("review_title"))
                .reviewContent(rs.getString("review_content"))
                .username(rs.getString("username"))  // 추가된 필드
                .createId(rs.getLong("create_id"))
                .createTime(rs.getTimestamp("create_time"))
                .updateId(rs.getLong("update_id"))
                .updateTime(rs.getTimestamp("update_time"))
                .build(), productId);
    }


    private RowMapper<Review> reviewRowMapper = (rs, rowNum) -> {
        Review review = new Review();
        review.setReviewId(rs.getLong("review_id"));
        review.setUserId(rs.getLong("user_id"));
        review.setProductId(rs.getLong("product_id"));
        review.setReviewTitle(rs.getString("review_title"));
        review.setReviewContent(rs.getString("review_content"));
        //review.setReviewImg(rs.getBytes("review_img"));
        review.setCreateId(rs.getLong("create_id"));
        review.setCreateTime(rs.getDate("create_time"));
        review.setUpdateId(rs.getLong("update_id"));
        review.setUpdateTime(rs.getDate("update_time"));
        return review;
    };
}
