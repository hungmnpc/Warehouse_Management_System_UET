package com.monopoco.common.service.impl;

import com.monopoco.common.model.response.CommonResponse;
import com.monopoco.common.service.CommonService;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class CommonServiceImpl implements CommonService {

    @Override
    public CommonResponse<UUID> generateRandomUUID() {
        UUID randomUUID = UUID.randomUUID();
        return new CommonResponse<UUID>().success().data(randomUUID);
    }
}
