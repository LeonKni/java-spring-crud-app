package com.exercise.unity.controller.exception;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class will catch and handle all exceptions within the application.
 * Each exception will be routed to its respective handler method based on the exception class.
 * <p/>
 * ExceptionControllerAdvice.java.
 *
 * @author Leon K
 */
@RestController
@ControllerAdvice
public class ExceptionControllerAdvice {
    private static final Logger LOGGER = Logger.getLogger(ExceptionControllerAdvice.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {Exception.class, NullPointerException.class})
    public ResponseEntity<HttpStatus> handleException(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<HttpStatus> handleSqlException(SQLException e) {
        LOGGER.error(e.getMessage(), e);
        return new ResponseEntity<HttpStatus>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
