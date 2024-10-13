package com.example.db.jdbc;

import com.example.db.entity.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;

@Repository
public class MemberRepository {

    private final JdbcTemplate template;

    public MemberRepository(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    public Account save(Account account){
        String sql = "insert into account (loginId, username, email) values (?, ?, ?)";

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


}
