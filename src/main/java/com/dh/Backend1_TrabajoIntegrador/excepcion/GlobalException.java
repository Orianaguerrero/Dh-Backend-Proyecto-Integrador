package com.dh.Backend1_TrabajoIntegrador.excepcion;

import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {
    private static final Logger LOGGER = Logger.getLogger(GlobalException.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> processResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> processBadRequestException(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> proccessIllegalArgumentException(IllegalArgumentException exception) {
        LOGGER.error("IllegalArgumentException: " + exception.getMessage());//, exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("IllegalArgumentException");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> processDataIntegrityViolationException(DataIntegrityViolationException exception) {
        LOGGER.error("DataIntegrityViolationException: " + exception.getMessage());//, exception);
        return ResponseEntity.status(HttpStatus.CONFLICT).body("DataIntegrityViolationException");
    }
}
