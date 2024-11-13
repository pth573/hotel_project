package com.project.hotel.service;

import com.project.hotel.model.entity.Bed;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BedService {
    void save(Bed bed);
    Bed findById(Long id);
    //    Bed findByName(String name);
    List<Bed> findAll();
    void deleteById(Long id);
//    void updateService(Long serviceId, Service updatedService);
}
