// package com.project.hotel.model.entity;

// import jakarta.persistence.*;
// import lombok.*;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Set;
// import java.util.stream.Collectors;

// @NoArgsConstructor
// @AllArgsConstructor
// @Builder
// @ToString
// @Getter
// @Setter
// public class RoomGroupBooking extends BaseEntity {
//     private Long roomGroupId;
//     private String groupName;
//     private Float area;
//     private List<Room> rooms;
//     private List<RoomImage> images;
//     private List<Bed> beds;

//     private Long pricePerNight;
//     private Long comboPrice2H;
//     private Long extraHourPrice;
//     private String description;

//     private int standardOccupancy;
//     private int maxOccupancy;
//     private int numChildrenFree;
//     private int extraAdult;


//     private String imageUrl;


//     private Long availableRoomCount;

//     public String getBedDescription() {
//         return beds.stream()
//                 .filter(bed -> bed.getBedNumber() >= 1)  // Lọc ra các giường có số lượng >= 1
//                 .map(bed -> bed.getBedType() + " x" + bed.getBedNumber())  // Mã hóa thông tin giường
//                 .collect(Collectors.joining(", "));  // Nối các thông tin giường lại với nhau
//     }


// //    public String getBedDescription() {
// //        return beds.stream()
// //                .map(bed -> bed.getBedType() + " x" + bed.getBedNumber())
// //                .collect(Collectors.joining(", "));
// //    }

// //    public String getServiceDescription() {
// //        return servic.stream()
// //                .map(bed -> bed.getBedType() + " x" + bed.getBedNumber())
// //                .collect(Collectors.joining(", "));
// //    }


//     public void addImg(RoomImage image) {
//         if(images == null){
//             images = new ArrayList<>();
//         }
//         images.add(image);
//         image.setRoomGroup(this);
//     }

//     public void removeImg(RoomImage image) {
//         if(images != null){
//             images.remove(image);
//             image.setRoomGroup(null);
//         }
//     }

//     public void addRoom(Room room) {
//         if(rooms == null){
//             rooms = new ArrayList<>();
//         }
//         rooms.add(room);
//         room.setRoomGroup(this);
//     }

//     public void removeRoom(Room room) {
//         if(rooms != null){
//             rooms.remove(room);
//             room.setRoomGroup(null);
//         }
//     }

//     public void clearImages() {
//         if (images != null) {
//             images.forEach(image -> image.setRoomGroup(null)); // Xóa tham chiếu roomGroup trong từng RoomImage
//             images.clear(); // Xóa tất cả phần tử trong danh sách images
//         }
//     }

//     public void clearRooms() {
//         if (rooms != null) {
//             rooms.forEach(room -> room.setRoomGroup(null)); // Xóa tham chiếu roomGroup trong từng Room
//             rooms.clear(); // Xóa tất cả phần tử trong danh sách rooms
//         }
//     }

//     @Override
//     public String toString() {
//         return "RoomGroup{" +
//                 "roomGroupId=" + roomGroupId +
//                 ", groupName='" + groupName + '\'' +
//                 ", area=" + area +
// //                ", bedType=" + bedType +
// //                ", roomSz=" + rooms.size() +
// //                ", imagesSize=" + images.size() +
//                 ", pricePerNight=" + pricePerNight +
//                 ", comboPrice2H=" + comboPrice2H +
//                 ", extraHourPrice=" + extraHourPrice +
//                 ", description='" + description + '\'' +
//                 ", standardOccupancy=" + standardOccupancy +
//                 ", imageUrl='" + imageUrl + '\'' +
//                 '}';
//     }
// }
