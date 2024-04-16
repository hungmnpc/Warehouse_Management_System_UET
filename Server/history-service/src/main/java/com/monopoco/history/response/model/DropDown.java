package com.monopoco.history.response.model;

import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.authservice.response.model
 * Author: hungdq
 * Date: 28/03/2024
 * Time: 14:40
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DropDown<K, V> {

    private K key;

    private V value;

}
