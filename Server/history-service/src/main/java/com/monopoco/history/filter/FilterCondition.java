package com.monopoco.history.filter;

import com.monopoco.history.enums.FilterOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Project: Server
 * Package: com.monopoco.history.filter
 * Author: hungdq
 * Date: 15/04/2024
 * Time: 16:37
 */
@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class FilterCondition {

    private String field;
    private FilterOperationEnum operator;
    private Object value;

}
