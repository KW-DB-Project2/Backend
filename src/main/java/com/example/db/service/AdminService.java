package com.example.db.service;

import com.example.db.dto.MonthlyTransactionData;
import com.example.db.entity.Account;
import com.example.db.entity.Product;
import com.example.db.entity.Review;
import com.example.db.enums.UserRole;
import com.example.db.jdbc.*;
import lombok.RequiredArgsConstructor;
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

    public List<Product> getAllProducts() {
        return productRepository.findAllProducts();
    }

    //상품 삭제
    @Transactional
    public boolean deleteProduct(Long productId) {
        int rowsAffected = productRepository.deleteProduct(productId);
        return rowsAffected > 0;
    }

    public List<Review> getAllReviews() {
        return reviewRepository.getAllReviews();
    }

    @Transactional
    public boolean deleteReview(Long reviewId) {
        int rowsAffected = reviewRepository.deleteReview(reviewId);   //새로 delete하는 함수 적용(admin의 경우 userId 필요없음)
        return rowsAffected > 0;
    }

    public List<Object> getAllReports() {
        List<Object> reports = new ArrayList<>();
        reports.add(reportRepository.getAllProductReport());
        reports.add(reportRepository.getAllReviewReport());
        return reports;
    }

    public List<Account> getAllUsers() {
        return memberRepository.findAllAccount();
    }

    @Transactional
    public void suspendUser(Long userId) {
        memberRepository.updateRole(userId, UserRole.BAN);
    }

    @Transactional
    public void lowerRank(Long userId){
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
    public void deleteById(Long id) {
        Optional<Account> optionalAccount = memberRepository.findById(id);
        if(optionalAccount.isEmpty()){
            throw new IllegalStateException("Account not found");
        }
        memberRepository.deleteById(id);
    }

    public List<MonthlyTransactionData> getMonthlyTransactionData() {
        return transactionRepository.getMonthlyTransactions();
    }

}
