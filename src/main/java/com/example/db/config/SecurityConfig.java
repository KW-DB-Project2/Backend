package com.example.db.config;

import com.example.db.error.CustomAccessDeniedHandler;
import com.example.db.error.CustomAuthenticationEntryPoint;
import com.example.db.jdbc.MemberRepository;
import com.example.db.jwt.JwtAuthenticationFilter;
//import com.example.db.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    //private final CustomAuthenticationProvider customAuthenticationProvider;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter,
                          CustomAuthenticationEntryPoint authenticationEntryPoint,
                          CustomAccessDeniedHandler accessDeniedHandler,
                          MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /*
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(memberRepository, passwordEncoder);
    }
    */

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(withDefaults())  // Enable CORS
                //.cors(cors -> cors.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(formLogin -> formLogin.disable())
                .logout(logout -> logout.disable())
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/strategy/**").permitAll()
                                .requestMatchers("/fortest").permitAll()
                                //.requestMatchers("/home/**").authenticated()
                                .requestMatchers("/logout").permitAll()
                                .requestMatchers("/api/user-info").permitAll()  //후에 check
                                .requestMatchers("/edit/user-info").permitAll()
                                .requestMatchers("/kakao/user/extra-info").permitAll()
                                .requestMatchers("/login/**").permitAll()
                                .requestMatchers("/refresh-token").permitAll()
                                .requestMatchers("/result/**").permitAll()
                                .requestMatchers("/user/info").permitAll()
                                .requestMatchers("/userinfo").permitAll()
                                .requestMatchers("/mypage/user").permitAll()
                                .requestMatchers("/mypage").permitAll()
                                .requestMatchers("/home/kospi").permitAll()
                                .requestMatchers("/home/kosdaq").permitAll()
                                .requestMatchers("/home/kospi200").permitAll()
                                .requestMatchers("/home/top20").permitAll()
                                .requestMatchers("/home/coinByFluctuating").permitAll()
                                .requestMatchers("/home/coinByClosingPrice").permitAll()
                                .requestMatchers("/home/coinByTradingVolume").permitAll()
                                .requestMatchers("/home/backtesting_gd").permitAll()
                                .requestMatchers("/home/backtesting_bb").permitAll()
                                .requestMatchers("/home/backtesting_ind").permitAll()
                                .requestMatchers("/home/backtesting_env").permitAll()
                                .requestMatchers("/home/backtesting_w").permitAll()
                                .requestMatchers("/backtest/**").permitAll()
                                .requestMatchers("/backtesting_mine_/**").permitAll()
                                .requestMatchers("/register").permitAll()
                                .requestMatchers("/local/**").permitAll()
                                .anyRequest().authenticated()
                )
                //.authenticationProvider(customAuthenticationProvider())
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling
                                .authenticationEntryPoint(authenticationEntryPoint)
                                .accessDeniedHandler(accessDeniedHandler)
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
