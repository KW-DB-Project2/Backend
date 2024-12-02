package com.example.db.service;

import com.example.db.dto.CommentDTO;
import com.example.db.dto.CommentDTOWithUsername;
import com.example.db.entity.Comment;
import com.example.db.jdbc.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

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
        try{
            return commentRepository.updateComment(comment);
        }catch (Exception e){
            log.error("comment update failed: {}", e.getMessage());
            throw new IllegalStateException("코멘트 업데이트 중 오류 발생: " + e.getMessage(), e);
        }
    }

    public List<CommentDTOWithUsername> getAllComments(Long reviewId){
        return commentRepository.getAllCommentsWithReviewId(reviewId);
    }

    public CommentDTOWithUsername getCommentById(Long commentId) {
        return commentRepository.getCommentWithUsername(commentId);
    }

    public void deleteComment(Long commentId, Long id) {
        int rowsAffected = commentRepository.deleteComment(commentId, id);
        if (rowsAffected == 0) {
            throw new RuntimeException("comment delete error! -> affectedRow is zero");
        }
    }
}
