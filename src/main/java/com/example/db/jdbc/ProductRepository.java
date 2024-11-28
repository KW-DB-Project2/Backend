package com.example.db.jdbc;

import com.example.db.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
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

    public Product save(Product product) {
        final String sql = "INSERT INTO product (user_id, product_title, product_content, product_price, product_status, product_img,create_id,create_time,update_id,update_time) " +
                "VALUES (?, ?, ?, ?, ?, ?,?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Timestamp createTime = new Timestamp(product.getCreateTime().getTime());  // getTime()으로 밀리초를 얻어 java.sql.Date로 변환
        Timestamp updateTime = new Timestamp(product.getUpdateTime().getTime());
        jdbcTemplate.update(con->{
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"product_id"});
            ps.setLong(1,product.getUserId());
            ps.setString(2, product.getProductTitle());
            ps.setString(3, product.getProductContent());
            ps.setLong(4, product.getProductPrice());
            ps.setBoolean(5, product.isProductStatus());
            ps.setBytes(6, product.getProductImg());
            ps.setLong(7,product.getUserId());
            ps.setTimestamp(8, createTime);
            ps.setLong(9,product.getUserId());
            ps.setTimestamp(10, updateTime);
            return ps;
        },keyHolder);

        Long generatedProductId = keyHolder.getKey().longValue();
        String selectSql = "select * from product where product_id = ?";
        return jdbcTemplate.queryForObject(selectSql, new BeanPropertyRowMapper<>(Product.class),generatedProductId);
    }

    public Product update(Product product) {
        String sql = "UPDATE product SET user_id = ?, product_title = ?, product_content = ?, " +
                "product_price = ?, product_status = ?, product_img = ?, update_id =?, update_time = ? WHERE product_id = ?";
        jdbcTemplate.update(sql,
                product.getUserId(),
                product.getProductTitle(),
                product.getProductContent(),
                product.getProductPrice(),
                product.isProductStatus(),
                product.getProductImg(),
                product.getUpdateId(),
                product.getUpdateTime(),
                product.getProductId());
        String returnSql = "select * from product where product_id =?";
        return jdbcTemplate.queryForObject(returnSql,new BeanPropertyRowMapper<>(Product.class), product.getProductId());
    }

    public int deleteProduct(Long productId){
        String sql = "DELETE FROM product WHERE product_id = ?";
        return jdbcTemplate.update(sql,productId);
    }
}
