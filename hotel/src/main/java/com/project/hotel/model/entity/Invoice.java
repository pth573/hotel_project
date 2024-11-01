package com.project.hotel.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "invoice")
public class Invoice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
    private String issueDate;
    private Long totalAmount;
    private Long discount;
    private Long netAmount;

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;
}
