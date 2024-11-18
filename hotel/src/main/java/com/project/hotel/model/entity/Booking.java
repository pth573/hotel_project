package com.project.hotel.model.entity;

import com.project.hotel.model.enumType.BookingStatus;
import com.project.hotel.model.enumType.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Table(name = "booking")
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    private String checkInDate;
    private String checkOutDate;
    private Long totalPrice;
    private Long amountHasPaid;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer user;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

//    @OneToOne(mappedBy = "booking", cascade = CascadeType.ALL)
//    private Invoice invoice;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;


}
