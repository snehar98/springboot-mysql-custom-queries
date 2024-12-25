package com.github.sneha.springboot_mysql_custom_queries.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class for handling cases when user details are not found.
 * This exception is thrown when a user is not found in the system.
 * It is annotated with @ResponseStatus to return a 404 Not Found status automatically.
 *
 * @author sneharavikumartl
 */
@Slf4j // Lombok annotation for logging
@ResponseStatus(HttpStatus.NOT_FOUND) // Automatically returns HTTP 404 when this exception is thrown
public class DepartmentNotFoundException extends RuntimeException {

    /**
     * Constructor for DepartmentNotFoundException.
     * This constructor takes a message string that will be logged and passed to the parent exception class.
     *
     * @param message the detail message explaining the cause of the exception
     */
    public DepartmentNotFoundException(String message) {
        super(message);
        log.error(message);
    }
}
