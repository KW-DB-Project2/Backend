package com.example.db.service;

import com.example.db.dto.ProductDTO;
import com.example.db.entity.Product;
import com.example.db.jdbc.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> searchProducts(String query) {
        return productRepository.findByProductTitleContainingIgnoreCase(query);
    }

    @Transactional
    public Product addProduct(ProductDTO productDTO) {
        Product product = Product.builder()
                .userId(productDTO.getUserId())
                .productTitle(productDTO.getProductTitle())
                //.productClass(productDTO.getProductClass())
                .productContent(productDTO.getProductContent())
                .productPrice(productDTO.getProductPrice())
                .productStatus(productDTO.isProductStatus())
                .productImg(productDTO.getProductImg())
                .createId(productDTO.getUserId())
                .createTime(new Date())
                .updateId(productDTO.getUserId())
                .updateTime(new Date())
                .build();
        return productRepository.save(product);
    }
    public List<Product> getProductId(Product product){
        return productRepository.findProductId(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAllProducts();
    }

    public List<Product> getAllProductsForMyPage() {
        return productRepository.findAllProductsWithoutStatus();
    }

    @Transactional
    public Product updateProduct(ProductDTO productDTO, Long productId){
        Product product = Product.builder()
                .productId(productId)
                .userId(productDTO.getUserId())
                .productTitle(productDTO.getProductTitle())
                .productContent(productDTO.getProductContent())
                .productPrice(productDTO.getProductPrice())
                .productStatus(productDTO.isProductStatus())
                .productImg(productDTO.getProductImg())
                .createId(productDTO.getUserId())
                .createTime(new Date())
                .updateId(productDTO.getUserId())
                .updateTime(new Date())
                .build();
        return productRepository.update(product);
    }

    @Transactional
    public boolean deleteProduct(Long productId) {
        int deleteRows = productRepository.deleteProduct(productId);
        return deleteRows > 0;
    }

    //해당 user의 id와 product의 userId와 비교하여 맞으면 true, 아니면 false
    public boolean isProductOwnedByUser(Long productId, Long userId){
        Product product = productRepository.findById(productId);
        return product != null && product.getUserId().equals(userId);
    }

}
