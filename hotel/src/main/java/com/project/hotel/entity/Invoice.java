package com.project.hotel.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Invoice {
    private int invoiceId;
    private String issueDate;
    private long totalAmount;
    private long discount;
    private long netAmount;
}
