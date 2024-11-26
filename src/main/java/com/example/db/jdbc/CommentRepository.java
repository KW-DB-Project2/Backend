package com.example.db.jdbc;

import com.example.db.dto.CommentDTO;
import com.example.db.entity.Comment;
import com.example.db.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Slf4j
@Repository
public class CommentRepository {

    private final JdbcTemplate template;

    public CommentRepository(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    public Long getCommentId(Comment comment){
        final String sql = "select comment_id from comment where product_id =? AND review_id = ? AND user_id = ?";
        Comment includedIdComment = template.queryForObject(sql, new BeanPropertyRowMapper<>(Comment.class), comment.getProductId(),comment.getReviewId(),comment.getUserId());
        return includedIdComment.getCommentId();
    }

    public Comment createComment(Comment comment) {
        final String sql = "INSERT INTO comment (user_id, product_id, review_id, comment_content, create_id, create_time, update_id,update_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();//자동 생성된 key 얻기 위한 변수
        Timestamp createTime = new Timestamp(comment.getCreateTime().getTime());
        Timestamp updateTime = new Timestamp(comment.getUpdateTime().getTime());
        template.update(con ->{
            PreparedStatement ps = con.prepareStatement(sql, new String[] {"comment_id"});
            ps.setLong(1, comment.getUserId());
            ps.setLong(2, comment.getProductId());
            ps.setLong(3, comment.getReviewId());
            ps.setString(4, comment.getCommentContent());
            ps.setLong(5, comment.getUserId());
            ps.setTimestamp(6, createTime);
            ps.setLong(7, comment.getUserId());
            ps.setTimestamp(8, updateTime);
            return ps;
            }, keyHolder);

        Long generatedCommentId = keyHolder.getKey().longValue();
        String selectSql = "SELECT * FROM comment WHERE comment_id = ?";
        Comment createdComment = template.queryForObject(selectSql, new BeanPropertyRowMapper<>(Comment.class), generatedCommentId);

//        CommentDTO commentDTO = CommentDTO.builder()
//                .commentId(createdComment.getCommentId())
//                .userId(createdComment.getUserId())
//                .productId(createdComment.getProductId())
//                .reviewId(createdComment.getReviewId())
//                .commentContent(createdComment.getCommentContent())
//                .build();
        return createdComment;
    }

    public Comment updateComment(Comment comment) {
        try{
            String sql = "UPDATE comment SET comment_content = ?, update_time = ?, update_id=? WHERE user_id = ? AND product_id =?";
            template.update(sql, comment.getCommentContent(), comment.getUpdateTime(),comment.getUpdateId(),comment.getUserId(),comment.getProductId());
            sql = "SELECT * from comment where product_id = ? AND review_id=?";
            Comment updatedComment = template.queryForObject(sql, new BeanPropertyRowMapper<>(Comment.class), comment.getProductId(),comment.getReviewId());
//            CommentDTO commentDTO = CommentDTO.builder()
//                    .commentId(updatedComment.getCommentId())
//                    .userId(updatedComment.getUserId())
//                    .productId(updatedComment.getProductId())
//                    .reviewId(updatedComment.getReviewId())
//                    .commentContent(updatedComment.getCommentContent())
//                    .build();
            return updatedComment;
        }catch (DataAccessException e) {
            log.error("Database error: {}", e.getMessage(), e);
            throw new RuntimeException("데이터베이스 처리 중 오류가 발생했습니다.");
        }
    }

    public int deleteComment(Long commentId, Long id) {
        String sql = "DELETE FROM comment WHERE comment_id = ? AND user_id = ?";
        return template.update(sql, commentId, id);
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
