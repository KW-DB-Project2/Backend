package com.example.db.controller;

import com.example.db.dto.ProductDTO;
import com.example.db.dto.minMaxAvgDTO;
import com.example.db.entity.Product;
import com.example.db.entity.Transaction;
import com.example.db.service.ProductService;
import com.example.db.service.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final TransactionService transactionService;

    @Autowired
    public ProductController(ProductService productService, TransactionService transactionService) {
        this.productService = productService;
        this.transactionService = transactionService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.addProduct(productDTO));
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/mypage")
    public ResponseEntity<List<Product>> getMyPageProducts() {
        return ResponseEntity.ok(productService.getAllProductsForMyPage());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("query") String query) {
        List<Product> searchResults = productService.searchProducts(query);
        return ResponseEntity.ok(searchResults);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO productDTO, Authentication authentication) {
        return ResponseEntity.ok(productService.updateProduct(productDTO,productId));
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId,Authentication authentication){
        productService.deleteProduct(productId);
        return ResponseEntity.ok("delete complete successfully!");
    }

    @PutMapping("/buy/{productId}")
    public ResponseEntity<?> buyProduct(@PathVariable Long productId, @RequestBody Transaction transaction){
        try{
            productService.buyProduct(productId);
            transactionService.saveTransaction(transaction);
        }catch(Exception e){
            log.error("product/buy error");
            e.printStackTrace();
        }
        return ResponseEntity.ok("product_status changed 0");
    }

    @GetMapping("/order")
    public ResponseEntity<?> getProductByOrder(@RequestParam String descOrAsc){
        log.info(descOrAsc);
        return ResponseEntity.ok(productService.getProductByOrder(descOrAsc));
    }

    @GetMapping("/minMaxAvg")
    public ResponseEntity<minMaxAvgDTO> getMinMaxAvgPrice(){
        return ResponseEntity.ok(productService.getMinMaxAvgPrice());
    }


}
