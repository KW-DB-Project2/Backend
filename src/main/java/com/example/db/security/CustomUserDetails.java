package com.example.db.security;

import com.example.db.entity.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/*
public class CustomUserDetails implements UserDetails {

    private final Long loginId;
    private final String username;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Account account){
        this.loginId = account.getLoginId();
        this.username = account.getUsername();
        this.password = account.getPassword();
        this.authorities = List.of(new SimpleGrantedAuthority(account.getRole().name()));
    }

    @Override
    public String getUsername() {
        return loginId.toString();
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
*/