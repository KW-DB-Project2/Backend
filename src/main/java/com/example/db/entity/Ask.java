package com.example.db.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ask {

    private Long askId;
    private Long userId;
    private Long productId;
    private String askContent;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;

}
