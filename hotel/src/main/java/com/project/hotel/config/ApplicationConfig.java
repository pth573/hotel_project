package com.project.hotel.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class ApplicationConfig {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserServiceConfig();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder
                = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());

        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz ->
                        authz.requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/register/**").permitAll()
                                .requestMatchers("/room/**").permitAll()
                                .requestMatchers("/book-room/**").permitAll()
                                .requestMatchers("/room-booking").permitAll()
                                .requestMatchers("/book-room").permitAll()
                                .requestMatchers("/room/update/**").permitAll()
                                .requestMatchers("/rooms/manager").permitAll()
                                .requestMatchers("/room-group/list").permitAll()
                                .requestMatchers("/room-group/**").permitAll()
                                .requestMatchers("/room-group/update/**").permitAll()
                                .requestMatchers("/service/list").permitAll()
                                .requestMatchers("/service/**").permitAll()
                                .requestMatchers("/delete/**").permitAll()
                                .requestMatchers("/update/**").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/1").permitAll()
                                .requestMatchers("/room-group-list-available/**").permitAll()
                                .requestMatchers("/admin/get-room-group/**").permitAll()
                                .requestMatchers("/admin/update-room-group/**").permitAll()
                                .requestMatchers("/admin/searchCustomer/**").permitAll()
                                .requestMatchers("/admin/booking/online/**").permitAll()
                                .requestMatchers("/find-customer/**").permitAll()
                                .requestMatchers("/create-customer/**").permitAll()
                                .requestMatchers("/book-room").permitAll()
                                .requestMatchers("/css/**", "/js/**", "/img/**", "/fonts/**","/dist/**", "/scss/**") // Thêm các đường dẫn tài nguyên tĩnh
                                .permitAll() // Cho phép truy cập không cần xác thực
                )
                .formLogin(login ->
                        login.loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .permitAll()
                                .successHandler((request, response, authentication) -> {
                                    String role = authentication.getAuthorities().toString();
                                    if (role.contains("ADMIN")) {
                                        response.sendRedirect("/admin");
                                    } else if (role.contains("CUSTOMER")) {
                                        response.sendRedirect("/");
                                    }
                                })
                )
                .logout(logout ->
                        logout.invalidateHttpSession(true)
                                .clearAuthentication(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                )
                .authenticationManager(authenticationManager)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                )
        ;

        return http.build();
    }
}