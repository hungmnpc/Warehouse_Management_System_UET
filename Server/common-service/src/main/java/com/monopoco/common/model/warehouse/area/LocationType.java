package com.monopoco.common.model.warehouse.area;

import com.monopoco.common.model.HistoryType;
import lombok.Getter;

import java.util.stream.Stream;

/**
 * Project: Server
 * Package: com.monopoco.common.model.warehouse.area
 * Author: hungdq
 * Date: 09/05/2024
 * Time: 16:58
 */

@Getter
public enum LocationType {

    RACK(0), FLOOR(1), NONE(2);

    Integer type;

    LocationType(Integer type) {
        this.type = type;
    }

    public static LocationType of (int type) {
        return Stream.of(LocationType.values())
                .filter(p -> p.getType() == type)
                .findFirst()
                .orElseThrow(IllegalAccessError::new);
    }

    public static LocationType copy(LocationType locationType) {
        return of(locationType.getType());
    }


    @Override
    public String toString() {
        return switch (type) {
            case 0 -> "RACK";
            case 1 -> "FLOOR";
            case 2 -> "NONE";
            default -> "UNKNOWN TYPE";
        };
    }
}