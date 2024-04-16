package com.monopoco.inventory.request;

import com.monopoco.inventory.entity.ImportRequest;
import com.monopoco.inventory.entity.Status;
import com.monopoco.inventory.util.CommonUtil;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.inventory.request
 * Author: hungdq
 * Date: 16/04/2024
 * Time: 15:52
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ImportRequestBody {

    private UUID warehouseId;

    private String poCode;

    private String importRequestCode;

    private LocalDate deliveryDate;

    private LocalDate estimatedArrivalDate;

    private Status status;

    private UUID employeeId1;

    private UUID employeeId2;

    private UUID employeeId3;

    private UUID employeeId4;

    private List<RequestDetail> requestDetailList;


    public ImportRequest mapToEntity() {
        return ImportRequest.builder()
                .id(CommonUtil.generateRandomUUID())
                .warehouseId(this.warehouseId)
                .poCode(this.poCode)
                .importRequestCode(this.importRequestCode)
                .deliveryDate(this.deliveryDate)
                .status(this.status)
                .employeeId1(this.employeeId1)
                .employeeId2(this.employeeId2)
                .employeeId3(this.employeeId3)
                .employeeId4(this.employeeId4)
                .estimatedArrivalDate(this.estimatedArrivalDate)
                .build();
    }

}
