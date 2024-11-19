package com.example.db.controller;

import com.example.db.entity.Product;
import com.example.db.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return ResponseEntity.ok("Product added successfully");
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam("query") String query) {
        List<Product> searchResults = productService.searchProducts(query);
        return ResponseEntity.ok(searchResults);
    }

    /*
    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody Product product, Authentication authentication) {
        //인증된 사용자 loginId인지 확인
        Long loginId = Long.parseLong(authentication.getName());

        //productId에 userId 검증 추가
        if(productService.isProductOwnedByUser(productId, loginId)) {

        }


    }
    */



}