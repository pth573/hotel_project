package com.project.hotel.service.impl;

import com.project.hotel.model.dto.UserDto;
import com.project.hotel.model.entity.User;
import com.project.hotel.repository.UserRepository;
import com.project.hotel.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Override
    public boolean isValidUser(UserDto userDto) {
        return userRepository.findUserByEmail(userDto.getEmail())
                .map(user -> bCryptPasswordEncoder.matches(userDto.getPassword(), user.getPassword()))
                .orElse(false);
    }

    @Override
    public Optional<User> findUserByEmail(String username) {
        return userRepository.findUserByEmail(username);
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = User.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .build();
        userRepository.save(user);
    }

}
