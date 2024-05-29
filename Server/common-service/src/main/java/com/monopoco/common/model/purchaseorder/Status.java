package com.monopoco.common.model.purchaseorder;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * Project: Server
 * Package: com.monopoco.purchaseorder.entity
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 17:03
 */

@Getter
public enum Status {


    NONE(0), DRAFT(1), INCOMING(2) , RECEIVED_AND_REQUIRES_WAREHOUSING(3),
    WAREHOUSING(4), STOCKED(5);

    private Integer status;

    Status(Integer status) {
        this.status = status;
    }

    public static Status of (int status) {
        return Stream.of(Status.values())
                .filter(p -> p.getStatus() == status)
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

    public static Status copy(Status status) {
        return of(status.getStatus());
    }


    @Override
    public String toString() {
        return switch (status) {
            case 0 -> "None";
            case 1 -> "Draft";
            case 2 -> "Incoming";
            case 3 -> "Received and Requires Warehousing";
            case 4 -> "Warehousing";
            case 5 -> "Stocked";
            default -> "Unknown status";
        };
    }
}