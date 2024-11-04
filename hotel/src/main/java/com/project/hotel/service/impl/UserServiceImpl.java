package com.project.hotel.service.impl;
import com.project.hotel.model.entity.User;
import com.project.hotel.model.enumType.Role;
import com.project.hotel.model.user.WebUser;
import com.project.hotel.repository.UserRepository;
import com.project.hotel.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Override
    public Optional<User> findUserByEmail(String username) {
        return userRepository.findUserByEmail(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> result = userRepository.findUserByEmail(email);
        User user = null;
        if(result.isPresent()){
            user = result.get();
        }
        else{
            throw new RuntimeException("Không thấy user có email là: " + email);
        }
        return user;
    }

    @Override
    public void save(WebUser webUser) {
        User user = new User();
        user.setEmail(webUser.getEmail());
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));
        user.setFullName(webUser.getFullName());
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.get().getRole().name());
        Collection<SimpleGrantedAuthority> authorities = Collections.singletonList(authority);

        return new org.springframework.security.core.userdetails.User(
                user.get().getEmail(),
                user.get().getPassword(),
                authorities
        );
    }

    private Collection<SimpleGrantedAuthority> mapRoleToAuthority(Role role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }
}
