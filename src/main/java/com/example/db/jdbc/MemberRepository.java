package com.example.db.jdbc;

import com.example.db.entity.Account;
import com.example.db.enums.UserRole;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Repository
public class MemberRepository {

    private final JdbcTemplate template;

    public MemberRepository(DataSource dataSource){
        this.template = new JdbcTemplate(dataSource);
    }

    /*
    public Account save(Account account){
        String sql = "insert into account (login_id, username, email, role) values (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        template.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setLong(1, account.getLoginId());
            ps.setString(2, account.getUsername());
            ps.setString(3, account.getEmail());
            ps.setString(4, account.getRole() != null ? account.getRole().name() : UserRole.USER.name());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        account.setId(key);
        return account;
    }
    */

    public Account save(Account account) {
        String sql;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        if(account.getLocalId() != null){
            //이 경우에는 카카오 로그인 말고 자체 로그인 진행
            sql = "INSERT INTO account (login_id, local_id, password, username, email, phone_number, role) values (?, ?, ?, ?, ?, ?, ?)";
            template.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                ps.setLong(1, account.getLoginId());
                ps.setString(2, account.getLocalId());
                ps.setString(3, account.getPassword());
                ps.setString(4, account.getUsername());
                ps.setString(5, account.getEmail());
                ps.setString(6, account.getPhoneNumber());
                ps.setString(7, account.getRole().name());
                return ps;
            }, keyHolder);
        } else{
            sql = "INSERT INTO account (login_id, username, email, phone_number, role) VALUES (?, ?, ?, ?, ?)";
            template.update(con -> {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                ps.setLong(1, account.getLoginId());
                ps.setString(2, account.getUsername());
                ps.setString(3, account.getEmail());
                ps.setString(4, account.getPhoneNumber());
                ps.setString(5, account.getRole().name());
                return ps;
            }, keyHolder);
        }

        account.setId(keyHolder.getKey().longValue());
        return account;
    }



    public Optional<Account> findByKakaoId(Long loginId){
        String sql = "select id, login_id, username, email, phone_number, role from account where login_id = ?";
        try {
            Account account = template.queryForObject(sql, kakaoAccountRowMapper(), loginId);
            return Optional.of(account);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Account> findById(Long loginId){
        String sql = "SELECT id, login_id, local_id, password, username, email, phone_number, role from account WHERE id = ?";
        try {
            Account account = template.queryForObject(sql, accountRowMapper(), loginId);
            return Optional.of(account);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Account> findByLoginId(Long loginId){
        String sql = "SELECT id, login_id, local_id, password, username, email, phone_number, role from account WHERE login_id = ?";
        try {
            Account account = template.queryForObject(sql, accountRowMapper(), loginId);
            return Optional.of(account);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Account> findByLocalId(String localId){
        String sql = "SELECT id, login_id, local_id, password, username, email, phone_number, role from account WHERE local_id = ?";
        try {
            Account account = template.queryForObject(sql, accountRowMapper(), localId);
            return Optional.of(account);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public Optional<Account> findByUsername(String username){
        String sql = "select id, login_id, local_id, password, username, email, phone_number, role from account where username = ?";
        try {
            Account account = template.queryForObject(sql, accountRowMapper(), username);
            return Optional.of(account);
        } catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    public void updateRole(Long id, UserRole role){
        String sql = "update account set role = ? where id = ?";
        template.update(sql, role.name(), id);
    }

    public void updatePhoneNumber(Long id, String phoneNumber){
       String sql = "update account set phone_number = ? where id = ?";
       template.update(sql, phoneNumber, id);
    }

    public void updateUsername(Long id, String username){
        String sql = "update account set username = ? where id = ?";
        template.update(sql, username, id);
    }

    public void updateEmail(Long id, String email){
        String sql = "update account set email = ? where id = ?";
        template.update(sql, email, id);
    }

    public List<Account> findAllAccount(){
        String sql = "SELECT * FROM account";
        return template.query(sql, accountRowMapper());
    }

    private RowMapper<Account> accountRowMapper(){
        return (rs, rowNum) -> {
            Account account = new Account();
            account.setId(rs.getLong("id"));
            account.setLoginId(rs.getLong("login_id"));
            account.setLocalId(rs.getString("local_id"));
            account.setPassword(rs.getString("password"));
            account.setUsername(rs.getString("username"));
            account.setEmail(rs.getString("email"));
            account.setPhoneNumber(rs.getString("phone_number"));
            account.setRole(UserRole.valueOf(rs.getString("role")));
            return account;
        };
    }

    public void updateUser(Account account){
        String sql = "UPDATE account SET username = ?, email = ?, phone_number = ? WHERE login_id = ?";
        template.update(sql, account.getEmail(), account.getPhoneNumber(), account.getLoginId());
    }

    public void deleteById(Long id){
        String sql = "DELETE FROM account WHERE id = ?";
        int rowsAffected = template.update(sql, id);
        if(rowsAffected == 0){
            throw new RuntimeException("Failed to delete user: No user found with id " + id);
        }
    }


    public Long generateRandomLoginId(int digits){
        Random random = new Random();
        Long loginId;
        do {
            StringBuilder loginIdBuilder = new StringBuilder();
            for (int i = 0; i < digits; i++) {
                loginIdBuilder.append(random.nextInt(10));
            }
            loginId = Long.parseLong(loginIdBuilder.toString());
        } while (findByLoginId(loginId).isPresent()); // 중복 확인
        return loginId;
    }

    private RowMapper<Account> kakaoAccountRowMapper() {
        return (rs, rowNum) -> {
            Account account = new Account();
            account.setId(rs.getLong("id"));
            account.setLoginId(rs.getLong("login_id"));
            account.setUsername(rs.getString("username"));
            account.setEmail(rs.getString("email"));
            account.setPhoneNumber(rs.getString("phone_number"));
            account.setRole(UserRole.valueOf(rs.getString("role")));
            return account;
        };
    }


}
