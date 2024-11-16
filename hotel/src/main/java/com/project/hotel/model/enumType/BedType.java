package com.project.hotel.model.enumType;

import lombok.Getter;

@Getter
public enum BedType {
    SINGLE("Giường đơn"),
    DOUBLE("Giường đôi"),
    KING_SIZE("Giường king size"),
    QUEEN_SIZE("Giường queen size");

    private final String displayName;

    BedType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }


}
