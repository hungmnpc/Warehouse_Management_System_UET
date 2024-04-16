package com.monopoco.history.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Project: Server
 * Package: com.monopoco.history.enums
 * Author: hungdq
 * Date: 15/04/2024
 * Time: 16:36
 */
public enum FilterOperationEnum {

    EQUAL("eq"),
    EQUAL_NUMBER("eqnum"),
    NOT_EQUAL("neq"),
    NOT_EQUAL_NUMBER("neqnum"),
    GREATER_THAN_NUMBER("gtnum"),
    GREATER_THAN_OR_EQUAL_TO_NUMBER("gtenum"),
    LESS_THAN_NUMBER("ltnum"),
    LESSTHAN_OR_EQUAL_TO_NUMBER("ltenum"),
    GREATER_THAN_DATE("gtdate"),
    LESS_THAN_DATE("ltdate"),
    IN("in"),
    NOT_IN("nin"),
    BETWEEN("btn"),
    CONTAINS("like"),
    NOT_CONTAINS("notLike"),
    IS_NULL("isnull"),
    IS_NOT_NULL("isnotnull"),
    START_WITH("startwith"),
    END_WITH("endwith"),
    IS_EMPTY("isempty"),
    IS_NOT_EMPTY("isnotempty"),
    JOIN("jn"),
    IS("is");


    private final String value;

    FilterOperationEnum(String value) {
        this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    public static FilterOperationEnum fromValue(String value) {
        for (FilterOperationEnum op : FilterOperationEnum.values()) {

            //Case insensitive operation name
            if (String.valueOf(op.value).equalsIgnoreCase(value)) {
                return op;
            }
        }
        return null;
    }

}
