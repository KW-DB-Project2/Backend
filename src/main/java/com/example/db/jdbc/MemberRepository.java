package com.example.db.jdbc;

import com.example.db.entity.Account;
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
public class MemberRepository {

    private final JdbcTemplate template;

    public MemberRepository(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    public Account save(Account account){
        String sql = "insert into account (login_id, username, email) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, account.getLoginId());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getEmail());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        account.setId(key);
        return account;
    }

    public Optional<Account> findByKakaoId(Long loginId){
        String sql = "select id, login_id, username, email from account where login_id = ?";
        try {
            Account account = template.queryForObject(sql, accountRowMapper(), loginId);
            return Optional.of(account);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Account> findByUsername(String username){
        String sql = "select id, login_id, username, email from account where username = ?";
        try {
            Account account = template.queryForObject(sql, accountRowMapper(), username);
            return Optional.of(account);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    private RowMapper<Account> accountRowMapper(){
        return (rs, rowNum) -> {
            Account account = new Account();
            account.setId(rs.getLong("id"));
            account.setLoginId(rs.getLong("login_id"));
            account.setUsername(rs.getString("username"));
            account.setEmail(rs.getString("email"));
            return account;
        };
    }


}
