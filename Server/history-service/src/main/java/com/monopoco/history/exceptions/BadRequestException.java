package com.monopoco.history.exceptions;

/**
 * Project: Server
 * Package: com.monopoco.history.exceptions
 * Author: hungdq
 * Date: 15/04/2024
 * Time: 16:51
 */
/**
 * trigger for bad request exception
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String msg, Throwable t) {
        super(msg, t);
    }

    public BadRequestException(String msg) {
        super(msg);
    }

}
