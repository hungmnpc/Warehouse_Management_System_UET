package com.monopoco.inventory.handler;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Project: Server
 * Package: com.monopoco.inventory.handler
 * Author: hungdq
 * Date: 17/05/2024
 * Time: 00:04
 */
@Component
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String s, Response response) {
        log.info("Error Decoder: {}, {}", s, response.body().toString());
//        if (response.status() >= 400 && response.status() <= 499) {
//            return new FeignException.NotFound("Not found")
//        }
//        if (response.status() >= 500 && response.status() <= 599) {
//            return new StashServerException(
//                    response.status(),
//                    response.reason()
//            );
//        }
//        return errorStatus(methodKey, response);
        return null;
    }
}
