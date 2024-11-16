package com.example.db.security;

import com.example.db.entity.Account;
import com.example.db.jdbc.MemberRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();

        Long loginId;
        try {
            loginId = Long.parseLong(username);
        } catch (NumberFormatException e) {
            throw new BadCredentialsException("Invalid username or password");
        }

        Account account = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new BadCredentialsException("Invalid username or password"));

        if(!passwordEncoder.matches(rawPassword, account.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        //인증성공
        CustomUserDetails userDetails = new CustomUserDetails(account);
        return new UsernamePasswordAuthenticationToken(userDetails, rawPassword, userDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
