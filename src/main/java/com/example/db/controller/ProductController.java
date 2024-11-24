package com.example.db.controller;

import com.example.db.entity.Product;
import com.example.db.service.ProductService;
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

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        productService.addProduct(product);
        product.setProductId(productService.getProductId(product).getFirst().getProductId());
        return ResponseEntity.ok(product);
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

    @PutMapping("/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId, @RequestBody Product product, Authentication authentication) {
        //productId에 userId 검증 추가
        return ResponseEntity.ok(productService.updateProduct(product));

//        try{
//            Long userId = (Long)authentication.getPrincipal();
//            if(productService.isProductOwnedByUser(productId,userId)){
//                return ResponseEntity.ok(productService.updateProduct(product));
//            }
//
//        }catch(Exception e){
//            log.error("product's userId is not match user's id");
//        }
//        return ResponseEntity.ok("update not complete");
    }
    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId,Authentication authentication){
        //Long id = (Long)authentication.getPrincipal();
        productService.deleteProduct(productId);
//        if(productService.isProductOwnedByUser(productId,id)){
//            productService.deleteProduct(productId);
//        }
        return ResponseEntity.ok("delete complete successfully!");
    }


}
