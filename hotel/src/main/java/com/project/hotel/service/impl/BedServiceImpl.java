package com.project.hotel.service.impl;
import com.project.hotel.model.entity.Bed;
import com.project.hotel.repository.BedRepository;
import com.project.hotel.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BedServiceImpl implements BedService {
    @Autowired
    private BedRepository bedRepository;

    @Override
    public void save(Bed bed) {
        bedRepository.save(bed);
    }

    @Override
    public Bed findById(Long id) {
        Optional<Bed> result = bedRepository.findById(id);
        Bed bed = null;
        if(result.isPresent()){
            bed = result.get();
        }
        else{
            throw new RuntimeException("Không thấy service có id: " + id);
        }
        return bed;
    }

    @Override
    public List<Bed> findAll() {
        return bedRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        bedRepository.deleteById(id);
    }

}
