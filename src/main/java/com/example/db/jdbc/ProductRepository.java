package com.example.db.jdbc;

import com.example.db.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findByProductTitleContainingIgnoreCase(String title) {
        String sql = "SELECT * FROM product WHERE LOWER(product_title) LIKE LOWER(?)";
        String searchPattern = "%" + title + "%";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class), searchPattern);
    }

    public List<Product> findAllProducts() {
        String sql = "SELECT * FROM product WHERE PRODUCT_STATUS = 1";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public Product findById(Long productId){
        String sql = "SELECT * FROM product WHERE PRODUCT_ID = ?";
        try{
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), productId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Product> findProductId(Product product){
        String sql = "select product_id from product where product_title = ?";
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Product.class),product.getProductTitle());
    }

    public int save(Product product) {
        String sql = "INSERT INTO product (user_id, product_title, product_class, product_content, product_price, product_status, product_img,create_id,create_time,update_id,update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?,?,?,?,?)";
        Date createTime = new Date(product.getCreateTime().getTime());  // getTime()으로 밀리초를 얻어 java.sql.Date로 변환
        Date updateTime = new Date(product.getUpdateTime().getTime());
        return jdbcTemplate.update(sql,
                product.getUserId(),
                product.getProductTitle(),
                product.getProductClass(),
                product.getProductContent(),
                product.getProductPrice(),
                product.isProductStatus(),
                product.getProductImg(),
                product.getCreateId(),
                createTime,
                product.getUpdateId(),
                updateTime);
    }

    public int update(Product product) {
        String sql = "UPDATE product SET user_id = ?, product_title = ?, product_class = ?, product_content = ?, " +
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
        String sql = "DELETE FROM product WHERE product_id = ?";
        return jdbcTemplate.update(sql,productId);
    }
}
