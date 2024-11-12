package com.project.hotel.service.impl;

import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.Service;
import com.project.hotel.repository.ServiceRepository;
import com.project.hotel.service.ServiceService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceServiceImpl implements ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public void save(Service service) {
        serviceRepository.save(service);
    }

    @Override
    public Service findById(Long id) {
        Optional<Service> result = serviceRepository.findById(id);
        Service service = null;
        if(result.isPresent()){
            service = result.get();
        }
        else{
            throw new RuntimeException("Không thấy service có id: " + id);
        }
        return service;
    }

    @Override
    public Service findByName(String name) {
        Optional<Service> result = serviceRepository.findByServiceName(name);
        Service service = null;
        if(result.isPresent()){
            service = result.get();
        }
        else{
            throw new RuntimeException("Không thấy service có id: " + name);
        }
        return service;
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        ///
        serviceRepository.deleteById(id);
    }

    @Override
    public void updateService(Long serviceId, Service updatedService) {
        Optional<Service> optionalService = serviceRepository.findById(serviceId);
        if (optionalService.isPresent()) {
            Service service = optionalService.get();
            service.setServiceName(updatedService.getServiceName());
            service.setDescription(updatedService.getDescription());
            service.setPrice(updatedService.getPrice());
            serviceRepository.save(service);
        } else {
            throw new EntityNotFoundException("Service with id " + serviceId + " not found");

        }
    }

    @Override
    public List<Service> getTop5Services() {
        return serviceRepository.findTop5ByOrderByServiceIdAsc();
    }


}
