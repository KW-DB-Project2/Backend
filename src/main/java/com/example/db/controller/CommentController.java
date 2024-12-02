package com.example.db.controller;

import com.example.db.dto.CommentDTO;
import com.example.db.dto.CommentDTOWithUsername;
import com.example.db.entity.Comment;
import com.example.db.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> updateComment(@PathVariable("commentId") Long commentId,@RequestBody CommentDTO commentDTO) {
        try{
            commentDTO.setCommentId(commentId);
            return ResponseEntity.ok(commentService.updateComment(commentDTO));
        }catch (IllegalStateException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }
    @GetMapping("/commentId")
    public Long getCommentId(@RequestBody CommentDTO commentDTO){
        return commentService.getCommentId(commentDTO);
    }

    @GetMapping("/{reviewId}")
    public List<CommentDTOWithUsername> getComment(@PathVariable("reviewId") Long reviewId) {
        return commentService.getAllComments(reviewId);
    }

    @GetMapping("/detail/{commentId}")
    public CommentDTOWithUsername getCommentDetail(@PathVariable("commentId") Long commentId) {
        return commentService.getCommentById(commentId);
    }

    /*
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId, Authentication authentication) {
        try {
            Long id = (Long)authentication.getPrincipal();
            commentService.deleteComment(commentId, id);
            return "Successfully comment deleted";
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }*/

    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId, @RequestParam("userId") Long userId) {
        try {
            //Long id = (Long)authentication.getPrincipal();
            commentService.deleteComment(commentId, userId);
            return "Successfully comment deleted";
        } catch (RuntimeException e){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }



}
