package com.example.db.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
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
