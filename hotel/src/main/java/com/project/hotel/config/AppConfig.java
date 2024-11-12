package com.project.hotel.config;

import com.project.hotel.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
//                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticateTheUser")
                                .usernameParameter("email")
                                .passwordParameter("password")
                                .permitAll()
                )
                .logout(logout ->
                        logout.invalidateHttpSession(true)
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
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