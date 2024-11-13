package com.project.hotel.service;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.Service;

import java.util.List;

@org.springframework.stereotype.Service
public interface ServiceService {
    void save(Service service);
    Service findById(Long id);
    Service findByName(String name);
    List<Service> findAll();
    void deleteById(Long id);
    void updateService(Long serviceId, Service updatedService);
    List<Service> getTop5Services();
}
