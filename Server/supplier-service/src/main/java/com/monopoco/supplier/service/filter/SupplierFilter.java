package com.monopoco.supplier.service.filter;

import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.supplier.service.filter
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 22:42
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SupplierFilter {

    private String supplierName;

    private String supplierNumber;

    private String supplierAddress;
}
