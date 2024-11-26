package com.example.db.controller;

import com.example.db.dto.AskDTO;
import com.example.db.entity.Ask;
import com.example.db.entity.Product;
import com.example.db.service.AskService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ask")
@AllArgsConstructor
public class AskController {
    private final AskService askService;

    @GetMapping("/{productId}")
    public List<Ask> getAllAsk(@PathVariable Long productId){
        return askService.getAllAsk(productId);
    }

    @PostMapping
    public Ask addAsk(@RequestBody AskDTO askDTO){
        return askService.addAsk(askDTO);
    }

    @DeleteMapping("/{askId}")
    public String deleteAsk(@PathVariable Long askId, @RequestBody AskDTO askDTO){
        try{
            askService.deleteAsk(askId, askDTO);
        }catch(Exception e){
            return "ask delete failed";
        }
        return "delete success";
    }

}
