package com.project.hotel.user;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class WebUser {
    @NotNull(message = "bắt buộc")
    @Size(min = 1, message = "bắt buộc")
    private String username;

    @NotNull(message = "bắt buộc")
    @Size(min = 1, message = "bắt buộc")
    private String password;

    @NotNull(message = "bắt buộc")
    @Size(min = 1, message = "bắt buộc")
    private String fullName;

    @NotNull(message = "bắt buộc")
    @Size(min = 1, message = "bắt buộc")
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;
}
