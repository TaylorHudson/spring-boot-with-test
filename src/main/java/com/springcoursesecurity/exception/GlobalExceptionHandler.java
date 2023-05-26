package com.springcoursesecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleStudentNotFound(EmployeeNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErrorResponse error = createErrorResponse(status, ex);

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse error = createErrorResponse(status, ex);

        return ResponseEntity.status(status).body(error);
    }

    private ErrorResponse createErrorResponse(HttpStatus status, Exception ex) {
        return ErrorResponse
                .builder()
                .status(status.value())
                .message(ex.getMessage())
                .timeStamp(System.currentTimeMillis())
                .build();
    }

}
