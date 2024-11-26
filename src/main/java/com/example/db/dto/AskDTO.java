package com.example.db.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AskDTO {
    private Long askId;
    private Long userId;
    private Long productId;
    private String askContent;
}
