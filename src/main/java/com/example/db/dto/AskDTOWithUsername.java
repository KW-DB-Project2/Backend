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
public class AskDTOWithUsername {

    private Long askId;
    private Long userId;
    private Long productId;
    private String username;
    private String askContent;
    private Long createId;
    private Date createTime;
    private Long updateId;
    private Date updateTime;
}
