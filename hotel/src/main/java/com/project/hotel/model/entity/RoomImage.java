    package com.project.hotel.model.entity;

    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Table(name = "room_image")
    public class RoomImage extends BaseEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long imageId;
        private String imageUrl;

        @ManyToOne
        @JoinColumn(name = "room_group_id")
        private RoomGroup roomGroup;
    }
