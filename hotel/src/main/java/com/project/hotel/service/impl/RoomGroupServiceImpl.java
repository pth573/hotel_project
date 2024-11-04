package com.project.hotel.service.impl;
import com.project.hotel.model.entity.Room;
import com.project.hotel.model.entity.RoomGroup;
import com.project.hotel.repository.RoomGroupRepository;
import com.project.hotel.repository.RoomImageRepository;
import com.project.hotel.service.RoomGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomGroupServiceImpl implements RoomGroupService {

    @Autowired
    private RoomGroupRepository roomGroupRepository;

    @Autowired
    private RoomImageRepository roomImageRepository;

    @Override
    public void save(RoomGroup roomGroup) {
        roomGroupRepository.save(roomGroup);
    }

    @Override
    public RoomGroup findById(Long id) {
        Optional<RoomGroup> result = roomGroupRepository.findById(id);
        RoomGroup roomGroup = null;
        if(result.isPresent()){
            roomGroup = result.get();
        }
        else{
            throw new RuntimeException("Không thấy RoomGroup có id: " + id);
        }
        return roomGroup;
    }


    @Override
    public List<RoomGroup> findAll() {
        return roomGroupRepository.findAll();
    }

//    @Override
//    public void deleteById(Long id) {
//        Optional<RoomGroup> roomGroupOptional = roomGroupRepository.findById(id);
//        if (roomGroupOptional.isPresent()) {
//            RoomGroup roomGroup = roomGroupOptional.get();
//            roomImageRepository.deleteRoomImageBy(roomGroup);
//
//            roomGroupRepository.delete(roomGroup);
//        } else {
//            throw new ResourceNotFoundException("RoomGroup not found with id " + id);
//        }
//    }

    @Override
    public void deleteById(Long id) {
        roomGroupRepository.deleteById(id);
    }



    public void updateRoomGroup(RoomGroup roomGroup) {
        if (roomGroupRepository.existsById(roomGroup.getRoomGroupId())) {
            roomGroupRepository.save(roomGroup);
        } else {
            throw new RuntimeException("Nhóm phòng không tồn tại");
        }
    }
}
