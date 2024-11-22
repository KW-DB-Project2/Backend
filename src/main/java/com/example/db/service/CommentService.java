package com.example.db.service;

import com.example.db.entity.Comment;
import com.example.db.jdbc.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public void createComment(Comment comment) {
        commentRepository.createComment(comment);
    }

    public void updateComment(Comment comment) {
        int rowsAffected = commentRepository.updateComment(comment);
        if (rowsAffected == 0) {
            throw new RuntimeException("You are not authorized to update comment Or There is no change");
        }
    }

    public void deleteComment(Long commentId, Long userId) {
        int rowsAffected = commentRepository.deleteComment(commentId, userId);
        if (rowsAffected == 0) {
            throw new RuntimeException("You are not authorized to delete comment Or There is no change");
        }
    }
}
