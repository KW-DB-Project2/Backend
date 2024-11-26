package com.example.db.service;

import com.example.db.entity.Product;
import com.example.db.jdbc.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    public int addProduct(Product product) {
        return productRepository.save(product);
    }
    public List<Product> getProductId(Product product){
        return productRepository.findProductId(product);
    }

    public List<Product> getProducts() {
        return productRepository.findAllProducts();
    }

    public List<Product> updateProduct(Product product, Long productId){
        return productRepository.update(product,productId);
    }


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
