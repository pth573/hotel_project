package com.project.hotel.service;
import com.project.hotel.model.entity.User;
import com.project.hotel.model.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {
    Optional<User> findUserByEmail(String username);
    void save(WebUser webUser);
    void save(User user);
    User findByEmail(String email);
}
