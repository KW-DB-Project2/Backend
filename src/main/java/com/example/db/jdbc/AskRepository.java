package com.example.db.jdbc;

import com.example.db.dto.AskDTO;
import com.example.db.dto.AskDTOWithUsername;
import com.example.db.entity.Ask;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class AskRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Ask> getAllAsk(Long productId){
        String sql = "select * from ask where product_id = ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Ask.class),productId);
    }

    public List<AskDTOWithUsername> getAllAskWithName(Long productId){
        String sql = "SELECT a.ask_id, a.user_id, a.product_id, u.username, a.ask_content, " +
                    "a.create_id, a.create_time, a.update_id, a.update_time " +
                    "FROM ask a " +
                    "JOIN account u ON a.user_id = u.id " +
                    "WHERE a.product_id = ?";

        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(AskDTOWithUsername.class),productId);
    }

    public AskDTOWithUsername getAskById(Long askId){
        String sql = "SELECT a.ask_id, a.user_id, a.product_id, u.username, a.ask_content, " +
                "a.create_id, a.create_time, a.update_id, a.update_time " +
                "FROM ask a " +
                "JOIN account u ON a.user_id = u.id " +
                "WHERE a.ask_id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(AskDTOWithUsername.class),askId);
    }

    public Ask insertAsk(Ask ask){
        final String sql = "insert into ask(user_id, product_id, ask_content, create_id, create_time, update_id, update_time) values(?,?,?,?,?,?,?) ";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Timestamp createTime = new Timestamp(new Date().getTime());
        Timestamp updateTime = new Timestamp(new Date().getTime());
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"ask_id"});
            ps.setLong(1, ask.getUserId());
            ps.setLong(2, ask.getProductId());
            ps.setString(3, ask.getAskContent());
            ps.setLong(4, ask.getUserId());
            ps.setTimestamp(5, createTime);
            ps.setLong(6, ask.getUserId());
            ps.setTimestamp(7, updateTime);
            return ps;
        }, keyHolder);

        Long generatedAskId = keyHolder.getKey().longValue();
        String selectsql = "select * from ask where ask_id = ?";
        return jdbcTemplate.queryForObject(selectsql, new BeanPropertyRowMapper<>(Ask.class),generatedAskId);
    }

    public void deleteAsk(Long askId, AskDTO askDTO){
        try{
            String sql = "delete from ask where ask_id = ? AND user_id = ? AND product_id = ?";
            jdbcTemplate.update(sql, askId, askDTO.getUserId(), askDTO.getProductId());
        }catch(Exception e){
            log.error("sql injection failed");
        }

    }
}
