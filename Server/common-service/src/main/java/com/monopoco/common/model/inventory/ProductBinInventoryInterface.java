package com.monopoco.common.model.inventory;

import com.monopoco.common.factory.CommonUtil;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

/**
 * Project: Server
 * Package: com.monopoco.common.model.inventory
 * Author: hungdq
 * Date: 15/05/2024
 * Time: 15:10
 */
public interface ProductBinInventoryInterface {


    byte[] getId();

    default UUID getUUIDId() {
        byte[] bytes = getId();
        return CommonUtil.convertFromByteArray(bytes);
    }

    byte[] getBinId();

    default UUID getUUIDBinId() {
        byte[] bytes = getBinId();
        return CommonUtil.convertFromByteArray(bytes);
    }

    byte[] getProductId();

    default UUID getUUIDProductId() {
        byte[] bytes = getProductId();
        return CommonUtil.convertFromByteArray(bytes);
    }

    Integer getQuantity();

    String getBinName();

    String getProductName();

    String getWarehouseName();

    Integer getIsRackStorage();


    byte[] getRackId();

    default UUID getUUIDRackId() {
        byte[] bytes = getRackId();
        return CommonUtil.convertFromByteArray(bytes);
    }


    byte[] getFloorId();

    default UUID getUUIDFloorId() {
        byte[] bytes = getFloorId ();
        return CommonUtil.convertFromByteArray(bytes);
    }

    String getAreaName();
}