package com.example.db.service;

import com.example.db.dto.CommentDTO;
import com.example.db.entity.Comment;
import com.example.db.jdbc.CommentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Long getCommentId(CommentDTO commentDTO){
        Comment comment = Comment.builder()
                .commentId(commentDTO.getCommentId())
                .userId(commentDTO.getUserId())
                .productId(commentDTO.getProductId())
                .reviewId(commentDTO.getReviewId())
                .commentContent(commentDTO.getCommentContent())
                .createId(null)
                .updateId(commentDTO.getUserId())
                .createTime(null)
                .updateTime(new Date())
                .build();
        return commentRepository.getCommentId(comment);
    }

    public CommentDTO createComment(CommentDTO commentdto) {
        Comment comment = Comment.builder()
                .commentId(commentdto.getCommentId())
                .userId(commentdto.getUserId())
                .productId(commentdto.getProductId())
                .reviewId(commentdto.getReviewId())
                .commentContent(commentdto.getCommentContent())
                .createId(commentdto.getUserId())
                .updateId(commentdto.getUserId())
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        return commentRepository.createComment(comment);
    }

    public CommentDTO updateComment(CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .commentId(commentDTO.getCommentId())
                .userId(commentDTO.getUserId())
                .productId(commentDTO.getProductId())
                .reviewId(commentDTO.getReviewId())
                .commentContent(commentDTO.getCommentContent())
                .createId(null)
                .updateId(commentDTO.getUserId())
                .createTime(null)
                .updateTime(new Date())
                .build();
        return commentRepository.updateComment(comment);
    }

    public void deleteComment(Long commentId, Long id) {
        int rowsAffected = commentRepository.deleteComment(commentId, id);
        if (rowsAffected == 0) {
            throw new RuntimeException("comment delete error! -> affectedRow is zero");
        }
    }
}
