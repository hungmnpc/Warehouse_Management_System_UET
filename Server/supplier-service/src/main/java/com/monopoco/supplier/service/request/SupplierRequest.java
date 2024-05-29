package com.monopoco.supplier.service.request;

import lombok.*;

/**
 * Project: Server
 * Package: com.monopoco.supplier.service.request
 * Author: hungdq
 * Date: 21/04/2024
 * Time: 17:57
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class SupplierRequest {

    private String supplierName;

    private String supplierNumber;

    private String supplierAddress1;

    private String supplierAddress2;
}
