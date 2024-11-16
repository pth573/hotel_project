package com.project.hotel.model.enumType;

import lombok.Getter;

@Getter
public enum BookingStatus {
    DELETED("Xóa"),
    PENDING("Đang chờ"),
    ACCEPTED("Chấp nhận"),
    REJECTED("Từ chối");
    private final String displayName;

    BookingStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

}
