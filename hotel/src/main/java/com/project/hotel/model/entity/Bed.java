package com.project.hotel.model.entity;

import com.project.hotel.model.enumType.BedType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "bed")
public class Bed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BedType bedType;
    private int bedNumber;

    @ManyToMany(mappedBy = "beds")
    private List<RoomGroup> roomGroups;

    public Bed(BedType bedType, int bedNumber) {
        this.bedType = bedType;
        this.bedNumber = bedNumber;
    }
//
    public void addRoomGroup(RoomGroup roomGroup) {
        if(roomGroups == null){
            roomGroups = new ArrayList<>();
        }
        roomGroups.add(roomGroup);
//        roomGroup.setRoomGroup(this);
    }

//    public void removeImg(RoomImage image) {
//        if(images != null){
//            images.remove(image);
//            image.setRoomGroup(null);
//        }
//    }
//
//    public void addRoom(Room room) {
//        if(rooms == null){
//            rooms = new ArrayList<>();
//        }
//        rooms.add(room);
//        room.setRoomGroup(this);
//    }
//
//    public void removeRoom(Room room) {
//        if(rooms != null){
//            rooms.remove(room);
//            room.setRoomGroup(null);
//        }
//    }
//
//    public void clearImages() {
//        if (images != null) {
//            images.forEach(image -> image.setRoomGroup(null)); // Xóa tham chiếu roomGroup trong từng RoomImage
//            images.clear(); // Xóa tất cả phần tử trong danh sách images
//        }
//    }
//
//    public void clearRooms() {
//        if (rooms != null) {
//            rooms.forEach(room -> room.setRoomGroup(null)); // Xóa tham chiếu roomGroup trong từng Room
//            rooms.clear(); // Xóa tất cả phần tử trong danh sách rooms
//        }
//    }


    @Override
    public String toString() {
        return "Bed{" +
                "id=" + id +
                ", bedType=" + bedType +
                ", bedNumber=" + bedNumber +
                '}';
    }
}
