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
public class CustomerDto2 {
    private Long customerId;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Long totalAmountPaid;
    private Long totalAmountBooking;
}
