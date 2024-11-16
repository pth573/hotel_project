package com.project.hotel.repository;

import com.project.hotel.model.entity.RoomGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomGroupRepository extends JpaRepository<RoomGroup, Long> {
    RoomGroup findByGroupName(String name);
}
