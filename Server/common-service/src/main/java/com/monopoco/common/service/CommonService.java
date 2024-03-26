package com.monopoco.common.service;

import com.monopoco.common.model.response.CommonResponse;

import java.util.UUID;

public interface CommonService {

    /**
     * Generate Random UUID
     * @return UUID
     */
    public CommonResponse<UUID> generateRandomUUID();
}
