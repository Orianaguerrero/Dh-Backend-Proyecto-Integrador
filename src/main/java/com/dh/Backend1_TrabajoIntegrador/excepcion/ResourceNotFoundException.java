package com.dh.Backend1_TrabajoIntegrador.excepcion;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
