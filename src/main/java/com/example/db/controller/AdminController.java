package com.example.db.controller;

import com.example.db.dto.MonthlyTransactionData;
import com.example.db.entity.Account;
import com.example.db.entity.Product;
import com.example.db.entity.Review;
import com.example.db.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/products")
    public ResponseEntity<?> getRegisteredProducts() {
        List<Product> allProducts = adminService.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") Long productId) {
        boolean deleteProduct = adminService.deleteProduct(productId);
        if (deleteProduct) {
            return ResponseEntity.ok("Product deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    @GetMapping("/reviews")
    public ResponseEntity<?> getAllReviews() {
        List<Review> allReviews = adminService.getAllReviews();
        return ResponseEntity.ok(allReviews);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable("reviewId") Long reviewId) {
        boolean deleteReview = adminService.deleteReview(reviewId);
        if (deleteReview) {
            return ResponseEntity.ok("Review deleted successfully");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review not found");
    }

    @GetMapping("/reports")
    public ResponseEntity<?> getAllReports() {
        List<Object> allReports = adminService.getAllReports();
        return ResponseEntity.ok(allReports);
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<Account> allUsers = adminService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @PutMapping("/users/{userId}/suspend")
    public ResponseEntity<?> suspendUser(@PathVariable("userId") Long userId) {
        try{
            adminService.suspendUser(userId);
            return ResponseEntity.ok("User suspended successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/transactions/monthly")
    public ResponseEntity<?> getMonthlyTransactionsPrice() {
        List<MonthlyTransactionData> monthlyTransactionData = adminService.getMonthlyTransactionData();
        return ResponseEntity.ok(monthlyTransactionData);
    }




}
