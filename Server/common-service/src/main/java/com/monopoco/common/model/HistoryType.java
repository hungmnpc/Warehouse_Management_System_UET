package com.monopoco.common.model;

import lombok.Getter;

import java.util.stream.Stream;

/**
 * Project: Server
 * Package: com.monopoco.common.model
 * Author: hungdq
 * Date: 06/05/2024
 * Time: 18:12
 */

@Getter
public enum HistoryType {

    GET(0), POST(1), DELETE(2), UPDATE(3), MESSAGE(4);

    private Integer type;

    HistoryType(Integer type) {
        this.type = type;
    }

    public static HistoryType of (int type) {
        return Stream.of(HistoryType.values())
                .filter(p -> p.getType() == type)
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

    public static HistoryType copy(HistoryType historyType) {
        return of(historyType.getType());
    }


    @Override
    public String toString() {
        return switch (type) {
            case 0 -> "GET";
            case 1 -> "POST";
            case 2 -> "DELETE";
            case 3 -> "UPDATE";
            case 4 -> "MESSAGE";
            default -> "UNKNOWN STATUS";
        };
    }
}