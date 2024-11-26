package com.example.db.service;

import com.example.db.dto.AskDTO;
import com.example.db.entity.Ask;
import com.example.db.jdbc.AskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AskService {
    private final AskRepository askRepository;

    public List<Ask> getAllAsk(Long productId){
       return askRepository.getAllAsk(productId);
    }


    public Ask addAsk(AskDTO askDTO){
        Ask ask = Ask.builder()
                .userId(askDTO.getUserId())
                .productId(askDTO.getProductId())
                .askContent(askDTO.getAskContent())
                .build();
        return askRepository.insertAsk(ask);
    }

    public void deleteAsk(Long askId, AskDTO askDTO){
        askRepository.deleteAsk(askId, askDTO);
    }
}
