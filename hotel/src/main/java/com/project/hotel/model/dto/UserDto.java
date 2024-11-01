package com.project.hotel.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String fullName;

    @NotNull(message = "email must be not null")
    private String email;
    @NotNull(message = "password must be not null")
    private String password;
    private String confirmedPassword;
    private String phoneNumber;
    private String address;
    private String identityCard;
    private Date dateOfBirth;
}
