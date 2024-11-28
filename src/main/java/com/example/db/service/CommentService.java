package com.example.db.service;

import com.example.db.dto.CommentDTO;
import com.example.db.entity.Comment;
import com.example.db.jdbc.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
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

    @Transactional
    public Comment createComment(CommentDTO commentdto) {
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
        Comment returnComment = new Comment();
        try{
            returnComment= commentRepository.createComment(comment);
        }catch (Exception e){
            log.error("database insertion error");
            e.printStackTrace();
        }
        return returnComment;
    }

    @Transactional
    public Comment updateComment(CommentDTO commentDTO) {
        Comment comment = Comment.builder()
                .commentId(commentDTO.getCommentId())
                .userId(commentDTO.getUserId())
                .productId(commentDTO.getProductId())
                .reviewId(commentDTO.getReviewId())
                .commentContent(commentDTO.getCommentContent())
                .createId(commentDTO.getUserId())
                .updateId(commentDTO.getUserId())
                .createTime(null)
                .updateTime(new Date())
                .build();
        Comment returnComment = new Comment();
        try{
            returnComment = commentRepository.updateComment(comment);
        }catch (Exception e){
            log.error("comment update failed");
            e.printStackTrace();
        }
        return returnComment;
    }

    public void deleteComment(Long commentId) {
        int rowsAffected = -1;
        try{
            rowsAffected = commentRepository.deleteComment(commentId);
        }catch (Exception e){
            log.error("delete comment is failed");
            e.printStackTrace();
        }
        if (rowsAffected == 0) {
            throw new RuntimeException("comment delete error! -> affectedRow is zero");
        }
    }
}
