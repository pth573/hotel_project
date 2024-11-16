package com.project.hotel.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ServiceDTO {
    private Long serviceId;
    private String serviceName;
    private String description;
    private double price;
}
