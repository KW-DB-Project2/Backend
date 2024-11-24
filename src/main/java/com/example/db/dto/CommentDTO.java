package com.example.db.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTO {
    private Long commentId;
    private Long userId;
    private Long productId;
    private Long reviewId;
    private String commentContent;
}
