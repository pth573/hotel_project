package com.project.hotel.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerDto {
    @NotNull(message = "bắt buộc")
    @Size(min = 1, message = "bắt buộc")
    @Pattern(regexp="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    private String email;

    @NotNull(message = "bắt buộc")
    @Size(min = 1, message = "bắt buộc")
    private String password;

    @NotNull(message = "bắt buộc")
    @Size(min = 1, message = "bắt buộc")
    private String fullName;

    private String phoneNumber;

    private Long totalAmountPaid;
    private Long totalAmountBooking;

    private Long customerId;
    private String address;
    private String dateOfBirth;
    private List<BookingDto3> bookings;
}
