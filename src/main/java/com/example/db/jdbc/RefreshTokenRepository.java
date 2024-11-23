package com.example.db.jdbc;

import com.example.db.entity.Account;
import com.example.db.entity.RefreshToken;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.Optional;

@Repository
public class RefreshTokenRepository {

    private final JdbcTemplate template;

    public RefreshTokenRepository(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    public RefreshToken saveOrUpdate(RefreshToken token) {
        Optional<RefreshToken> existingToken = findByLoginId(token.getLoginId());

        if (existingToken.isPresent()) {
            // 기존에 존재하면 업데이트
            update(token);
            token.setId(existingToken.get().getId()); // 기존 ID를 유지
        } else {
            // 존재하지 않으면 새로 삽입
            save(token);
        }
        return token;
    }

    public RefreshToken save(RefreshToken token){
        String sql = "insert into refresh_token (login_id, token, expiry_date) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, token.getLoginId());
            ps.setString(2, token.getToken());
            ps.setDate(3, new java.sql.Date(token.getExpiryDate().getTime()));
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        token.setId(key);
        return token;
    }

    public void deleteByUserId(Long loginId){
        String sql = "delete from refresh_token where login_id = ?";
        template.update(sql, loginId);
    }

    /*
    public void update(RefreshToken token) {
        String sql = "update refresh_token set token = ?, expiry_date = ? where user_id = ?";
        template.update(sql, token.getToken(), new java.sql.Date(token.getExpiryDate().getTime()), token.getLoginId());
    }
    */
    public void update(RefreshToken token) {
        String sql = "update refresh_token set token = ?, expiry_date = ? where login_id = ?";
        template.update(sql, token.getToken(), new java.sql.Date(token.getExpiryDate().getTime()), token.getLoginId());
    }

    public Optional<RefreshToken> findByLoginId(Long loginId){
        String sql = "select id, login_id, token, expiry_date from refresh_token where login_id = ?";
        try {
            RefreshToken refreshToken = template.queryForObject(sql, refreshTokenRowMapper(), loginId);
            return Optional.of(refreshToken);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    // RowMapper를 사용하여 결과를 RefreshToken 객체로 매핑
    /*
    private RowMapper<RefreshToken> refreshTokenRowMapper() {
        return new RowMapper<RefreshToken>() {
            @Override
            public RefreshToken mapRow(ResultSet rs, int rowNum) throws SQLException {
                RefreshToken token = new RefreshToken();
                token.setId(rs.getLong("id"));
                token.setLoginId(rs.getLong("login_id"));
                token.setToken(rs.getString("token"));
                token.setExpiryDate(rs.getDate("expiry_date"));
                return token;
            }
        };
    }
    */

    private RowMapper<RefreshToken> refreshTokenRowMapper(){
        return (rs, rowNum) -> {
            RefreshToken token = new RefreshToken();
            token.setId(rs.getLong("id"));
            token.setLoginId(rs.getLong("login_id"));
            token.setToken(rs.getString("token"));
            token.setExpiryDate(rs.getDate("expiry_date"));
            return token;
        };
    }




}
