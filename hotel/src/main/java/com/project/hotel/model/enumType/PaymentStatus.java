package com.project.hotel.model.enumType;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PAID("Đã thanh toán "),
    DEPOSIT_PAID("thanh toán tiền cọc"),
    NOT_PAID("Chua thanh toán");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
