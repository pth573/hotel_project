package com.project.hotel.repository;

import com.project.hotel.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
}
