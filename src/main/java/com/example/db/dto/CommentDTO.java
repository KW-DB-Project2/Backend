package com.example.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long commentId;
    private Long userId;
    private Long productId;
    private Long reviewId;
    private String commentContent;
}
