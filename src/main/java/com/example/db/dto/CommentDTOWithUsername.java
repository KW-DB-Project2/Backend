package com.example.db.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTOWithUsername {

    private Long commentId;
    private Long userId;
    private Long productId;
    private Long reviewId;
    private String username;
    private String commentContent;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
}
