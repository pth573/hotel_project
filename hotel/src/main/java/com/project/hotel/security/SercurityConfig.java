//package com.project.hotel.security;
//
//import com.project.hotel.service.UserService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//
//
//import javax.sql.DataSource;
//
//
//@Configuration
//public class SecurityConfig {
//
////    @Bean
//
//
//    //bcrypt bean definition
//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    //authenticationProvider bean definition
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
//        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//        auth.setUserDetailsService(userService); //set the custom user details service
//        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
//        return auth;
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests(configurer ->
//                                configurer
//                                        .requestMatchers("/").permitAll()
//                                        .requestMatchers("/home/courses/list/**").permitAll()
//                                        .requestMatchers("/users/**").hasAnyRole("USER", "INSTRUCTOR", "ADMIN")
////                                .requestMatchers("/users/**").hasRole()
////                                .requestMatchers("/users/**").hasRole("ADMIN")
////                                .requestMatchers("/course/**").hasRole("INSTRUCTOR")
////                                .requestMatchers("/courses/list-none-active").hasAnyRole("INSTRUCTOR", "ADMIN")
////                                .requestMatchers("/").hasRole("USER")
////                                .requestMatchers("/").hasRole("ADMIN")
////                                .requestMatchers("/").hasRole("INSTRUCTOR")
////                                .requestMatchers("/courses/active").hasRole("ADMIN")
//                                        .requestMatchers("/courses/list-none-active").hasRole("ADMIN")
//                                        .requestMatchers("/orders/list").hasRole("ADMIN")
////                                .requestMatchers("/leaders/**").hasRole("INSTRUCTOR")
////                                .requestMatchers("/systems/**").hasRole("ADMIN")
//                                        .requestMatchers("/register/**").permitAll()
//                                        .anyRequest().authenticated()
//                )
//                .formLogin(form ->
//                        form
//                                .loginPage("/showMyLoginPage")
//                                .loginProcessingUrl("/authenticateTheUser")
//                                .permitAll()
//                )
//                .logout(logout -> logout.permitAll()
//                )
////                .logout(logout ->
////                        logout.invalidateHttpSession(true)
////                                .clearAuthentication(true)
////                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
////                                .logoutSuccessUrl("/login?logout")
////                                .permitAll()
////                )
//                .exceptionHandling(configurer ->
//                        configurer.accessDeniedPage("/access-denied")
//                );
//
//        return http.build();
//    }
//
//}
