package com.example.db.service;

import com.example.db.dto.MonthlyTransactionData;
import com.example.db.entity.Account;
import com.example.db.entity.Product;
import com.example.db.entity.Review;
import com.example.db.enums.UserRole;
import com.example.db.jdbc.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final ReportRepository reportRepository;
    private final MemberRepository memberRepository;
    private final TransactionRepository transactionRepository;

    private void validateAdminRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("Not authenticated");
        }
        Long id;
        try{
            id = (Long) authentication.getPrincipal(); // Principal에서 loginId 추출
            Optional<Account> optionalAccount = memberRepository.findById(id);
            if (optionalAccount.isPresent()) {
                Account account = optionalAccount.get();
                if(account.getRole() != UserRole.ADMIN) {
                    throw new IllegalArgumentException("Not admin");
                }
            }
        }catch (Exception e) {
            throw new IllegalArgumentException("Not admin");
        }
    }

    public List<Product> getAllProducts() {
        validateAdminRole();
        return productRepository.findAllProducts();
    }

    //상품 삭제
    @Transactional
    public boolean deleteProduct(Long productId) {
        validateAdminRole();
        int rowsAffected = productRepository.deleteProduct(productId);
        return rowsAffected > 0;
    }

    public List<Review> getAllReviews() {
        validateAdminRole();
        return reviewRepository.getAllReviews();
    }

    @Transactional
    public boolean deleteReview(Long reviewId) {
        validateAdminRole();
        int rowsAffected = reviewRepository.deleteReview(reviewId);   //새로 delete하는 함수 적용(admin의 경우 userId 필요없음)
        return rowsAffected > 0;
    }

    public List<Object> getAllReports() {
        validateAdminRole();
        List<Object> reports = new ArrayList<>();
        reports.add(reportRepository.getAllProductReport());
        reports.add(reportRepository.getAllReviewReport());
        return reports;
    }

    public List<Account> getAllUsers() {
        validateAdminRole();
        return memberRepository.findAllAccount();
    }

    @Transactional
    public void suspendUser(Long userId) {
        validateAdminRole();
        memberRepository.updateRole(userId, UserRole.BAN);
    }

    @Transactional
    public void lowerRank(Long userId){
        validateAdminRole();
        Optional<Account> byId = memberRepository.findById(userId);
        if(byId.isPresent()){
            Account account = byId.get();
            if(account.getRole() == UserRole.ADMIN) {
                memberRepository.updateRole(userId, UserRole.USER);
            }else{
                throw new IllegalStateException("Current user is NOT ADMIN");
            }
        }else {
            throw new IllegalArgumentException("Current user does not exist");
        }
    }

    @Transactional
    public void upgradeAdmin(Long userId){
        validateAdminRole();
        Optional<Account> byId = memberRepository.findById(userId);
        if(byId.isPresent()){
            Account account = byId.get();
            if(account.getRole() != UserRole.ADMIN) {
                memberRepository.updateRole(userId, UserRole.ADMIN);
            }else{
                throw new IllegalStateException("Current user is already ADMIN");
            }
        }else {
            throw new IllegalArgumentException("Current user does not exist");
        }
    }

    @Transactional
    public void deleteById(Long id) {
        validateAdminRole();
        Optional<Account> optionalAccount = memberRepository.findById(id);
        if(optionalAccount.isEmpty()){
            throw new IllegalStateException("Account not found");
        }else if(optionalAccount.get().getRole() != UserRole.ADMIN){
            throw new IllegalStateException("Current user is not ADMIN");
        }
        memberRepository.deleteById(id);
    }

    public List<MonthlyTransactionData> getMonthlyTransactionData() {
        validateAdminRole();
        return transactionRepository.getMonthlyTransactions();
    }

}
