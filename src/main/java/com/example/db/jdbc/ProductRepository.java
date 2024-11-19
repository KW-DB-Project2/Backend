package com.example.db.jdbc;

import com.example.db.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findByProductTitleContainingIgnoreCase(String title) {
        String sql = "SELECT * FROM products WHERE LOWER(product_title) LIKE LOWER(?)";
        String searchPattern = "%" + title + "%";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class), searchPattern);
    }

    public List<Product> findAllProducts() {
        String sql = "SELECT * FROM products WHERE PRODUCT_STATUS = 1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public Product findById(Long productId){
        String sql = "SELECT * FROM products WHERE PRODUCT_ID = ?";
        try{
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), productId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int save(Product product) {
        String sql = "INSERT INTO products (user_id, product_title, product_class, product_content, product_price, product_status, product_img) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql,
                product.getUserId(),
                product.getProductTitle(),
                product.getProductClass(),
                product.getProductContent(),
                product.getProductPrice(),
                product.isProductStatus(),
                product.getProductImg());
    }

    public int update(Product product) {
        String sql = "UPDATE products SET user_id = ?, product_title = ?, product_class = ?, product_content = ?, " +
                "product_price = ?, product_status = ?, product_img = ? WHERE product_id = ?";
        return jdbcTemplate.update(sql,
                product.getUserId(),
                product.getProductTitle(),
                product.getProductClass(),
                product.getProductContent(),
                product.getProductPrice(),
                product.isProductStatus(),
                product.getProductImg(),
                product.getProductId());
    }

    public int deleteProduct(Long productId){
        String sql = "DELETE FROM products WHERE product_id = ?";
        return jdbcTemplate.update(sql,productId);
    }
}