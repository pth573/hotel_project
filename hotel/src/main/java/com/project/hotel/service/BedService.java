package com.project.hotel.service;

import com.project.hotel.model.entity.Bed;
import com.project.hotel.model.entity.Service;

import java.util.List;

public interface BedService {
    void save(Bed bed);
    Bed findById(Long id);
    //    Bed findByName(String name);
    List<Bed> findAll();
    void deleteById(Long id);
//    void updateService(Long serviceId, Service updatedService);
}
