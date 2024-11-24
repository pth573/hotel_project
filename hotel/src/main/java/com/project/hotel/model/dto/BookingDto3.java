package com.project.hotel.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class BookingDto3 {
    private Long id;
    private Long totalPrice;
    private Long amountHasPaid;
    @Override
    public String toString() {
        return "BookingDto{" +
                "id=" + id +
                ", totalPrice=" + totalPrice +
                ", amountHasPaid=" + amountHasPaid +
                '}';
    }
}
