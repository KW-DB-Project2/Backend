package com.example.db.service;

import com.example.db.dto.AskDTO;
import com.example.db.dto.AskDTOWithUsername;
import com.example.db.entity.Ask;
import com.example.db.jdbc.AskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AskService {
    private final AskRepository askRepository;

    public List<AskDTOWithUsername> getAllAsk(Long productId){
       return askRepository.getAllAskWithName(productId);
    }

    public AskDTOWithUsername getAsk(Long askId){
        return askRepository.getAskById(askId);
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
