package com.project.hotel.model.enumType;

import lombok.Getter;

@Getter
public enum RoomStatus {
    AVAILABLE("Còn trống "),
    BOOKED("Đã dược đặt");

    private final String displayName;

    RoomStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }


}
