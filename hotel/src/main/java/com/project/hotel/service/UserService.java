package com.project.hotel.service;

import com.project.hotel.model.dto.UserDto;
import com.project.hotel.model.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {
    boolean isValidUser(UserDto userDto);
    Optional<User> findUserByEmail(String username);
    void saveUser(UserDto userDto);
}
