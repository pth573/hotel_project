package com.project.hotel.model.enumType;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum PaymentStatus {
    PAID("Đã thanh toán "),
    PENDING("Chưa thanh toán"),
    DEPOSIT_PAID("Đã thanh toán tiền cọc"),
    NOT_PAID("Chưa thanh toán");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }


    @Override
    public String toString() {
        return displayName;
    }
}
