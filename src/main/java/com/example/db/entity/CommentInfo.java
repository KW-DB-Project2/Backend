package com.example.db.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentInfo {

    private Long commentId;
    private Long loginId;
    private Long productId;
    private Long reviewId;
    private String commentContent;
}
