package com.project.hotel.config;

import com.project.hotel.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class AppConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers("/**").permitAll()
                                .requestMatchers("/register/**").permitAll()
                                .requestMatchers("/room/**").permitAll()
                                .requestMatchers("/bookRoom/**").permitAll()
                                .requestMatchers("/room-booking").permitAll()
                                .requestMatchers("/bookRoom").permitAll()
                                .requestMatchers("/room/update/**").permitAll()
                                .requestMatchers("/rooms/manager").permitAll()
                                .requestMatchers("/roomgroup/list").permitAll()
                                .requestMatchers("/roomgroup/**").permitAll()
                                .requestMatchers("/roomgroup/update/**").permitAll()
                                .requestMatchers("/service/list").permitAll()
                                .requestMatchers("/service/**").permitAll()
                                .requestMatchers("/delete/**").permitAll()
                                .requestMatchers("/update/**").permitAll()
                                .requestMatchers("/").permitAll()
                                .requestMatchers("/login").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .permitAll()
                )
                .logout(logout -> logout.permitAll()
                )
                .exceptionHandling(configurer ->
                        configurer.accessDeniedPage("/access-denied")
                )
                .csrf(csrf ->
                        csrf.ignoringRequestMatchers("/api/**")  // Không bảo vệ CSRF đối với các yêu cầu API
                );
        return http.build();
    }
}