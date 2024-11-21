package com.example.db.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
public class Comment {

    private Long commentId;
    private Long userId;
    private Long productId;
    private Long reviewId;
    private String commentContent;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
}
