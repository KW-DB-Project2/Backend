package com.example.db.controller;

import com.example.db.dto.CommentDTO;
import com.example.db.entity.Comment;
import com.example.db.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public Comment createComment(@RequestBody CommentDTO commentdto) {
        return commentService.createComment(commentdto);
    }

    @PutMapping("/{commentId}")
    public Comment updateComment(@PathVariable Long commentId,@RequestBody CommentDTO commentDTO) {
        commentDTO.setCommentId(commentId);
        return commentService.updateComment(commentDTO);
    }
    @GetMapping("/commentId")
    public Long getCommentId(@RequestBody CommentDTO commentDTO){
        return commentService.getCommentId(commentDTO);
    }

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable Long commentId, Authentication authentication) {
        try {
            Long id = (Long)authentication.getPrincipal();
            commentService.deleteComment(commentId, id);
            return "Successfully comment deleted";
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
