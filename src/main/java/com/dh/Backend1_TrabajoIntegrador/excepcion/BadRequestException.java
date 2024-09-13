package com.dh.Backend1_TrabajoIntegrador.excepcion;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
