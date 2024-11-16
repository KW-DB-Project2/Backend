package com.example.db.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AskInfo {

    private Long askId;
    private Long loginId;
    private Long productId;
    private String askContent;
}
