package com.monopoco.inventory.entity;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * Project: Server
 * Package: com.monopoco.inventory.entity
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 15:52
 */

@Getter
public enum Status {

    NONE(0), DRAFT(1), PENDING(2), CONFIRMED(3), PROCESSING(4),
    COMPLETED(5), CANCELLED(6), DELAYED(7);

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
            case 1 -> "DRAFT";
            case 2 -> "PENDING";
            case 3 -> "CONFIRMED";
            case 4 -> "PROCESSING";
            case 5 -> "COMPLETED";
            case 6 -> "CANCELLED";
            case 7 -> "DELAYED";
            default -> "NONE";
        };
    }
}