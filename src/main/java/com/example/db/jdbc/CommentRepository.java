package com.example.db.jdbc;

import com.example.db.entity.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class CommentRepository {

    private final JdbcTemplate template;

    public CommentRepository(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    public int createComment(Comment comment) {
        String sql = "INSERT INTO comment (user_id, product_id, review_id, comment_content, create_time) VALUES (?, ?, ?, ?, NOW())";
        return template.update(sql, comment.getUserId(), comment.getProductId(), comment.getReviewId(), comment.getCommentContent());
    }

    public int updateComment(Comment comment) {
        String sql = "UPDATE comment SET comment_content = ?, update_time = NOW() WHERE comment_id = ? AND user_id = ?";
        return template.update(sql, comment.getCommentContent(), comment.getCommentId(), comment.getUserId());
    }

    public int deleteComment(Long commentId, Long userId) {
        String sql = "DELETE FROM comment WHERE comment_id = ? AND user_id = ?";
        return template.update(sql, commentId, userId);
    }

    private RowMapper<Comment> commentRowMapper = (rs, rowNum) -> {
        Comment comment = new Comment();
        comment.setCommentId(rs.getLong("comment_id"));
        comment.setUserId(rs.getLong("user_id"));
        comment.setProductId(rs.getLong("product_id"));
        comment.setReviewId(rs.getLong("review_id"));
        comment.setCommentContent(rs.getString("comment_content"));
        comment.setCreateTime(rs.getDate("create_time"));
        comment.setUpdateTime(rs.getDate("update_time"));
        return comment;
    };
}
